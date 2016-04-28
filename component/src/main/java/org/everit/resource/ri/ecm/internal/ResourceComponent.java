/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.biz)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.resource.ri.ecm.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import org.everit.osgi.ecm.annotation.Activate;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Deactivate;
import org.everit.osgi.ecm.annotation.ManualService;
import org.everit.osgi.ecm.annotation.ManualServices;
import org.everit.osgi.ecm.annotation.ServiceRef;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.component.ComponentContext;
import org.everit.osgi.ecm.extender.ExtendComponent;
import org.everit.persistence.querydsl.support.QuerydslSupport;
import org.everit.resource.ResourceService;
import org.everit.resource.ri.ResourceServiceImpl;
import org.everit.resource.ri.ecm.ResourceConstants;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;

/**
 * ECM component for {@link ResourceService} interface based on {@link ResourceServiceImpl}.
 */
@ExtendComponent
@Component(componentId = ResourceConstants.SERVICE_FACTORYPID_RESOURCE,
    configurationPolicy = ConfigurationPolicy.FACTORY, label = "Everit Resource RI",
    description = "Resource component manages resource table.")
@StringAttributes({
    @StringAttribute(attributeId = Constants.SERVICE_DESCRIPTION,
        defaultValue = ResourceConstants.DEFAULT_SERVICE_DESCRIPTION,
        priority = 1, label = "Service Description",
        description = "The description of this component configuration. It is used to easily "
            + "identify the service registered by this component.") })
@ManualServices(@ManualService(ResourceService.class))
public class ResourceComponent {

  private QuerydslSupport querydslSupport;

  private ServiceRegistration<ResourceService> serviceRegistration;

  /**
   * Component activator method.
   */
  @Activate
  public void activate(final ComponentContext<ResourceComponent> componentContext) {
    ResourceService resourceService = new ResourceServiceImpl(querydslSupport);
    Dictionary<String, Object> serviceProperties =
        new Hashtable<>(componentContext.getProperties());
    serviceRegistration =
        componentContext.registerService(ResourceService.class, resourceService, serviceProperties);
  }

  /**
   * Component deactivate method.
   */
  @Deactivate
  public void deactivate() {
    if (serviceRegistration != null) {
      serviceRegistration.unregister();
    }
  }

  @ServiceRef(attributeId = ResourceConstants.ATTR_QUERYDSL_SUPPORT_TARGET, defaultValue = "",
      attributePriority = 2, label = "Querydsl Support OSGi filter",
      description = "OSGi Service filter expression for QueryDSLSupport instance.")
  public void setQuerydslSupport(final QuerydslSupport querydslSupport) {
    this.querydslSupport = querydslSupport;
  }

}

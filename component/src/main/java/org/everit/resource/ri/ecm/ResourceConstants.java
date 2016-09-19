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
package org.everit.resource.ri.ecm;

/**
 * Constants of the resource component.
 */
public final class ResourceConstants {
	
  /**
   * The property name of the OSGi filter expression defining which QuerydslSupport should be used
   * by the resource component.
   */
  public static final String ATTR_QUERYDSL_SUPPORT_TARGET = "querydslSupport.target";

  public static final String DEFAULT_SERVICE_DESCRIPTION = "Everit Resource Service";

  /**
   * The service factory PID of the resource component.
   */
  public static final String SERVICE_FACTORYPID_RESOURCE = "org.everit.resource.ri.ecm.Resource";

  private ResourceConstants() {
  }
}

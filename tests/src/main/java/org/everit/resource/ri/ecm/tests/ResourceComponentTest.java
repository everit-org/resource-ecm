/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
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
package org.everit.resource.ri.ecm.tests;

import org.everit.osgi.dev.testrunner.TestDuringDevelopment;
import org.everit.osgi.dev.testrunner.TestRunnerConstants;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Deactivate;
import org.everit.osgi.ecm.annotation.Service;
import org.everit.osgi.ecm.annotation.ServiceRef;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.extender.ExtendComponent;
import org.everit.persistence.querydsl.support.QuerydslSupport;
import org.everit.resource.ResourceService;
import org.everit.resource.ri.schema.qdsl.QResource;
import org.junit.Assert;
import org.junit.Test;

import com.querydsl.sql.SQLQuery;

/**
 * Test for Resource Component.
 */
@ExtendComponent
@Component(componentId = "ResourceComponentTest", configurationPolicy = ConfigurationPolicy.IGNORE)
@StringAttributes({
    @StringAttribute(attributeId = TestRunnerConstants.SERVICE_PROPERTY_TESTRUNNER_ENGINE_TYPE,
        defaultValue = "junit4"),
    @StringAttribute(attributeId = TestRunnerConstants.SERVICE_PROPERTY_TEST_ID,
        defaultValue = "resourceComponentTest") })
@Service
public class ResourceComponentTest {

	@Deactivate
	public void deactivate() {
		System.out.println("eactivate calleddss");
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("After sleeping");
	}
	
  /**
   * Fake, non existing resourceId to be able to test deleteResource with wrong parameter.
   */
  private static final Long FAKE_ID = 2000L;

  private QuerydslSupport querydslSupport;

  private ResourceService resourceService;

  /**
   * Counts the resource records in the database.
   *
   * @return Number of the records.
   */
  private Long countResources() {
    return querydslSupport.execute((connection, configuration) -> {
      QResource resource = QResource.resource;
      return new SQLQuery<Long>(connection, configuration).from(resource).fetchCount();
    });
  }

  @ServiceRef(defaultValue = "")
  public void setQuerydslSupport(final QuerydslSupport querydslSupport) {
    this.querydslSupport = querydslSupport;
  }

  @ServiceRef(defaultValue = "")
  public void setResourceService(final ResourceService resourceService) {
    this.resourceService = resourceService;
  }

  /**
   * Test method for {@link ResourceService} createResource and deleteResource methods.
   */
  @Test
  public void testResourceService() {
    long firstCount = countResources();
    long resourceId = resourceService.createResource();
    long count = countResources();
    long expectedCount = firstCount + 1;
    Assert.assertEquals(expectedCount, count);
    Assert.assertFalse(resourceService.deleteResource(FAKE_ID));
    Assert.assertTrue(resourceService.deleteResource(resourceId));
    count = countResources();
    Assert.assertEquals(firstCount, count);
  }

}

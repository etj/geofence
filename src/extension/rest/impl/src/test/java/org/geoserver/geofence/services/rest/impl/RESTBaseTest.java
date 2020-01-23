/* (c) 2014 - 2017 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */

package org.geoserver.geofence.services.rest.impl;

import org.geoserver.geofence.services.dto.ShortGroup;
import org.geoserver.geofence.services.rest.RESTRuleService;
import org.geoserver.geofence.services.rest.RESTUserGroupService;
import org.geoserver.geofence.services.rest.RESTUserService;
import org.geoserver.geofence.services.rest.model.RESTOutputRule;
import org.geoserver.geofence.services.rest.model.RESTOutputRuleList;
import org.geoserver.geofence.services.rest.model.RESTShortUser;
import org.geoserver.geofence.services.rest.model.RESTShortUserList;
import org.geoserver.geofence.services.rest.model.config.RESTFullUserGroupList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("classpath*:applicationContext.xml")
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public abstract class RESTBaseTest {
    private static final Logger LOGGER = LogManager.getLogger(RESTBaseTest.class);
    
    @org.junit.Rule 
    public TestName name = new TestName();

    @Autowired
    protected WebApplicationContext ctx;

    @Autowired
    protected  RESTUserService restUserService;
    
    @Autowired
    protected  RESTUserGroupService restUserGroupService;
    
    @Autowired
    protected  RESTRuleService restRuleService;

    public RESTBaseTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void before() throws Exception {
        LOGGER.info("");
        LOGGER.info("============================== TEST " + name.getMethodName());
        LOGGER.info("");

        assertNotNull("CTX not set", ctx);
        assertNotNull("restUserService not set", restUserService);
        assertNotNull("restUserGroupService not set", restUserGroupService);
        assertNotNull("restRuleService not set", restRuleService);
        
        
        RESTOutputRuleList rules = restRuleService.get(null, null, false, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        for (RESTOutputRule rule : rules) {
            LOGGER.warn("Removing " + rule);
            restRuleService.delete(rule.getId());
        }


        RESTShortUserList users = restUserService.getList(null, null, null);
        for (RESTShortUser user : users) {
            LOGGER.warn("Removing " + user);
            restUserService.delete(user.getUserName(), true);
        }
        RESTFullUserGroupList roles = restUserGroupService.getList(null, null, null);
        for (ShortGroup role : roles) {
            LOGGER.warn("Removing " + role);
            restUserGroupService.delete(role.getName(), true);
        }

        LOGGER.info("----------------- ending cleaning tasks ------------- ");

    }


}

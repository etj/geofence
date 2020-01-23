/* (c) 2014 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */

package org.geoserver.geofence.services.rest;

import org.geoserver.geofence.services.rest.exception.BadRequestRestEx;
import org.geoserver.geofence.services.rest.exception.InternalErrorRestEx;
import org.geoserver.geofence.services.rest.exception.NotFoundRestEx;
import org.geoserver.geofence.services.rest.model.RESTBatch;
import org.geoserver.geofence.services.rest.model.RESTOutputRuleList;
import org.geoserver.geofence.services.rest.model.RESTShortInstanceList;
import org.geoserver.geofence.services.rest.model.RESTShortUserList;
import org.geoserver.geofence.services.rest.model.config.RESTConfigurationRemapping;
import org.geoserver.geofence.services.rest.model.config.RESTFullConfiguration;
import org.geoserver.geofence.services.rest.model.config.RESTFullUserGroupList;
import org.geoserver.geofence.services.rest.model.config.RESTFullUserList;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */

@RequestMapping(path = "/service")
public interface RESTConfigService
{

    /**
     * @Deprecated misbehaves since usergroups introduction. Please use backup()
     */
    @GetMapping( //
            path = "/full", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )              
    RESTFullConfiguration getConfiguration(
            @RequestParam(name = "includeGFUsers", defaultValue = "False") Boolean includeGRUsers);

    @GetMapping( //
            path = "/backup", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )              
    RESTBatch backup(
            @RequestParam(name = "includeGFUsers", defaultValue = "False") Boolean includeGRUsers);

    @PutMapping( //
            path = "/restore", //
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )              
    void restore(@RequestBody RESTBatch batch)
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

    @PutMapping( //
            path = "/cleanup"
    )              
    void cleanup()
            throws InternalErrorRestEx;

    @GetMapping( //
            path = "/backup/groups", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )              
    RESTBatch backupGroups();

    @GetMapping( //
            path = "/backup/users", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )              
    RESTBatch backupUsers();

    @GetMapping( //
            path = "/backup/instances", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )              
    RESTBatch backupInstances();

    @GetMapping( //
            path = "/backup/rules", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )              
    RESTBatch backupRules();

    /**
     * @Deprecated
     */
    @PutMapping( //
            path = "/full", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )              
    RESTConfigurationRemapping setConfiguration(
            @RequestBody RESTFullConfiguration config,
            @RequestParam(name = "includeGRUsers", defaultValue = "False") Boolean includeGRUsers)
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

    /**
     * @Deprecated
     */
    @GetMapping( //
            path = "/users", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )              
    RESTFullUserList getUsers()
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

    /**
     * @Deprecated
     */
    @GetMapping( //
            path = "/groups", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )                          
    RESTFullUserGroupList getUserGroups()
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

    //====

    /**
     * @Deprecated used for testing only
     */
    @PostMapping( //
            path = "/groups", //
            consumes = {
                MediaType.APPLICATION_XML_VALUE, 
                MediaType.TEXT_XML_VALUE, 
                MediaType.APPLICATION_JSON_VALUE}
    )                          
    void setUserGroups(@RequestBody RESTFullUserGroupList groups)
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

    /**
     * only for debug/quick insert
     * takes as input the same xml returned by the related service GET operation
     *
     * @Deprecated used for testing only
     */
    @PostMapping( //
            path = "/users/short", //
            consumes = {
                MediaType.APPLICATION_XML_VALUE, 
                MediaType.TEXT_XML_VALUE, 
                MediaType.APPLICATION_JSON_VALUE}
    )                          
    void setUsers(@RequestBody RESTShortUserList users)
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

    /**
     * @Deprecated used for testing only
     */
    @PostMapping( //
            path = "/instances/short", //
            consumes = {
                MediaType.APPLICATION_XML_VALUE, 
                MediaType.TEXT_XML_VALUE, 
                MediaType.APPLICATION_JSON_VALUE}
    )                          
    void setInstances(@RequestBody RESTShortInstanceList instances)
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

    /**
     * @Deprecated used for testing only
     */
    @PostMapping( //
            path = "/rules/short", //
            consumes = {
                MediaType.APPLICATION_XML_VALUE, 
                MediaType.TEXT_XML_VALUE, 
                MediaType.APPLICATION_JSON_VALUE}
    )                          
    void setRules(@RequestBody RESTOutputRuleList rules)
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

}

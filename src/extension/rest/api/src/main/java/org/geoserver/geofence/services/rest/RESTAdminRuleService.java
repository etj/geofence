/* (c) 2015 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */

package org.geoserver.geofence.services.rest;

import org.geoserver.geofence.services.rest.exception.BadRequestRestEx;
import org.geoserver.geofence.services.rest.exception.InternalErrorRestEx;
import org.geoserver.geofence.services.rest.exception.NotFoundRestEx;

import org.geoserver.geofence.services.rest.model.RESTInputAdminRule;
import org.geoserver.geofence.services.rest.model.RESTOutputAdminRule;
import org.geoserver.geofence.services.rest.model.RESTOutputAdminRuleList;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */

@RequestMapping(path = "/adminrules")
public interface RESTAdminRuleService
{
    @PostMapping( //
            path = "/", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )    
    ResponseEntity<Long> insert(@RequestBody RESTInputAdminRule rule) throws BadRequestRestEx, NotFoundRestEx;

    @GetMapping( //
            path = "/id/{id}", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )    
    RESTOutputAdminRule get(@PathVariable("id") Long id) throws BadRequestRestEx, NotFoundRestEx;

    @PutMapping( //
            path = "/id/{id}", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )    
    void update(@PathVariable("id") Long id,
        @RequestBody RESTInputAdminRule rule) throws BadRequestRestEx, NotFoundRestEx;

    @DeleteMapping( //
            path = "/id/{id}", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )    
    ResponseEntity delete(@PathVariable("id") Long id) throws BadRequestRestEx, NotFoundRestEx;

    @GetMapping( //
            path = "/", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )    
    RESTOutputAdminRuleList get(
        @RequestParam("page") Integer page,
        @RequestParam("entries") Integer entries,
        @RequestParam(name="full", defaultValue = "false") boolean full,

        @RequestParam("userName") String userName,
        @RequestParam("userAny")  Boolean userAny,

        @RequestParam("groupName") String groupName,
        @RequestParam("groupAny")  Boolean groupAny,

        @RequestParam("instanceId")   Long instanceId,
        @RequestParam("instanceName") String  instanceName,
        @RequestParam("instanceAny")  Boolean instanceAny,

        @RequestParam("workspace") String  workspace,
        @RequestParam("workspaceAny")  Boolean workspaceAny

    ) throws BadRequestRestEx, InternalErrorRestEx;

    @GetMapping( //
            path = "/count"
    )    
    long count(
        @RequestParam("userName") String userName,
        @RequestParam("userAny")  Boolean userAny,

        @RequestParam("groupName") String groupName,
        @RequestParam("groupAny")  Boolean groupAny,

        @RequestParam("instanceId")   Long instanceId,
        @RequestParam("instanceName") String  instanceName,
        @RequestParam("instanceAny")  Boolean instanceAny,

        @RequestParam("workspace") String  workspace,
        @RequestParam("workspaceAny")  Boolean workspaceAny
    );

}

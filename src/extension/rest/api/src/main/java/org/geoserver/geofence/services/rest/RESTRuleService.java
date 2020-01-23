/* (c) 2014 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.geofence.services.rest;

import org.geoserver.geofence.services.exception.WebApplicationException.Response;
import org.geoserver.geofence.services.rest.exception.BadRequestRestEx;
import org.geoserver.geofence.services.rest.exception.InternalErrorRestEx;
import org.geoserver.geofence.services.rest.exception.NotFoundRestEx;
import org.geoserver.geofence.services.rest.model.RESTInputRule;
import org.geoserver.geofence.services.rest.model.RESTOutputRule;
import org.geoserver.geofence.services.rest.model.RESTOutputRuleList;

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

@RequestMapping(path = "/rules")
public interface RESTRuleService {

    @PostMapping( //
            path = "", //
            produces = {MediaType.TEXT_PLAIN_VALUE}
    )
    ResponseEntity<Long> insert(@RequestBody RESTInputRule rule) 
            throws BadRequestRestEx, NotFoundRestEx;

    @GetMapping( //
            path = "/id/{id}", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    RESTOutputRule get(@PathVariable("id") Long id) 
            throws BadRequestRestEx, NotFoundRestEx;

    @PutMapping( //
            path = "/id/{id}", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    void update(@PathVariable("id") Long id, @RequestBody RESTInputRule rule) 
            throws BadRequestRestEx, NotFoundRestEx;

    @DeleteMapping( //
            path = "/id/{id}", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity delete(@PathVariable("id") Long id) 
            throws BadRequestRestEx, NotFoundRestEx;

    @GetMapping( //
            path = "", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    RESTOutputRuleList get(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "entries", required = false) Integer entries,
            @RequestParam(name = "full", defaultValue = "false") boolean full,
            @RequestParam(name = "userName", required = false) String userName,
            @RequestParam(name = "userAny", required = false) Boolean userAny,
            @RequestParam(name = "groupName", required = false) String groupName,
            @RequestParam(name = "groupAny", required = false) Boolean groupAny,
            @RequestParam(name = "instanceId", required = false) Long instanceId,
            @RequestParam(name = "instanceName", required = false) String instanceName,
            @RequestParam(name = "instanceAny", required = false) Boolean instanceAny,
            @RequestParam(name = "service", required = false) String serviceName,
            @RequestParam(name = "serviceAny", required = false) Boolean serviceAny,
            @RequestParam(name = "request", required = false) String requestName,
            @RequestParam(name = "requestAny", required = false) Boolean requestAny,
            @RequestParam(name = "workspace", required = false) String workspace,
            @RequestParam(name = "workspaceAny", required = false) Boolean workspaceAny,
            @RequestParam(name = "layer", required = false) String layer,
            @RequestParam(name = "layerAny", required = false) Boolean layerAny
    ) throws BadRequestRestEx, InternalErrorRestEx;

    @GetMapping( //
            path = "/count",
            produces = {MediaType.TEXT_PLAIN_VALUE}
    )
    Long count(
            @RequestParam(name = "userName", required = false) String userName,
            @RequestParam(name = "userAny", required = false) Boolean userAny,
            @RequestParam(name = "groupName", required = false) String groupName,
            @RequestParam(name = "groupAny", required = false) Boolean groupAny,
            @RequestParam(name = "instanceId", required = false) Long instanceId,
            @RequestParam(name = "instanceName", required = false) String instanceName,
            @RequestParam(name = "instanceAny", required = false) Boolean instanceAny,
            @RequestParam(name = "service", required = false) String serviceName,
            @RequestParam(name = "serviceAny", required = false) Boolean serviceAny,
            @RequestParam(name = "request", required = false) String requestName,
            @RequestParam(name = "requestAny", required = false) Boolean requestAny,
            @RequestParam(name = "workspace", required = false) String workspace,
            @RequestParam(name = "workspaceAny", required = false) Boolean workspaceAny,
            @RequestParam(name = "layer", required = false) String layer,
            @RequestParam(name = "layerAny", required = false) Boolean layerAny
    );

    /**
     * Move the provided rules to the target priority. Rules will be sorted by
     * their priority, first rule will be updated with a priority equal to the
     * target priority and the next ones will get an incremented priority value.
     */
    @GetMapping( //
            path = "/move", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity move(
            @RequestParam("rulesIds") String rulesIds,
            @RequestParam("targetPriority") Integer targetPriority
    ) throws BadRequestRestEx, InternalErrorRestEx;
}

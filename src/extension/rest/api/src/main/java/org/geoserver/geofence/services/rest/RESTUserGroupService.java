/* (c) 2014 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.geofence.services.rest;

import org.geoserver.geofence.services.dto.ShortGroup;
import org.geoserver.geofence.services.exception.WebApplicationException.Response;
import org.geoserver.geofence.services.rest.exception.BadRequestRestEx;
import org.geoserver.geofence.services.rest.exception.ConflictRestEx;
import org.geoserver.geofence.services.rest.exception.InternalErrorRestEx;
import org.geoserver.geofence.services.rest.exception.NotFoundRestEx;
import org.geoserver.geofence.services.rest.model.RESTInputGroup;
import org.geoserver.geofence.services.rest.model.config.RESTFullUserGroupList;

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
@RequestMapping(path = "/groups")
public interface RESTUserGroupService {

    @GetMapping( //
            path = "/", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    RESTFullUserGroupList getList(
            @RequestParam("nameLike") String nameLike,
            @RequestParam("page") Integer page,
            @RequestParam("entries") Integer entries) 
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

    @GetMapping( //
            path = "/count/{nameLike}"
    )
    long count(@PathVariable("nameLike") String nameLike);

    @GetMapping( //
            path = "/name/{name}", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    ShortGroup get(@PathVariable("name") String name) 
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

    @PostMapping( //
            path = "/", //
            produces = {MediaType.TEXT_PLAIN_VALUE}
    )
    ResponseEntity<Long> insert(@RequestBody RESTInputGroup group) 
            throws BadRequestRestEx, ConflictRestEx, InternalErrorRestEx;

    @PutMapping( //
            path = "/name/{name}", //
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    void update(
            @PathVariable("name") String name,
            @RequestBody RESTInputGroup group) 
            throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

    /**
     * Deletes a UserGroup.
     *
     * @param name The name of the group to delete
     * @param cascade When true, also delete all the Rules referring to that
     * group
     *
     * @throws BadRequestRestEx (HTTP code 400) if parameters are illegal
     * @throws NotFoundRestEx (HTTP code 404) if the group is not found
     * @throws ConflictRestEx (HTTP code 409) if any rule refers to the group
     * and cascade is false
     * @throws InternalErrorRestEx (HTTP code 500)
     */
    @DeleteMapping( //
            path = "/name/{name}"
    )
    ResponseEntity delete(
            @PathVariable("name") String name,
            @RequestParam(name = "cascade", defaultValue = "false") boolean cascade) 
            throws ConflictRestEx, NotFoundRestEx, InternalErrorRestEx;

}

/* (c) 2014 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */

package org.geoserver.geofence.services.rest;

import org.geoserver.geofence.services.rest.exception.BadRequestRestEx;
import org.geoserver.geofence.services.rest.exception.InternalErrorRestEx;
import org.geoserver.geofence.services.rest.exception.NotFoundRestEx;
import org.geoserver.geofence.services.rest.model.RESTBatch;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */

@RequestMapping("/batch")
public interface RESTBatchService
{
    @PostMapping( //
            path = "/exec", //
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE}
    )                
    ResponseEntity exec(@RequestBody RESTBatch batch) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;

    /**
     * Similar to exec, but not transaction.
     * Used internally.
     */
    void runBatch(RESTBatch batch) throws BadRequestRestEx, NotFoundRestEx, InternalErrorRestEx;
}

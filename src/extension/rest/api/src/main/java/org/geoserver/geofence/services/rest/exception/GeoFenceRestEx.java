/* (c) 2014 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */

package org.geoserver.geofence.services.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Used as a catchall when forwarding exceptions
 *
 * @author ETj (etj at geo-solutions.it)
 */
public abstract class GeoFenceRestEx extends ResponseStatusException {

    protected GeoFenceRestEx(HttpStatus status, String reason) {
        super(status, reason);
    }
}

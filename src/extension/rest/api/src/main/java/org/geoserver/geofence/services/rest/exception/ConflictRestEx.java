/* (c) 2014 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */

package org.geoserver.geofence.services.rest.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class ConflictRestEx extends GeoFenceRestEx {

    public ConflictRestEx(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}

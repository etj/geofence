/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geoserver.geofence.services.rest.impl.converters;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 *
 * @author geosol
 */
//@EnableWebMvc
@Configuration
public class MessageConvertPrinter {

    private static final Logger LOGGER = LogManager.getLogger(MessageConvertPrinter.class);

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        handlerAdapter.getMessageConverters().stream()
                .map(c -> c.toString())
                .forEach(LOGGER::info);
    }
}

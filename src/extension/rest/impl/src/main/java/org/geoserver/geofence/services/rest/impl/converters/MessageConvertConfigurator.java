/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geoserver.geofence.services.rest.impl.converters;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author geosol
 */

@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = MessageConvertConfigurator.class)
public class MessageConvertConfigurator implements WebMvcConfigurer {

    private static final Logger LOGGER = LogManager.getLogger(MessageConvertConfigurator.class);    
    
   @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        LOGGER.info("*** Adding converter " + LongHttpMessageConverter.class.getSimpleName());
        converters.add(new LongHttpMessageConverter());
    }
    
    
}
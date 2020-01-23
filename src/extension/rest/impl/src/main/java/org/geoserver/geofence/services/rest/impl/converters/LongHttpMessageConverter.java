/*
 */
package org.geoserver.geofence.services.rest.impl.converters;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

/**
 *
 * @author geosol
 */
public class LongHttpMessageConverter extends AbstractHttpMessageConverter<Long> implements HttpMessageConverter<Long> {
    
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;    

    public LongHttpMessageConverter() {
        super(DEFAULT_CHARSET, MediaType.TEXT_PLAIN);
    }
        
    @Override
    public boolean supports(Class<?> clazz) {
        return Long.class == clazz;
    }

    @Override
    protected Long readInternal(Class<? extends Long> clazz, HttpInputMessage inputMessage) 
            throws IOException, HttpMessageNotReadableException {
        
        Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
        String asString = StreamUtils.copyToString(inputMessage.getBody(), charset);
        return Long.parseLong(asString);
    }

    @Override
    protected void writeInternal(Long t, HttpOutputMessage outputMessage) 
            throws IOException, HttpMessageNotWritableException {
        Charset charset = getContentTypeCharset(outputMessage.getHeaders().getContentType());
        StreamUtils.copy(t.toString(), charset, outputMessage.getBody());
    }

    private Charset getContentTypeCharset(@Nullable MediaType contentType) {
        if (contentType != null && contentType.getCharset() != null) {
            return contentType.getCharset();
        } else {
            Charset charset = getDefaultCharset();
            Assert.state(charset != null, "No default charset");
            return charset;
        }
    }

}

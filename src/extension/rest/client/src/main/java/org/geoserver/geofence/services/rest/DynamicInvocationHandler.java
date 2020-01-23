/*
 
 */
package org.geoserver.geofence.services.rest;


import java.awt.print.Book;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.naming.OperationNotSupportedException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;


import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



public class DynamicInvocationHandler implements InvocationHandler {
 
    private static Logger LOGGER = Logger.getLogger(DynamicInvocationHandler.class);
 
    private final Class restService;
    private final String baseUrl;
    
    
    private String classPathPrefix = null;

    public DynamicInvocationHandler(Class restService, String baseUrl) {
        this.restService = restService;
        this.baseUrl = baseUrl;
        
        // gather class-level RequestHandler
        RequestMapping rm = (RequestMapping)restService.getAnnotation(RequestMapping.class);
        if(rm != null) {
            classPathPrefix = rm.path()[0];
        }
                
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) 
      throws Throwable {
        
        LOGGER.debug("Invoked method: "+  method.getName());

        
        CoalescedReqMap rm = getRequestMapping(method);
        if(rm == null) {
            throw new RuntimeException("RequestMapping not found for method " + method.getName());
        }

        LOGGER.debug(" ===> RequestMapping found:: "+  rm);
        
        
        String[] methodPaths = rm.path;
//        if(methodPaths != null) {
//            for (String methodPath : methodPaths) {
//            }
//        }
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(baseUrl);
                LOGGER.debug("  BASE target: "+  target);                
        
        if(classPathPrefix != null)
            target = target.path(classPathPrefix);

        LOGGER.debug("  CLASS target: "+  target);                

        if(methodPaths != null) {
            if(methodPaths.length > 1) {
                throw new IllegalStateException("Unexpected mathod path len");
            }
            
            String methodPath = methodPaths[0];            
            LOGGER.debug("  Method path: "+  methodPath);
            
            if(methodPath.contains("{")) {
                methodPath = bindArgs(method, methodPath, args);
                LOGGER.info("  Bound Method path: "+  methodPath);                
            }
                            
            target = target.path(methodPath);
        }

        LOGGER.info("  METHOD target: "+  target);                
        
        
        
        Builder request = target.request(rm.produces);
        Response response;
        switch(rm.method[0]) {
            case GET:            
                response = request.get();
                break;
            case DELETE:            
                response = request.delete();
                break;
            default:
                throw new OperationNotSupportedException("Method " + rm.method[0] + " not supported yet");
        }
        
        LOGGER.info("Request " +  rm.method[0] + " " + target.getUri());
        LOGGER.info("Accept: " +  Arrays.deepToString(rm.produces));
        LOGGER.info("Response: " + response.getStatus() + " " + response.getStatusInfo());
        LOGGER.info("Expected type: " + method.getReturnType());
        
        if(response.getStatus() > 399) {
            throw new IllegalArgumentException("request status: " + response.getStatus() + " " + response.getStatusInfo());
        }
        
        Class responseType = method.getReturnType();
        if(responseType == ResponseEntity.class) {
            return response.getEntity();
        
        } else {        
            return response.readEntity(responseType);
        }
        
//        System.out.println("Response code: " + response.getStatus());
//		Book book = response.readEntity(Book.class);
//		System.out.println("Title: " + book.getTitle());
//        
// 
//        return 42;
    }
    

    private CoalescedReqMap getRequestMapping(Method method) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        
        for (Annotation annotation : method.getAnnotations()) {
            CoalescedReqMap rm = getSuperAnnotations(method, annotation);
            if(rm != null) {
                return rm;
            }
        }
               
        return null;

    }

    private CoalescedReqMap getSuperAnnotations(Method method, Annotation annotation) {
        
//        LOGGER.info("  Check Annotation for method " + method.getName() + " -> " + annotation.annotationType().getSimpleName() + " TYPE " + annotation );
        
        if(annotation instanceof RequestMapping) {
            RequestMapping rm = (RequestMapping) annotation;
            CoalescedReqMap ret = new CoalescedReqMap();
            ret.method = rm.method();
            ret.path = rm.path();
            ret.produces = rm.produces();
            return ret;
        }
                
        for (Annotation superann : annotation.annotationType().getAnnotations()) {
//            LOGGER.info("     Checking superannotation -> " + superann.annotationType().getSimpleName());
            if(superann.annotationType() == RequestMapping.class) {
                CoalescedReqMap ret = new CoalescedReqMap();
                ret.method = ((RequestMapping) superann).method();
                //ret.path = ((RequestMapping) annotation).path();
                                
//                for (Method m : annotation.annotationType().getMethods()) {
//                    LOGGER.info("      Found method " + annotation.annotationType().getSimpleName() + " :: " + m );
//                }
                
                try {
                    ret.path = (String[])annotation.annotationType().getMethod("path").invoke(annotation);
                    ret.produces = (String[])annotation.annotationType().getMethod("produces").invoke(annotation);
                } catch (Exception ex) {
                    LOGGER.warn("Introspection error", ex);
                    return null;
                }
                return ret;
            }
        }
        
        return null;
    }

    private String bindArgs(Method method, String path, Object[] args) {
        for (int i = 0; i < method.getParameterCount(); i++) {
            Annotation[] pAnnArr = method.getParameterAnnotations()[i];
            for (Annotation pAnn : pAnnArr) {
                LOGGER.debug(" - Arg annotation: " + pAnn);
                if(pAnn.annotationType() == PathVariable.class) {
                    PathVariable pv = (PathVariable)pAnn;
                    String varName = pv.value();
                    String value = args[i] == null ? "" : args[i].toString();
                    LOGGER.debug(" - Binding var " + varName + " = " + value);
                    
                    path = path.replace("{"+varName+"}", value);
                }
            }
        }
        
        return path;
    }
            
    static class CoalescedReqMap {
        public String[] path = null;
        public RequestMethod[] method = null;
        public String[] produces = null;
        

        @Override
        public String toString() {
            String p = path != null? path.length + "/"+path[0]: "-";
            String m = method != null? method.length + "/"+ method[0].name() : "-";
            
            return "CoalescedReqMap{" + "path=" + p + ", method=" + m + '}';
        }
    }
    
}
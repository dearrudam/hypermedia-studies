package com.github.dearrudam.hypermediastudies.thingshop.boundary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    // Logger
    private final Logger log = LoggerFactory.getLogger(CORSFilter.class);

    @Override
    public void filter(final ContainerRequestContext requestContext,
                       final ContainerResponseContext cres) throws IOException {
        cres.getHeaders().add("Access-Control-Allow-Origin","*");
        cres.getHeaders().add("Access-Control-Allow-Headers", "accept, origin, authorization, content-type, x-requested-with");
        cres.getHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        cres.getHeaders().add("Access-Control-Max-Age", "-1");
        cres.getHeaders().add("Cache-Control", "no-cache");

    }

}
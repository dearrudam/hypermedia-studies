package com.github.dearrudam.hypermediastudies.thingshop.boundary;

import com.github.dearrudam.hypermediastudies.thingshop.representation.EntityBuilder;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/resources")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ThingShopResource {

    @Context
    UriInfo uriInfo;

    @Inject
    EntityBuilder entityBuilder;

    @GET
    public Response root(@Context UriInfo uriInfo) {
        return Response.ok(
                    entityBuilder.buildRoot(uriInfo)
                )
                .build();
    }

}
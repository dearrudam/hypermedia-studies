package com.github.dearrudam.hypermediastudies.thingshop.boundary;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("/resources")
@RequestScoped
public class ThingShopResource {


    @GET
    @Produces("application/vnd.siren+json")
    public Response root(@Context UriInfo uriInfo) {
        return Response.ok(
                Json.createObjectBuilder()
                        .add("actions", Json
                                .createArrayBuilder()
                                .add(Json
                                        .createObjectBuilder()
                                        .add("name", "items")
                                        .add("href", uriInfo.getBaseUriBuilder().path(ItemsResource.class, "items").build().toString())
                                        .build()
                                ).build()
                        ).build())
                .build();
    }

}
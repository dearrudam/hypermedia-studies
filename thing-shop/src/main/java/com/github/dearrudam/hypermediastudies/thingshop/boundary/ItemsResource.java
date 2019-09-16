package com.github.dearrudam.hypermediastudies.thingshop.boundary;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

public class ItemsResource {

    @GET
    @Path("resources/items")
    @Produces("application/vnd.siren+json")
    public Response items() {
        return Response.ok().build();
    }

}

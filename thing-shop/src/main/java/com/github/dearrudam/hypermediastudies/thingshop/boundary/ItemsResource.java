package com.github.dearrudam.hypermediastudies.thingshop.boundary;

import com.github.dearrudam.hypermediastudies.thingshop.entity.Item;
import com.github.dearrudam.hypermediastudies.thingshop.representation.EntityBuilder;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("resources/items")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemsResource {

    @Context
    UriInfo uriInfo;

    @Inject
    EntityBuilder entityBuilder;

    @GET
    @Path("")
    public Response items(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("5") int pageSize
    ) {
        return Response.ok(
                entityBuilder.buildItems(Item.findAll().page(Page.of(page, pageSize)), uriInfo)
        ).build();
    }

    @GET
    @Path("{id}")
    public Response getItem(@PathParam("id") Long id) {
        Item item = Item.findById(id);
        if (item == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(
                entityBuilder.buildItem(item, uriInfo)
        ).build();
    }
}

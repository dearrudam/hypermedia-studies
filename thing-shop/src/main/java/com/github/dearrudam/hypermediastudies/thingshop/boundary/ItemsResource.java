package com.github.dearrudam.hypermediastudies.thingshop.boundary;

import com.github.dearrudam.hypermediastudies.thingshop.entity.Item;
import com.github.dearrudam.hypermediastudies.thingshop.entity.Money;
import com.github.dearrudam.hypermediastudies.thingshop.representation.EntityBuilder;
import com.github.dearrudam.hypermediastudies.thingshop.representation.LinkBuilder;
import io.quarkus.panache.common.Page;
import org.checkerframework.checker.regex.qual.RegexBottom;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.math.BigDecimal;


@Path("resources/items")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemsResource {

    @Context
    UriInfo uriInfo;

    @Inject
    EntityBuilder entityBuilder;

    @Inject
    LinkBuilder linkBuilder;

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

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addItem(
            @FormParam("name") @NotEmpty String name,
            @FormParam("price_currency") @DefaultValue("BRL") String priceCurrency,
            @FormParam("price_amount") Double priceAmount){
        Item item = new Item(name, new Money(priceCurrency, BigDecimal.valueOf(priceAmount).setScale(2)));
        Item.persist(item);
        return Response.created(linkBuilder.forItem(item, uriInfo)).build();
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

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response updateItem(@PathParam("id") Long id,
                               @FormParam("name") @NotEmpty String name,
                               @FormParam("price_currency") @DefaultValue("BRL") String priceCurrency,
                               @FormParam("price_amount") Double priceAmount){
        Item item=Item.findById(id);
        if(item==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        item.name = name;
        item.amount = new Money(priceCurrency, BigDecimal.valueOf(priceAmount).setScale(2));
        Item.persist(item);
        return Response.ok(entityBuilder.buildItem(item, uriInfo)).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response deleteItem(@PathParam("id") Long id){
        long deleted = Item.delete("id", id);
        if(deleted == 0){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}

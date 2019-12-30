package com.github.dearrudam.hypermediastudies.thingshop.representation;

import com.github.dearrudam.hypermediastudies.thingshop.boundary.ItemsResource;
import com.github.dearrudam.hypermediastudies.thingshop.boundary.ThingShopResource;
import com.github.dearrudam.hypermediastudies.thingshop.entity.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@ApplicationScoped
public class LinkBuilder {

    public URI forItems(UriInfo uriInfo, int page, int pageSize) {
        return createResourceURI(uriInfo, ItemsResource.class, "items")
                .queryParam("page", page)
                .queryParam("pageSize", pageSize).build();
    }

    public URI forItems(UriInfo uriInfo) {
        return forItems(uriInfo, 0, 5);
    }

    private UriBuilder createResourceURI(UriInfo uriInfo, Class<?> resourceClass, String method) {
        return uriInfo.getBaseUriBuilder().path(resourceClass).path(resourceClass, method);
    }

    private URI createResourceURI(UriInfo uriInfo, Class<?> resourceClass) {
        return uriInfo.getBaseUriBuilder().path(resourceClass).build();
    }

    public URI forItem(Item item, UriInfo uriInfo) {
        return createResourceURI(uriInfo, ItemsResource.class, "getItem").build(item.id);
    }

    public URI forRootResources(UriInfo uriInfo) {
        return createResourceURI(uriInfo, ThingShopResource.class);
    }


}

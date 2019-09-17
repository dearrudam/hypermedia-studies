package com.github.dearrudam.hypermediastudies.thingshop.representation;

import com.github.dearrudam.hypermediastudies.thingshop.entity.Item;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static com.sebastian_daschner.siren4javaee.Siren.createEntityBuilder;

@ApplicationScoped
public class EntityBuilder {

    @Inject
    LinkBuilder linkBuilder;

    public JsonObject buildItems(PanacheQuery<Item> items, UriInfo uriInfo) {

        Page currentPage = items.page();

        URI selfLink = linkBuilder.forItems(uriInfo, currentPage.index, currentPage.size);

        com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder =
                createEntityBuilder()
                        .addClass("items")
                        .addLink(selfLink, "self");

        items.list().stream().map(i -> buildItemTeaser(i, uriInfo)).forEach(entityBuilder::addEntity);

        if(items.count() > 0) {
            if (items.hasPreviousPage()) {
                Page page = currentPage.previous();
                entityBuilder.addLink(linkBuilder.forItems(uriInfo, page.index, page.size), "previous");
            }

            if (items.hasNextPage()) {
                Page page = currentPage.next();
                entityBuilder.addLink(linkBuilder.forItems(uriInfo, page.index, page.size), "next");
            }
        }

        entityBuilder.addLink(linkBuilder.forRootResources(uriInfo), "root");

        return entityBuilder
                .build();
    }

    public JsonObject buildItem(Item item, UriInfo uriInfo) {
        com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder = createEntityBuilder().addClass("item");
        entityBuilder.addProperty("name", item.name)
                .addProperty("price_currency", item.amount.currency)
                .addProperty("price_amount", item.amount.amount)
                .addLink(linkBuilder.forItem(item, uriInfo), "self")
                .addLink(linkBuilder.forItems(uriInfo,0,5), "items")
                .addLink(linkBuilder.forRootResources(uriInfo), "root");
        return entityBuilder.build();
    }

    public JsonObject buildItemTeaser(Item item, UriInfo uriInfo) {
        com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder = createEntityBuilder().addClass("item");
        entityBuilder.addProperty("name", item.name)
                .addLink(linkBuilder.forItem(item, uriInfo), "self");
        return entityBuilder.build();
    }

    public JsonObject buildRoot(UriInfo uriInfo) {
        com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder = createEntityBuilder().addClass("root");
        entityBuilder.addLink(linkBuilder.forItems(uriInfo,0,5),"items");
        return entityBuilder.build();
    }
}

package com.github.dearrudam.hypermediastudies.thingshop.representation;

import com.github.dearrudam.hypermediastudies.thingshop.entity.Item;
import com.sebastian_daschner.siren4javaee.FieldType;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;

import static com.sebastian_daschner.siren4javaee.Siren.*;

@ApplicationScoped
public class EntityBuilder {

    @Inject
    LinkBuilder linkBuilder;

    public com.sebastian_daschner.siren4javaee.EntityBuilder buildItems(PanacheQuery<Item> items, UriInfo uriInfo) {

        Page currentPage = items.page();

        URI selfLink = linkBuilder.forItems(uriInfo, currentPage.index, currentPage.size);

        com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder =
                createEntityBuilder()
                        .addClass("items")
                        .addLink(selfLink, "self");

        items.list().stream().map(i -> buildItemTeaser(i, uriInfo)).forEach(entityBuilder::addEntity);

        if (items.count() > 0) {
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

        URI itemlink = linkBuilder.forItems(uriInfo);
        String actionName = "add-item";
        String method = "POST";
        String title = "Add Item";

        entityBuilder
                .addAction(createActionBuilder()
                        .setName(actionName)
                        .setType(MediaType.APPLICATION_FORM_URLENCODED)
                        .setMethod(method)
                        .setHref(itemlink)
                        .setTitle(title)
                        .addField(
                                createFieldBuilder()
                                        .setName("name")
                                        .setTitle("Item's name")
                                        .setType(FieldType.TEXT)
                        )
                        .addField(
                                createFieldBuilder()
                                        .setName("price_currency")
                                        .setTitle("Item's currency")
                                        .setType(FieldType.TEXT)
                                        .setValue("BRL")
                        )
                        .addField(
                                createFieldBuilder()
                                        .setName("price_amount")
                                        .setTitle("Item's amount")
                                        .setType(FieldType.NUMBER)
                                        .setValue("0.00")
                        )
                        .build());

        return entityBuilder;
    }


    public com.sebastian_daschner.siren4javaee.EntityBuilder buildItem(Item item, UriInfo uriInfo) {
        com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder = createEntityBuilder().addClass("item");
        entityBuilder.addProperty("name", item.name)
                .addProperty("price_currency", item.amount.currency)
                .addProperty("price_amount", item.amount.amount)
                .addLink(linkBuilder.forItem(item, uriInfo), "self")
                .addLink(linkBuilder.forItems(uriInfo, 0, 5), "items")
                .addLink(linkBuilder.forRootResources(uriInfo), "root");

        URI itemlink = linkBuilder.forItem(item, uriInfo);

        entityBuilder
                .addAction(createActionBuilder()
                        .setName("edit-item")
                        .setType(MediaType.APPLICATION_FORM_URLENCODED)
                        .setMethod("PUT")
                        .setHref(itemlink)
                        .setTitle("Edit item")
                        .addField(
                                createFieldBuilder()
                                        .setName("name")
                                        .setTitle("Item's name")
                                        .setType(FieldType.TEXT)
                                        .setValue(item.name)
                        )
                        .addField(
                                createFieldBuilder()
                                        .setName("price_currency")
                                        .setTitle("Item's currency")
                                        .setType(FieldType.TEXT)
                                        .setValue(item.amount.currency)
                        )
                        .addField(
                                createFieldBuilder()
                                        .setName("price_amount")
                                        .setTitle("Item's amount")
                                        .setType(FieldType.NUMBER)
                                        .setValue(item.amount.amount.toString())
                        )
                        .build());
        entityBuilder
                .addAction(createActionBuilder()
                        .setName("del-item")
                        .setType(MediaType.APPLICATION_FORM_URLENCODED)
                        .setMethod("DELETE")
                        .setHref(itemlink)
                        .setTitle("Delete Item")
                        .build());

        return entityBuilder;
    }

    public com.sebastian_daschner.siren4javaee.EntityBuilder buildItemTeaser(Item item, UriInfo uriInfo) {
        com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder = createEntityBuilder().addClass("item");
        entityBuilder.addProperty("name", item.name)
                .addLink(linkBuilder.forItem(item, uriInfo), "self");
        return entityBuilder;
    }

    public com.sebastian_daschner.siren4javaee.EntityBuilder buildRoot(UriInfo uriInfo) {
        com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder = createEntityBuilder().addClass("root");
        entityBuilder.addLink(linkBuilder.forItems(uriInfo, 0, 5), "items");
        return entityBuilder;
    }

    public com.sebastian_daschner.siren4javaee.EntityBuilder buildForMessage(String message) {
        com.sebastian_daschner.siren4javaee.EntityBuilder entityBuilder = createEntityBuilder().addClass("message");
        entityBuilder.addProperty("message", message);
        return entityBuilder;
    }
}

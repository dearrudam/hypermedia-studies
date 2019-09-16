package com.github.dearrudam.hypermediastudies.thingshop.boundary;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;

import java.net.URL;
import java.text.MessageFormat;

import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@QuarkusTest
public class ThingShopResourceTest {

    @TestHTTPResource("")
    URL url;

    @Test
    public void root() {
        given()
                .when().get("/resources")
                .then()
                .statusCode(200)
                .header(HttpHeaders.CONTENT_TYPE, "application/vnd.siren+json")
                //.body( equalTo("{}"))
                .body(matchesJsonSchemaInClasspath("siren.schema.json"))
                .body(not("{}"))
                .body("actions.size()", greaterThan(0))
                .body("actions*.name", not(empty()))
                .body("actions*.href", not(empty()))
                .body("actions[0].name", is("items"))
                .body("actions[0].href", is(String.format("%sresources/items",url.toString())))

        ;
    }
}
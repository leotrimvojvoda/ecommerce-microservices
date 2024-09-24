package com.vojvoda.inventoryservice;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldCheckIfInStock() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("skuCode", "iphone_15")
                .queryParam("quantity", 5)
                .when()
                .get("/api/inventory")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("true"));
    }
}

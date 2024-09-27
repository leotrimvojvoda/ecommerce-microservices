package com.vojvoda.apigatewayms.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
public class Routes {


    @Value("${product-service-url}")
    private String PRODUCT_SERVICE_URL;

    @Value("${order-service-url}")
    private String ORDER_SERVICE_URL;

    @Value("${inventory-service-url}")
    private String INVENTORY_SERVICE_URL;

    @Value("${product-service-base-path}")
    private String PRODUCT_SERVICE_BASE_PATH;

    @Value("${order-service-base-path}")
    private String ORDER_SERVICE_BASE_PATH;

    @Value("${inventory-service-base-path}")
    private String INVENTORY_SERVICE_BASE_PATH;


    // Product Service
    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {

        return GatewayRouterFunctions.route("product_service")
                .route(RequestPredicates.path(PRODUCT_SERVICE_BASE_PATH),
                        HandlerFunctions.http(PRODUCT_SERVICE_URL))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("productServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceBaseRoute() {
        return GatewayRouterFunctions.route("product_service_swagger")
                .route(RequestPredicates.path("/aggregate/product-service/v3/api-docs"),
                        HandlerFunctions.http(PRODUCT_SERVICE_URL))
                .filter(setPath("/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("productServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    // Order Service
    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {

        return GatewayRouterFunctions.route("order_service")
                .route(RequestPredicates.path(ORDER_SERVICE_BASE_PATH),
                        HandlerFunctions.http(ORDER_SERVICE_URL))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("orderServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceBaseRoute() {
        return GatewayRouterFunctions.route("order_service_swagger")
                .route(RequestPredicates.path("/aggregate/order-service/v3/api-docs"),
                        HandlerFunctions.http(ORDER_SERVICE_URL))
                .filter(setPath("/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("orderServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    // Inventory Service
    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute() {

        return GatewayRouterFunctions.route("inventory_service")
                .route(RequestPredicates.path(INVENTORY_SERVICE_BASE_PATH),
                        HandlerFunctions.http(INVENTORY_SERVICE_URL))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventoryServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceBaseRoute() {
        return GatewayRouterFunctions.route("inventory_service_swagger")
                .route(RequestPredicates.path("/aggregate/inventory-service/v3/api-docs"),
                        HandlerFunctions.http(INVENTORY_SERVICE_URL))
                .filter(setPath("/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventoryServiceSwaggerCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return GatewayRouterFunctions.route("fallbackRoute")
                .GET("/fallbackRoute",
                        request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("Service Unavailable, please try again later"))
                .build();
    }
}
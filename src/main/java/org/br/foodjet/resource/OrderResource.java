package org.br.foodjet.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.br.foodjet.resource.common.OrderStatus;
import org.br.foodjet.repository.entity.OrderRequest;
import org.br.foodjet.resource.response.OrderResponse;
import org.br.foodjet.service.Service;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/requestfood")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class OrderResource {

    @Inject
    Service service;

    @GET
    @Path("/all")
    @Tag(name = "Order", description = "FoodJet")
    @APIResponse(
        responseCode = "200",
        content = @Content(
            mediaType = APPLICATION_JSON,
            schema = @Schema(implementation = OrderResponse.class)
        )
    )
    @APIResponse(responseCode = "500", description = "Internal server error")
    @APIResponse(responseCode = "502", description = "Bad gateway")
    public List<OrderResponse> listAllRequestsFoods() {
        return service.listAllOrder();
    }

    @GET
    @Path("/{id}")
    @Tag(name = "Order", description = "FoodJet")
    @APIResponse(
        responseCode = "200",
        content = @Content(
            mediaType = APPLICATION_JSON,
            schema = @Schema(implementation = OrderResponse.class)
        )
    )
    @APIResponse(responseCode = "500", description = "Internal server error")
    @APIResponse(responseCode = "502", description = "Bad gateway")
    public OrderResponse findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    @APIResponse(
        responseCode = "200",
        content = @Content(
            mediaType = APPLICATION_JSON,
            schema = @Schema(implementation = OrderResponse.class)
        )
    )
    @APIResponse(responseCode = "500", description = "Internal server error")
    @APIResponse(responseCode = "502", description = "Bad gateway")
    @Tag(name = "Order", description = "FoodJet")
    public List<OrderResponse> listByName(@Valid @NotBlank @QueryParam("client_name") String clientName) {
        return service.findByName(clientName);
    }

    @POST
    @Tag(name = "Order", description = "FoodJet")
    @APIResponse(
        responseCode = "200",
        content = @Content(
            mediaType = APPLICATION_JSON,
            schema = @Schema(implementation = OrderResponse.class)
        )
    )
    @APIResponse(responseCode = "500", description = "Internal server error")
    @APIResponse(responseCode = "502", description = "Bad gateway")
    public OrderResponse create(OrderRequest order) {
        return service.createOrder(order);
    }

    @PATCH
    @Path("/{id}")
    @Tag(name = "Order", description = "FoodJet")
    @APIResponse(
        responseCode = "200",
        content = @Content(
            mediaType = APPLICATION_JSON,
            schema = @Schema(implementation = OrderResponse.class)
        )
    )
    @APIResponse(responseCode = "500", description = "Internal server error")
    @APIResponse(responseCode = "502", description = "Bad gateway")
    public OrderResponse update(@Valid @NotNull @PathParam("id") Long id,
        @NotNull @QueryParam("status") OrderStatus status) {
        return service.updateOrder(status, id);
    }
}

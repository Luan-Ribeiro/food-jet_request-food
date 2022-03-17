package org.br.foodjet.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.br.foodjet.exception.to.ErrorDetailTO;
import org.br.foodjet.repository.entity.OrderRequest;
import org.br.foodjet.resource.response.OrderResponse;
import org.br.foodjet.service.OrderService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/requestfood")
@RequiredArgsConstructor
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Tag(name = "Order", description = "Manage orders request food")
public class OrderResource {

    private final OrderService orderService;

    @GET
    @Path("/all")
    @APIResponse(responseCode = "200", description = "Gets all orders", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = OrderResponse.class)
    ))
    @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @Operation(
        summary = "getAllOrders",
        description = "Get all orders"
    )
    public List<OrderResponse> listAllRequestsFoods() {
        return orderService.listAllOrder();
    }

    @GET
    @Path("/{id}")
    @APIResponse(responseCode = "200", description = "Gets order", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = OrderResponse.class)
    ))
    @APIResponse(responseCode = "404", description = "Id not found", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @Operation(
        summary = "getOrderById",
        description = "Get order by id"
    )
    public OrderResponse findById(@NotNull @PathParam("id") Long id) {
        return orderService.findById(id);
    }

    @GET
    @APIResponse(responseCode = "200", description = "Gets order", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = OrderResponse.class)
    ))
    @APIResponse(responseCode = "404", description = "Client name not found", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @Operation(
        summary = "listByName",
        description = "List orders by client name"
    )
    public List<OrderResponse> listByName(@NotBlank @QueryParam("clientName") String clientName) {
        return orderService.findByName(clientName);
    }

    @POST
    @APIResponse(responseCode = "201", description = "Created order request food", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = OrderResponse.class)
    ))
    @APIResponse(responseCode = "400", description = "Bad Request", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(
        mediaType = APPLICATION_JSON,
        schema = @Schema(implementation = ErrorDetailTO.class)
    ))
    @Operation(
        summary = "createOrderRequest",
        description = "Create a new order request food"
    )
    public Response create(@Valid OrderRequest order) {
        return orderService.createOrder(order);
    }
}

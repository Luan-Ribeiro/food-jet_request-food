package org.br.foodjet.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.br.foodjet.repository.entity.OrderRequest;
import org.br.foodjet.exception.to.ErrorDetailTO;
import org.br.foodjet.resource.response.OrderResponse;
import org.br.foodjet.service.OrderService;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.jboss.resteasy.reactive.ResponseStatus;

@Path("/requestfood")
@RequiredArgsConstructor
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class OrderResource {

    private final OrderService orderService;

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
    public List<OrderResponse> listAllRequestsFoods() {
        return orderService.listAllOrder();
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
    public OrderResponse findById(@NotNull @PathParam("id") Long id) {
        return orderService.findById(id);
    }

    @GET
    @APIResponse(
        responseCode = "200",
        content = @Content(
            mediaType = APPLICATION_JSON,
            schema = @Schema(implementation = OrderResponse.class)
        )
    )
    @Tag(name = "Order", description = "FoodJet")
    public List<OrderResponse> listByName(@NotBlank @QueryParam("clientName") String clientName) {
        return orderService.findByName(clientName);
    }

    @POST
    @Tag(name = "Order", description = "FoodJet")
    @APIResponses(
        value = {
            @APIResponse(
                responseCode = "200",
                content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = OrderResponse.class)
                )
            ),
            @APIResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = ErrorDetailTO.class)
                )
            )
        }
    )
    @ResponseStatus(value = HttpStatus.SC_CREATED)
    public OrderResponse create(@Valid OrderRequest order) {
        return orderService.createOrder(order);
    }
}

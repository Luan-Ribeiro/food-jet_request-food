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
import org.br.foodjet.resource.request.OrderRequest;
import org.br.foodjet.resource.response.OrderResponse;
import org.br.foodjet.service.OrderService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/requestfood")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @GET
    @Path("/all")
    @Tag(name = "Order", description = "FoodJet")
    public List<OrderResponse> listAllRequestsFoods() {
        return orderService.listAllOrder();
    }

    @GET
    @Path("/{id}")
    @Tag(name = "Order", description = "FoodJet")
    public OrderResponse findById(@PathParam("id") Long id) {
        return orderService.findById(id);
    }

    @GET
    @Tag(name = "Order", description = "FoodJet")
    public List<OrderResponse> listByName(@Valid @NotBlank @QueryParam("client_name") String clientName) {
        return orderService.findByName(clientName);
    }

    @POST
    @Tag(name = "Order", description = "FoodJet")
    public OrderResponse create(OrderRequest order) {
        return orderService.createOrder(order);
    }

    @PATCH
    @Path("/{id}")
    @Tag(name = "Order", description = "FoodJet")
    public OrderResponse update(@Valid @NotNull @PathParam("id") Long id,
        @NotNull @QueryParam("status") OrderStatus status) {
        return orderService.updateOrder(status, id);
    }
}

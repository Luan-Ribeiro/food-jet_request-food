package org.br.foodjet.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.resource.request.OrderRequest;
import org.br.foodjet.resource.response.OrderResponse;
import org.br.foodjet.service.OrderService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Slf4j
@Path("/requestfood")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @GET
    @Path("/all")
    @Tag(name = "Order", description = "Pix transactions")
    public List<OrderResponse> listAllRequestsFoods() {
        return orderService.listAllOrder();
    }

    @POST
    @Tag(name = "Order", description = "Pix transactions")
    public List<OrderResponse> create(List<OrderRequest> items){
       return orderService.createOrder(items);
    }
}

package org.br.foodjet.integration;

import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import org.br.foodjet.resource.response.OrderResponseTO;
import org.br.foodjet.resource.to.OrderRequestTO;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/inventory")
public class InventoryResource {

    @Inject
    @RestClient
    InventoryService service;

    @PUT
    @Path("/ingredients")
    public OrderResponseTO verifyOrderAndFlushIngredients(@RequestBody OrderRequestTO orderRequestTO) {
        return service.verifyIfOrderIsPossible(orderRequestTO);
    }
}
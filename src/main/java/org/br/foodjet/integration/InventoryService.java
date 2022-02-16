package org.br.foodjet.integration;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import org.br.foodjet.resource.response.OrderResponseTO;
import org.br.foodjet.resource.to.OrderRequestTO;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/inventory")
@RegisterRestClient(configKey = "inventory-api")
public interface InventoryService {

    @PUT
    @Path("/ingredients")
    OrderResponseTO verifyIfOrderIsPossible(@RequestBody OrderRequestTO orderRequestTO);


}

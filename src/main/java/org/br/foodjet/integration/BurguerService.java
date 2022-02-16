package org.br.foodjet.integration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.br.foodjet.resource.response.BurguerResponseTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/burguer")
@RegisterRestClient(configKey = "inventory-api")
public interface BurguerService {

    @GET
    BurguerResponseTO findByName(@QueryParam String nameBurguer);
}

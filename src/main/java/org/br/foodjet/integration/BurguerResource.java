package org.br.foodjet.integration;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.br.foodjet.resource.response.BurguerResponseTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/burguer")
public class BurguerResource {

    @Inject
    @RestClient
    BurguerService service;

    @GET
    public BurguerResponseTO findByName(@QueryParam("nameBurguer") String nameBurguer) {
        return service.findByName(nameBurguer);
    }
}
package com.apave.tba.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by A4811584 on 27/03/2017.
 */
@Path("/salutations")
public class HelloService {

    @GET
    @Path("/hello/{name}")
    public Response hello(@PathParam("name") String name) {
        if(name == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok("Hello " + name + " !").build();
    }

    @GET
    @Path("/goodbye/{name}")
    public Response goodbye(@PathParam("name") String name) {
        if(name == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok("Ciao " + name + " !").build();
    }
}

package com.apave.tba.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created by A4811584 on 27/03/2017.
 */
@Path("/hello")
public class HelloService {

    @GET
    @Path("/{name}")
    public String hello(@PathParam("name") String name){
        return "Hello " + name + " !";
    }

}

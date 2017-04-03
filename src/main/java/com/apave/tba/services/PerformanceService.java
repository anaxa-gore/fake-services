package com.apave.tba.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Random;

/**
 * Created by A4811584 on 27/03/2017.
 */
@Path("/performances")
public class PerformanceService {
    @GET
    @Path("/ping/{max}")
    public Response getInfos(@PathParam("max") int maxValue) {
        return Response.ok("Un message de réponse en " + wait(maxValue) + "ms").build();
    }

    @GET
    @Path("/data/{max}")
    public Response getData(@PathParam("max") int maxValue, @QueryParam("datas") String datas) {
        return Response.ok("Donnée renvoyée en " + wait(maxValue) + "ms : ", datas).build();
    }

    private int wait(int max) {
        Random r = new Random();

        int waitingDelay = r.nextInt(max);
        try {
            Thread.sleep(waitingDelay);
        } catch (InterruptedException ie) {
            // On s'en fout !
            return waitingDelay;
        }
        return waitingDelay;
    }
}

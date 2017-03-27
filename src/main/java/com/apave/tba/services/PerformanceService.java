package com.apave.tba.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.Random;

/**
 * Created by A4811584 on 27/03/2017.
 */
@Path("/performances")
public class PerformanceService {
    @GET
    @Path("/{max}")
    public Response getInfos(@PathParam("max") int maxValue) {
        Random r = new Random();

        int waitingDelay = r.nextInt(maxValue);
        try {
            Thread.sleep(waitingDelay);
        } catch (InterruptedException e) {
            // On s'en fout
        } finally {
            return Response.ok("Un message de réponse en " + waitingDelay + "ms").build();
        }
    }
}

package com.apave.tba.services;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.junit.Assert.assertEquals;

/**
 * Created by A4811584 on 27/03/2017.
 */
public class HelloServiceTest {
    private static final String BASE_URI = "http://localhost:9998";//
    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig config = new ResourceConfig(HelloService.class);

        server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);

        Client client = ClientBuilder.newClient();
        target = client.target(BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testHelloWithName() {
        assertEquals("Hello Tom !", target.path("hello").path("Tom").request().get().readEntity(String.class));
        assertEquals("Hello Jay !", target.path("hello").path("Jay").request().get().readEntity(String.class));
    }

    @Test
    public void testHelloWithNoName() {
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), target.path("hello").path("").request().get().getStatus());
    }

    @Test
    public void testHelloWithNoName2() {
        assertEquals("Hello Yop !", target.path("hello").path("Yop").request().get().readEntity(String.class));
    }
}


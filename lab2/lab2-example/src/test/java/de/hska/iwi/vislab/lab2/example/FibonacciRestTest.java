package de.hska.iwi.vislab.lab2.example;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class FibonacciRestTest {

  private HttpServer server;
  private WebTarget target;

  @Before
  public void setUp() throws Exception {
    // start the server
    server = Main.startServer();
    // create the client
    Client c = ClientBuilder.newClient();

    // uncomment the following line if you want to enable
    // support for JSON in the client (you also have to uncomment
    // dependency on jersey-media-json module in pom.xml and
    // Main.startServer())
    // --
    // c.configuration().enable(new
    // org.glassfish.jersey.media.json.JsonJaxbFeature());

    target = c.target(Main.BASE_URI);
  }

  @After
  public void tearDown() throws Exception {
    server.shutdown();
  }

  /** Test to see that the message "Got it!" is sent in the response. */
  @Test
  public void testGetFirst() {
    String responseMsg =
        target.path("nextFibonacciNumber").request().accept(MediaType.TEXT_PLAIN).get(String.class);
    assertEquals("1", responseMsg);
  }

  @Test
  public void testIncrement() {

    target
        .path("nextFibonacciNumber")
        .request()
        .accept(MediaType.TEXT_PLAIN)
        .post(Entity.text("content"), String.class);
    String responseMsg1 =
        target.path("nextFibonacciNumber").request().accept(MediaType.TEXT_PLAIN).get(String.class);
    assertEquals("1", responseMsg1);
    target
        .path("nextFibonacciNumber")
        .request()
        .accept(MediaType.TEXT_PLAIN)
        .post(Entity.text("content"), String.class);
    String responseMsg2 =
        target.path("nextFibonacciNumber").request().accept(MediaType.TEXT_PLAIN).get(String.class);
    assertEquals("2", responseMsg2);
    target
        .path("nextFibonacciNumber")
        .request()
        .accept(MediaType.TEXT_PLAIN)
        .post(Entity.text("content"), String.class);
    String responseMsg3 =
        target.path("nextFibonacciNumber").request().accept(MediaType.TEXT_PLAIN).get(String.class);
    assertEquals("3", responseMsg3);
  }

  @Test
  public void resetFibonacci() {
    target
        .path("nextFibonacciNumber")
        .request()
        .accept(MediaType.TEXT_PLAIN)
        .post(Entity.text("content"), String.class);
    String responseMsg =
        target.path("nextFibonacciNumber").request().accept(MediaType.TEXT_PLAIN).get(String.class);
    assertNotSame("1", responseMsg);
    //Reset
    target
        .path("nextFibonacciNumber")
        .request()
        .accept(MediaType.TEXT_PLAIN)
        .delete();
      String responseAfterReset =
              target.path("nextFibonacciNumber").request().accept(MediaType.TEXT_PLAIN).get(String.class);
    assertEquals("1", responseAfterReset);
  }
}
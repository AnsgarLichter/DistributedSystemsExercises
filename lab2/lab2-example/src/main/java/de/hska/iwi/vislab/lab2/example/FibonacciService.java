package de.hska.iwi.vislab.lab2.example;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("nextFibonacciNumber")
public class FibonacciService {
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String getCurrentFibonacci() {
    FibonacciRessource fib = FibonacciRessource.getInstance();
    return "Current: " + fib.getFibonacci();
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getCurrentFibonacciPlainText() {
    FibonacciRessource fib = FibonacciRessource.getInstance();
    return Integer.toString(fib.getFibonacci());
  }

  @POST
  @Produces(MediaType.TEXT_PLAIN)
  public String incrementIndex() {
    FibonacciRessource fib = FibonacciRessource.getInstance();
    fib.nextFibonacci();
    return Integer.toString(fib.getFibonacci());
  }

  @DELETE
  @Produces(MediaType.TEXT_PLAIN)
  public void reset() {
    FibonacciRessource fib = FibonacciRessource.getInstance();
    fib.reset();
  }
}
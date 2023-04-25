package de.hska.iwi.vislab.lab2.example;

public class FibonacciRessource {
    private static final FibonacciRessource obj = new FibonacciRessource();
    int index = 1;
    int currentFibonacci = 1;
  
    private FibonacciRessource() {}
  
    public static FibonacciRessource getInstance() {
      return obj;
    }
  
    public int nextFibonacci() {
      index += 1;
      currentFibonacci = calcFibonacci(index);
      return currentFibonacci;
    }
  
    public int getFibonacci() {
      return currentFibonacci;
    }
  
    private int calcFibonacci(int n) {
      if (n == 0) {
        return 0;
      } else if (n == 1) {
        return 1;
      }
      return calcFibonacci(n - 1) + calcFibonacci(n - 2);
    }
  
    public void reset() {
      index = 1;
      currentFibonacci = 1;
    }
  }
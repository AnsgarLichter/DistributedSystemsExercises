package de.hska.iwi.vislab.lab1.example.ws;

import javax.jws.WebService;

@WebService(endpointInterface = "de.hska.iwi.vislab.lab1.example.ws.FibonacciServiceIntf")
public class FibonacciServiceImpl implements FibonacciServiceIntf {
    
    @Override
	public int getFibonacci(int i) {
		return fib(i);
	}

    public int fib(int i){
        if(i < 2)
            return i;
        return fib(i-1) + fib(i-2);
    }

}

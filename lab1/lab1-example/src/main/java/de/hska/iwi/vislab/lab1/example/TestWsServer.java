package de.hska.iwi.vislab.lab1.example;

import javax.swing.JOptionPane;
import javax.xml.ws.Endpoint;

import de.hska.iwi.vislab.lab1.example.ws.BuecherServiceImpl;
import de.hska.iwi.vislab.lab1.example.ws.FibonacciServiceImpl;


/** Testserver fuer den Webservice */
public class TestWsServer {
	public static void main(final String[] args) {
		String url = (args.length > 0) ? args[0]
				: "http://localhost:4434/buecherservice";
		String fibUrl = "http://localhost:4434/fibonacciService";
		Endpoint ep = Endpoint.publish(url, new BuecherServiceImpl());
		Endpoint fib = Endpoint.publish(fibUrl, new FibonacciServiceImpl());
		JOptionPane.showMessageDialog(null, "TestWsServer beenden");
		ep.stop();
		fib.stop();
	}
}

package com.sap.webservice.server;

import javax.xml.ws.Endpoint;

import com.sap.webservice.implement.CalculateServiceImplement;

public class Server {
	
	public static void main(String[] args) {
		String address = "http://localhost:8888/cal";
		Endpoint.publish(address, new CalculateServiceImplement());
	}
	
}

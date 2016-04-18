package com.sap.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.sap.webservice.interfaces.CalculateService;
import com.sap.webservice.server.Server;

public class Client {

	public static void main(String[] args) {
		try {
			URL url = new URL("http://localhost:8888/cal");
			QName qname = new QName("http://implement.webservice.sap.com/", "CalculateServiceImplementService");
			
		    Service service = Service.create(url,qname);
		    CalculateService ser = service.getPort(CalculateService.class);
		    
		    System.out.println(ser.sum(12, 7));
		    
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

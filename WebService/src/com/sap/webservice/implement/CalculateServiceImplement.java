package com.sap.webservice.implement;

import javax.jws.WebService;

import com.sap.webservice.interfaces.CalculateService;

@WebService(endpointInterface="com.sap.webservice.interfaces.CalculateService")
public class CalculateServiceImplement implements CalculateService {

	@Override
	public int sum(int a, int b) {
		// TODO Auto-generated method stub
		System.out.println("a : " + a + " b : " + b + "a + b = " + (a+b));
		return a+b;
	}

	@Override
	public int minus(int a, int b) {
		// TODO Auto-generated method stub
		System.out.println("a : " + a + " b : " + b + "a - b = " + (a-b));
		return a-b;
	}

}

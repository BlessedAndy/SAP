package com.sap.webservice.interfaces;

import javax.jws.WebService;

@WebService
public interface CalculateService {
	
	public int sum(int a, int b);
	
	public int minus(int a, int b);

}

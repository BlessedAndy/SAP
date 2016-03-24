package com.sap.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TestDate {
	
	public static void main(String[] args) {
		
		for(int i=1;i<9;i++){
			System.out.println(addDate("2016-03-24 03:01:00 AM", 30000*i));

		}
		
	}
	
	
	public static String addDate(String timestamp, int x) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");// 24小时�?
		Date date = null;
		try {
			date = format.parse(timestamp);
			System.out.println("date : "+date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MILLISECOND, x);
		date = cal.getTime();
		System.out.println("front:" + date);
		System.out.println("format.format(date): "+format.format(date));
		return format.format(date);
	}

}

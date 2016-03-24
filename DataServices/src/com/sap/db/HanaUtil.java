package com.sap.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 
 * @author Andy Zhang
 *
 */
public class HanaUtil {

	static int counter;
	static ResultSet result;
	static String SCHED_NAME = null;
	static String JOB_GUID = null;
	static ArrayList<String> JOB_NAMES;
	static int interval = 1;
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		//JB_Y02_PM_WEB_FIN_SAVEAMT_DUE_DELTA 是坏的
		
	/*	String[] ex = { "JB_Y02_PM_WEB_FIN_SAVEAMT_DUE_DELTA" };
		int i = 1;
		for (String JobName : excludeJobNames(getJobNames(), ex)) {
			System.out.println(i++ + " : " + JobName);
		}
		
		insertBatch(excludeJobNames(getJobNames()));*/
		
		//Mar 10, 2016 12:01:00.0 AM
		//yyyy-MM-dd HH:mm:ss
		//MM dd, yyyy HH:mm:ss.f 
		//2016-03-11 01:09:00
		/*insert("JB_Y01TB_BASE_PEDS_DELTA");
		System.out.println(addDate("2016-03-10 12:01:00 AM", 30000));*/
		
//		System.out.println(getGUID("JB_Y01TB_BASE_PEDS_DELTA"));
//		System.out.println(addDate("2016-03-10 12:01:00 AM", 30000));
	/*	int i=1;
		for(String JOB_NAME : getJobNames()){
			if(!JOB_NAME.equalsIgnoreCase("JB_Y02_PM_WEB_FIN_SAVEAMT_DUE_DELTA")){
				System.out.println(i+" : "+JOB_NAME);
				i++;
			}
		
		}*/
		/*for(int i=1;i<10;i++){
			System.out.println(addDate("2016-03-10 12:01:00 AM", 30000*i));
		}*/
		
		/*String[] ins = { "JB_Y35LH_SHEET_COMP_NOPK_8AM", "JB_Y35LH_SHEET_COMP_NOPK_4PM","JB_Y01WEB_PAY_RATE_NOPK_12AM","JB_Y35LH_SHEET_COMP_NOPK_12AM"};
		for (int i = 0; i < ins.length; i++) {
			insert(ins[i]);
		}*/
		/**
		 * Add scheule for all DELTA job except ...
		 */
		//=======================================================
		//String[] exc = { "JB_Y02_PM_WEB_FIN_SAVEAMT_DUE_DELTA" };
		//insertBatch(excludeJobNames(getJobNames(), exc));
		//=======================================================
//		insertBatch(getJobNames());
	}

	/**
	 * 生成数据库连接
	 * @return
	 */
	public static Connection getConnection(){
//		Connection conn = DBUtil.getHANAConnection();
//		HED AB_SAP_POC  10.10.141.177:33915
		//DS_REPOSITORY	10.10.141.177  DS_REPOSITORY Init1234
		//DS_REPOSITORY_2	10.5.128.2:32915 	DS_REPOSITORY Init1234
		Connection conn = null;
		try {
			Class.forName("com.sap.db.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:sap://10.5.128.2:32915?reconnect=true","DS_REPOSITORY","Init1234"); 
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		}catch (SQLException e) {	
			e.printStackTrace();
		}
		return conn;
	}
}

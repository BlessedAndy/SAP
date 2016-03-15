package com.sap.db.hana;

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
		String create = "CREATE TABLE AL_SCHEDULE_INFO(OBJECT_KEY INT,"
				+ "SCHED_NAME VARCHAR(100),"
				+ "JOB_GUID VARCHAR(100),"
				+ "START_TIME VARCHAR(100),"
				+ "JOB_COMMAND VARCHAR(100),"
				+ "MACHINE_NAME VARCHAR(100),"
				+ "AT_ID INT,"
				+ "ACTIVE INT,"
				+ "RECURRENCE_TYPE VARCHAR(100),"
				+ "RECURRENCE_PATTERN VARCHAR(100),"
				+ "HOST_NAME VARCHAR(100),"
				+ "PORT INT,"
				+ "HOUR_BEGIN TIMESTAMP,"
				+ "HOUR_END TIMESTAMP,"
				+ "INTERVAL_OR_NOT INT,"
				+ "USES_PARAMFILE INT,"
				+ "SCHEDULED_IN VARCHAR(100))";
 * @author I310003
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

	/*public static void insertBatch(ArrayList<String> JOB_NAMES){

		for(int i=0;i < JOB_NAMES.size();i++){
			String JOB_NAME = JOB_NAMES.get(i);
			insert(JOB_NAME);
		}
	}
	
	public static void insert(String JOB_NAME){
		
		Connection conn = getConnection();
//		String query = "SELECT MAX(OBJECT_KEY) FROM AL_SCHEDULE_INFO";
//		String drop = "DROP TABLE AL_SCHEDULE_INFO";
//		String sql = "SELECT *FROM AL_SCHEDULE_INFO";
		
		for (int i = 0; i < JOB_NAMES.size(); i++) {
			JOB_NAME = JOB_NAMES.get(i);
			counter = queryMax() + 1;
			
			String insertSQL1 = "insert into AL_SCHED_INFO values(counter,"	//"OBJECT_KEY", int not null
					+ JOB_NAME+","				//SCHED_NAME String not null
					+ getGUID(JOB_NAME)+","    //JOB_GUID  String  not null
					+ addDate("2016-03-10 12:01:00 AM", 30000)+","		//START_TIME LONGDATE NOT NULL
					+ "-R\"Repo.txt\"  -G\""
					+ ""+getGUID(JOB_NAME)+""
					+ "\" -t5 -T14 -KspPRD,"  //JOB_COMMAND String not null
					+ ","										//MACHINE_NAME String
					+ "-1,"										//AT_ID   int
					+ "0,"										//ACTIVE  int not null
					+ "WEEKLY,"								//RECURRENCE_TYPE  String
					+ "-2147483521,"						//RECURRENCE_PATTERN  int
					+ "ahradq01.ab-insurance.com,"			//HOST_NAME String
					+ "3500,"									//PORT	int
					+ "'0',"									//HOUR_BEGIN	int
					+ "'0',"									//HOUR_END	int
					+ "0,"										//INTERVAL	int
					+ "1,"										//USES_PARAMFILE	int
					+ "'JS')";
																																								 
			
			
			
			String insertSQL = "insert into AL_SCHED_INFO"
					+ " values("+counter+","		//"OBJECT_KEY", int not null
					+ "'"+JOB_NAME+"',"				//SCHED_NAME String not null
					+ "'"+getGUID(JOB_NAME)+"',"    //JOB_GUID  String  not null
					+ ""+addDate("2016-03-10 12:01:00 AM", counter)+","		//START_TIME LONGDATE NOT NULL
					+ "'-R\"Repo.txt\"  -G\""
					+ ""+getGUID(JOB_NAME)+""
					+ "\" -t5 -T14 -KspPRD,'"  //JOB_COMMAND String not null
					+ ","										//MACHINE_NAME String
					+ "-1,"										//AT_ID   int
					+ "0,"										//ACTIVE  int not null
					+ "'WEEKLY',"								//RECURRENCE_TYPE  String
					+ "-2147483521,"						//RECURRENCE_PATTERN  int
					+ "'ahradq01.ab-insurance.com',"			//HOST_NAME String
					+ "3500,"									//PORT	int
					+ "'0',"									//HOUR_BEGIN	int
					+ "'0',"									//HOUR_END	int
					+ "0,"										//INTERVAL	int
					+ "1,"										//USES_PARAMFILE	int
					+ "'JS')";									//SCHEDULED_IN	String
		}
			
			//2 ;94        ;JB_Y01WEB_BAS_CODELIST_NOPK_12AM         ;2b5782b9-4fc1-46f9-9d1a-504d90b0bad2;Mar 11, 2016 12:01:00.0 AM;-R\"Repo_2.txt\"  -G\"2b5782b9_4fc1_46f9_9d1a_504d90b0bad2\" -t5 -T14 -KspPRD;            ;-1   ;0     ;WEEKLY         ;-2,147,483,521    ;ahradq01.ab-insurance.com;3,500;0         ;0       ;0       ;1             ;JS          
		
			String NoPKDeltaInsertSql = "insert into AL_SCHED_INFO values("+counter+","
					+ "'"+JOB_NAME+"',"
							+ "'"+getGUID(JOB_NAME)+"',"
									+ "'"+addDate("2016-03-10 12:01:00 AM", 30000*interval)+"'"
											+ ",'-R\"Repo.txt\"  -G\""+getGUID(JOB_NAME)+"\" -t5 -T14 -KspPRD',"
													+ "'',-1,0,'WEEKLY','-2147483521','ahradq01.ab-insurance.com',"
													+ "3500,'0','0',0,1,'JS')";
			
			String NoPKInsertSql = "insert into AL_SCHED_INFO values("+counter+","
					+ "'"+JOB_NAME+"',"
							+ "'"+getGUID(JOB_NAME)+"',"
									+ "'"+addDate("2016-03-10 12:01:00 AM", 30000*interval)+"'"
											+ ",'-R\"Repo.txt\"  -G\""+getGUID(JOB_NAME)+"\" -t5 -T14 -KspPRD',"
													+ "'',-1,0,'WEEKLY','-2147483521','ahradq01.ab-insurance.com',"
													+ "3500,'0','0',0,1,'JS')";
			
			String DeltaInsertSql = "insert into AL_SCHED_INFO values("+counter+","
					+ "'"+JOB_NAME+"',"
							+ "'"+getGUID(JOB_NAME)+"',"
									+ "'"+addDate("2016-03-10 12:01:00 AM", 30000*interval)+"'"
											+ ",'-R\"Repo.txt\"  -G\""+getGUID(JOB_NAME)+"\" -t5 -T14 -KspPRD',"
													+ "'',-1,0,'WEEKLY','-2147483521','ahradq01.ab-insurance.com',"
													+ "3500,'0','0',0,1,'JS')";
			interval++;
		try {
			PreparedStatement pstmt = conn.prepareStatement(DeltaInsertSql);
//			pstmt.executeUpdate();
		    pstmt.execute();
		    if (!conn.getAutoCommit()) {   
		        conn.commit();
		      }
		    conn.close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> excludeJobNames(ArrayList<String> JOB_NAMES, String[] EX_NAMES){
		for(String JOB_NAME : getJobNames()){
			for(int i=0; i < EX_NAMES.length; i++){
				if(JOB_NAME.equalsIgnoreCase(EX_NAMES[i])){
					JOB_NAMES.remove(EX_NAMES[i]);
				}
			}
		}
		return JOB_NAMES;
	}


	private static ArrayList<String> excludeJobNames(ArrayList<String> jobNames) {
		// TODO Auto-generated method stub
		for(String JOB_NAME : getJobNames()){
			for(int i=0; i < jobNames.size(); i++){
				if(JOB_NAME.equalsIgnoreCase(jobNames.get(i))){
					JOB_NAMES.remove(jobNames.get(i));
				}
			}
		}
		return JOB_NAMES;
	}
	
	public static ArrayList<String> getJobNames() {
		JOB_NAMES = new ArrayList<String>();
		PreparedStatement pstmt;
		try {
			pstmt = getConnection()
					.prepareStatement("SELECT NAME FROM AL_LANG where OBJECT_TYPE=0 and NAME LIKE 'JB_Y%_DELTA'");
			result = pstmt.executeQuery();
			for (int i = 0; result.next(); i++) {
				if (result.getString(1) != null) {
					JOB_NAMES.add(result.getString(1));
				} else {
					System.out.println("NULL!");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JOB_NAMES;
	}
	
	public static String addDate(String timestamp, int x) {
		//MMM dd, yyyy hh:mm:ss.S a                    2016-03-10 12:01:00 AM
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss a",Locale.ENGLISH);// 24小时制
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//12小时制
		Date date = null;
		try {
			date = format.parse(timestamp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (date == null)
			return "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MILLISECOND, x);
//		cal.add(Calendar.HOUR_OF_DAY, x);// 24小时制
		 //关键的  
//		 Date newDate = DateUtils.addMilliseconds(cal,100))；  
		 //就是对cd加上100毫秒,减100秒就是加上-100毫秒      
		  
		 //将生成的时间输出为字符串  
//		 String newDate_str = sDateFormat.format(newDate);
		// cal.add(Calendar.HOUR, x);12小时制
		date = cal.getTime();
//		System.out.println("front:" + date);
		cal = null;
		return format.format(date);
	}

	public static String getGUID(String JOB_NAME){
		PreparedStatement pstmt;
		try {
			pstmt = getConnection().prepareStatement("SELECT GUID FROM AL_LANG WHERE NAME = '"+JOB_NAME+"'");
			result = pstmt.executeQuery();
			result.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			JOB_GUID = result.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JOB_GUID;
	}
		
	public static String getSchedName(String GUID){
		PreparedStatement pstmt;
		try {
			pstmt = getConnection().prepareStatement("SELECT SCHED_NAME FROM AL_SCHEDULE_INFO WHERE JOB_GUID = "+GUID+"");
			result = pstmt.executeQuery();
			result.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			SCHED_NAME = result.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SCHED_NAME;
	}
	
	
	public static int queryMax(){
		PreparedStatement pstmt;
		try {
			pstmt = getConnection().prepareStatement("SELECT MAX(OBJECT_KEY) FROM AL_SCHED_INFO");
			result = pstmt.executeQuery();
			result.next();
			counter = result.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(counter);
		return counter;
	}
	 */
	/**
	 * 生成数据库连接
	 * @return
	 */
	public static Connection getConnection(){
		//HD5: 10.128.245.61:30515	"i310003","Initial1234"
		//HED AB_SAP_POC  10.10.141.177:33915
		//DS_REPOSITORY	10.5.128.2:32915  DS_REPOSITORY Init1234
		//DS_REPOSITORY_2	10.5.128.2:32915 	DS_REPOSITORY Init1234
		String url = "jdbc:sap://10.5.128.2:32915?reconnect=true";
		Connection conn = null;
		try {
			Class.forName("com.sap.db.jdbc.Driver");
			conn = DriverManager.getConnection(url,"DS_REPOSITORY","Init1234"); 
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		}catch (SQLException e) {	
			e.printStackTrace();
		}
		return conn;
	}
}

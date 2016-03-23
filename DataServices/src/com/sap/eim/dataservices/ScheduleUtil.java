package com.sap.eim.dataservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.sap.db.HanaUtil;

public class ScheduleUtil {
	
	static int counter;
	static ResultSet result;
	static String SCHED_NAME = null;
	static String JOB_GUID = null;
	static ArrayList<String> JOB_NAMES;
	static int interval = 1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] ins = { "JB_Y35LH_SHEET_COMP_NOPK_8AM", "JB_Y35LH_SHEET_COMP_NOPK_4PM","JB_Y01WEB_PAY_RATE_NOPK_12AM","JB_Y35LH_SHEET_COMP_NOPK_12AM"};
		for (int i = 0; i < ins.length; i++) {
			insert(ins[i]);
		}

	}
	
	public static void insertBatch(ArrayList<String> JOB_NAMES){

		for(int i=0;i < JOB_NAMES.size();i++){
			String JOB_NAME = JOB_NAMES.get(i);
			insert(JOB_NAME);
		}
	}
	
	public static void insertNOPK(String JOB_NAME, String NAME_EXTEND) {
		Connection conn = HanaUtil.getConnection();
		//
		String NoPKDeltaInsertSql = "insert into AL_SCHED_INFO values(" + counter + "," + "'" + JOB_NAME + "'," + "'"
				+ getGUID(JOB_NAME) + "'," + "'" + addDate("2016-03-10 12:01:00 AM", 30000 * interval) + "'"
				+ ",'-R\"Repo.txt\"  -G\"" + getGUID(JOB_NAME) + "\" -t5 -T14 -KspPRD',"
				+ "'',-1,0,'WEEKLY','-2147483521','ahradq01.ab-insurance.com'," + "3500,'0','0',0,1,'JS')";

		String NoPKInsertSql = "insert into AL_SCHED_INFO values(" + counter + "," + "'" + JOB_NAME + NAME_EXTEND +"'," + "'"
				+ getGUID(JOB_NAME) + "'," + "'" + addDate("2016-03-10 12:01:00 AM", 30000 * interval) + "'"
				+ ",'-R\"Repo.txt\"  -G\"" + getGUID(JOB_NAME) + "\" -t5 -T14 -KspPRD',"
				+ "'',-1,0,'WEEKLY','-2147483521','ahradq01.ab-insurance.com'," + "3500,'0','0',0,1,'JS')";

		
		interval++;
		try {
			PreparedStatement pstmt = conn.prepareStatement(NoPKInsertSql);
			// pstmt.executeUpdate();
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
	
	
	public static void insert(String JOB_NAME){
		
		Connection conn = HanaUtil.getConnection();
			
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
			pstmt = HanaUtil.getConnection()
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
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss a",Locale.ENGLISH);// 24小时�?
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//12小时�?
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
//		cal.add(Calendar.HOUR_OF_DAY, x);// 24小时�?
		 //关键�?  
//		 Date newDate = DateUtils.addMilliseconds(cal,100))�?  
		 //就是对cd加上100毫秒,�?100秒就是加�?-100毫秒      
		  
		 //将生成的时间输出为字符串  
//		 String newDate_str = sDateFormat.format(newDate);
		// cal.add(Calendar.HOUR, x);12小时�?
		date = cal.getTime();
//		System.out.println("front:" + date);
		cal = null;
		return format.format(date);
	}

	public static String getGUID(String JOB_NAME){
		PreparedStatement pstmt;
		try {
			pstmt = HanaUtil.getConnection().prepareStatement("SELECT GUID FROM AL_LANG WHERE NAME = '"+JOB_NAME+"'");
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
			pstmt = HanaUtil.getConnection().prepareStatement("SELECT SCHED_NAME FROM AL_SCHEDULE_INFO WHERE JOB_GUID = "+GUID+"");
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
			pstmt = HanaUtil.getConnection().prepareStatement("SELECT MAX(OBJECT_KEY) FROM AL_SCHED_INFO");
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

}

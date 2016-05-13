package com.sap.eim.dataservices;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.sap.db.DBUtil;
import com.sap.db.HanaUtil;
import com.sap.util.ExportExcelUtil;
/**
 * 
 * @author Andy Zhang
 *
 */
public class ScheduleUtil {
	
	static int counter = 1000;

	static String startTime_4AM = "2016-05-09 04:20:00 AM";  //2016-03-24 03:01:00 AM  //schedule开始的时间
	static String startTime_8PM = "2016-05-09 08:20:00 PM";
	static String startTime_12PM = "2016-05-09 12:20:00 PM";
	static String NOPKStartTime_12PM = "2016-04-12 12:12:00 PM";  
	static String NOPKStartTime_4AM = "2016-04-12 04:10:00 AM";
	static String NOPKStartTime_8PM = "2016-04-12 08:10:00 PM";
	static ResultSet result;
	static String SCHED_NAME = null;
	static String JOB_GUID = null;
	static ArrayList<String> JOB_NAMES;
	static int interval = 1;  
	static int intervalSec = 10000; //每个JOB的时间间隔，10秒

	private String suffix;

	public static void main(String[] args) {
		
		/*suffix = startTime_4AM.substring(11, 13) + startTime_4AM.substring(20, 22);
		System.out.println("Suffix : " + suffix);*/

		if (queryMax() == 0) {
			counter = 100000;
		} else {
			counter = queryMax() + 1;
		}

		System.out.println(counter);

		/*String[] JobNames = new String[] { "JB_Y57FW_COGNIZANCE_DELTA",
				"JB_Y57FW_HOLD_ACOURT_DELTA"};*/

		ArrayList<String> JobNames = getJobNames("JB_Y52" + "%DELTA"); // SQL

		int i = 0;
		for (String JobName : JobNames) {
			System.out.println(counter + ":" + JobName);
			insert(JobName, startTime_4AM, "4AM");
			insert(JobName, startTime_8PM, "8PM");
			insert(JobName, startTime_12PM,"12PM");
			i++;
		}
		System.out.println(i*3 + " job schedules created!");
		
		//导出统计信息
//		exportStatistics();
	
		//DELTA JOB : "JB_Y%_DELTA"
		//NOPK JOB : "JB_Y%_NOPK"
		//Master Data : "JB_Y00%_MD"
		/*ArrayList<String> JobNames = getJobNames("JB_Y00%_MD");
		for (String JobName : JobNames) {
			System.out.println(JobName);
//			insert(JobName);
		}
		System.out.println(JobNames.size());*/

	/*String[] pres = {"01","02","03","04","07","11","13","14","15",
			"17","19","24","35","45","48","52","56","57","58","59"};
			
	int total =0;
	for(String pre : pres){
		ArrayList<String> JobNames = getJobNames("JB_Y"+pre+"%DELTA");  //SQL 语句里like后面的通配符regex
		insertBatch(JobNames);
		System.out.println("Y"+ pre +" : "+ JobNames.size()+" job schedules created!");
		total += JobNames.size();
	}
	System.out.println("Total Job Schedules : "+ total +" added.");*/
	
	
		
	}
	
	/**
	 * 激活或关闭schedule
	 * 必须在management console里激活！！！
	 */
	void active(){
		
	}
	
	public static void insertBatch(ArrayList<String> JOB_NAMES){

		for(int i=0;i < JOB_NAMES.size();i++){
			String JOB_NAME = JOB_NAMES.get(i);
			System.out.println(JOB_NAME +" :");
//			insert(JOB_NAME);
		}
	}
	
	static void exportStatistics(){
		ExportExcelUtil<List> ex = new ExportExcelUtil<List>();
		// ID CUID Name Size Folder Owner Created at Modified at Description isInstance Universe
		String[] Headers = { "PREFIX", "JOB NO", "JOB NAME"};
		List dataSet = new ArrayList<String[]>();
		String[] pres = {"01","02","03","04","07","11","13","14","15",
				"17","19","24","32","34","35","45","46","48","50","52","56","57","58","59"};
				
		for(String pre : pres){
			ArrayList<String> JobNames = getJobNames("JB_Y"+pre+"%_DELTA");  //SQL 语句里like后面的通配符regex
			for(String JobName : JobNames){
				String[] row = {pre,String.valueOf(JobNames.size()),JobName};
				dataSet.add(row);
			}
			System.out.println("Y"+ pre +" : "+ JobNames.size()+" job schedules created!");
		}
		
//		List dataset = RetriveUtil.webiRowDetail(enterpriseSession);
		
		OutputStream out;
		try {
			out = new FileOutputStream("C://Users//i310003//Desktop//export//DELTA.xls");
			ex.exportExcel(Headers, dataSet, out);
			out.close();
			System.out.println("excel导出成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 输入NOPK的匹配名就好，例如： "JB_Y01%_NOPK"
	 * @param regex
	 */
	public static void insertNOPKBatch(String regex){
		ArrayList<String> JOB_NAMES = getJobNames(regex);
		for(int i=0;i < JOB_NAMES.size();i++){
			String JOB_NAME = JOB_NAMES.get(i);
			insertNOPK(JOB_NAME);
		}
		System.out.println(JOB_NAMES.size() +" job schedules created!");
	}
	
	static void insertNOPK(String JOB_NAME){
		insertNOPK_12PM(JOB_NAME);
		insertNOPK_4AM(JOB_NAME);
		insertNOPK_8PM(JOB_NAME);
	}
	
	/**
	 * 
	 * @param JOB_NAME
	 * @param StartTime   Start time of the schedules 12,4 or 8 (12PM, 4AM, 8PM)
	 */
	private static void insertNOPK_12PM(String JOB_NAME) {
		Connection conn = HanaUtil.getConnection();

		String NoPKInsertSql = "";
			NoPKInsertSql = "insert into AL_SCHED_INFO values("+counter+","
					+ "'"+JOB_NAME+"_12AM"+"',"    //生成中午12点跑的Schedule名字
					+ "'"+getGUID(JOB_NAME)+"'," 
							+ "'"+addDate(NOPKStartTime_12PM, 30000*interval)+"'"
							+ ",'-Slocalhost -NsecEnterprise -Q\"Repo_2\" -UAdministrator -PSW5pdDEyMzQ  -G\""+getGUID(JOB_NAME)+"\" -t5 -T14 -KspPRD',"
											+ "'',-1," //AT_ID,如果inactive则为-1,active则为OBJECT_NO
											+ "0,'WEEKLY','-2147483521','ahradq01.ab-insurance.com',"
											+ "3500,'0','0',0,0,'localhost')";
			counter++;
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
	
	private static void insertNOPK_4AM(String JOB_NAME) {
		Connection conn = HanaUtil.getConnection();

		String NoPKInsertSql = "";
			NoPKInsertSql = "insert into AL_SCHED_INFO values("+counter+","
					+ "'"+JOB_NAME+"_4AM"+"',"
					+ "'"+getGUID(JOB_NAME)+"'," 
							+ "'"+addDate(NOPKStartTime_4AM, 30000*interval)+"'"
							+ ",'-Slocalhost -NsecEnterprise -Q\"Repo_2\" -UAdministrator -PSW5pdDEyMzQ  -G\""+getGUID(JOB_NAME)+"\" -t5 -T14 -KspPRD',"
											+ "'',-1," //AT_ID,如果inactive则为-1,active则为OBJECT_NO
											+ "0,'WEEKLY','-2147483521','ahradq01.ab-insurance.com',"
											+ "3500,'0','0',0,0,'localhost')";
		counter++;
		interval++;
		try {
			PreparedStatement pstmt = conn.prepareStatement(NoPKInsertSql);
			pstmt.execute();
			if (!conn.getAutoCommit()) {
				conn.commit();
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void insertNOPK_8PM(String JOB_NAME) {
		Connection conn = HanaUtil.getConnection();

		String NoPKInsertSql = "";
		
			NoPKInsertSql = "insert into AL_SCHED_INFO values("+counter+","
					+ "'"+JOB_NAME+"_8PM"+"',"
					+ "'"+getGUID(JOB_NAME)+"'," 
							+ "'"+addDate(NOPKStartTime_8PM, 30000*interval)+"'"
							+ ",'-Slocalhost -NsecEnterprise -Q\"Repo_2\" -UAdministrator -PSW5pdDEyMzQ  -G\""+getGUID(JOB_NAME)+"\" -t5 -T14 -KspPRD',"
											+ "'',-1," //AT_ID,如果inactive则为-1,active则为OBJECT_NO
											+ "0,'WEEKLY','-2147483521','ahradq01.ab-insurance.com',"
											+ "3500,'0','0',0,0,'localhost')";
		counter++;
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
	
	/**
	 * 
	 * @param JOB_NAME
	 * @param startTime
	 * @param suffix
	 */
	public static void insert(String JOB_NAME, String startTime, String suffix) {

		Connection conn = DBUtil.getHANAConnection();
		String DeltaInsertBOESql_P = "insert into AL_SCHED_INFO values(" + counter + "," + "'" + JOB_NAME + "_" + suffix
				+ "'," + "'" + getGUID(JOB_NAME) + "'," + "'" + addDate(startTime, intervalSec * interval) + "'"
				+ ",'-Slocalhost -NsecEnterprise -Q\"Repo_2\" -UAdministrator -PSW5pdDEyMzQ  -G\"" + getGUID(JOB_NAME)
				+ "\" -t5 -T14 -KspPRD'," + "'',-1," // AT_ID,如果inactive则为-1,active则为OBJECT_NO
				+ "0,'WEEKLY','-2147483521','ahradq01.ab-insurance.com'," + "3500,'0','0',0,0,'localhost')";

		/*String DeltaInsertBOESql_D = "insert into AL_SCHED_INFO values(" + counter + "," + "'" + JOB_NAME + "'," + "'"
				+ getGUID(JOB_NAME) + "'," + "'" + addDate(startTime, intervalSec * interval) + "'"
				+ ",'-Slocalhost -NsecEnterprise -Q\"Repo\" -UAdministrator -PSW5pdDEyMzQ  -G\"" + getGUID(JOB_NAME)
				+ "\" -t5 -T14 -KspOraDEV_to_HANAERPDEV'," + "'',-1," // AT_ID,如果inactive则为-1,active则为OBJECT_NO
				+ "0,'WEEKLY','-2147483521','ahradq01.ab-insurance.com'," + "3500,'0','0',0,0,'localhost')";*/

		counter++;
		interval++;
		try {
			PreparedStatement pstmt = conn.prepareStatement(DeltaInsertBOESql_P);
			pstmt.execute();
			if (!conn.getAutoCommit()) {
				conn.commit();
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
/*	public static void insert(String JOB_NAME){
		
		Connection conn = DBUtil.getHANAConnection();
		String DeltaInsertBOESql_P = "insert into AL_SCHED_INFO values("+counter+","
				+ "'"+JOB_NAME+"_"+suffix+"',"
						+ "'"+getGUID(JOB_NAME)+"'," 
								+ "'"+addDate(startTime, intervalSec*interval)+"'"
										+ ",'-Slocalhost -NsecEnterprise -Q\"Repo_2\" -UAdministrator -PSW5pdDEyMzQ  -G\""+getGUID(JOB_NAME)+"\" -t5 -T14 -KspPRD',"
												+ "'',-1," //AT_ID,如果inactive则为-1,active则为OBJECT_NO
												+ "0,'WEEKLY','-2147483521','ahradq01.ab-insurance.com',"
												+ "3500,'0','0',0,0,'localhost')";
		
		String DeltaInsertBOESql_D = "insert into AL_SCHED_INFO values("+counter+","
				+ "'"+JOB_NAME+"',"
						+ "'"+getGUID(JOB_NAME)+"'," 
								+ "'"+addDate(startTime, intervalSec*interval)+"'"
										+ ",'-Slocalhost -NsecEnterprise -Q\"Repo\" -UAdministrator -PSW5pdDEyMzQ  -G\""+getGUID(JOB_NAME)+"\" -t5 -T14 -KspOraDEV_to_HANAERPDEV',"
												+ "'',-1," //AT_ID,如果inactive则为-1,active则为OBJECT_NO
												+ "0,'WEEKLY','-2147483521','ahradq01.ab-insurance.com',"
												+ "3500,'0','0',0,0,'localhost')";

			
			counter++;
			interval++;
		try {
			PreparedStatement pstmt = conn.prepareStatement(DeltaInsertBOESql_P);
		    pstmt.execute();
		    if (!conn.getAutoCommit()) {   
		        conn.commit();
		      }
		    conn.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
/*	public static ArrayList<String> excludeJobNames(ArrayList<String> JOB_NAMES, String[] EX_NAMES){
		for(String JOB_NAME : getJobNames()){
			for(int i=0; i < EX_NAMES.length; i++){
				if(JOB_NAME.equalsIgnoreCase(EX_NAMES[i])){
					JOB_NAMES.remove(EX_NAMES[i]);
				}
			}
		}
		return JOB_NAMES;
	}*/


	private static ArrayList<String> excludeJobNames(ArrayList<String> jobNames, String JobNameRegex) {
		// TODO Auto-generated method stub
		for(String JOB_NAME : getJobNames(JobNameRegex)){
			for(int i=0; i < jobNames.size(); i++){
				if(JOB_NAME.equalsIgnoreCase(jobNames.get(i))){
					JOB_NAMES.remove(jobNames.get(i));
				}
			}
		}
		return JOB_NAMES;
	}
	
	public static ArrayList<String> getJobNames(String regex) {
		JOB_NAMES = new ArrayList<String>();
		PreparedStatement pstmt;
		try {
			// regex = JB_Y%_DELTA
			pstmt = DBUtil.getHANAConnection()
					.prepareStatement("SELECT NAME FROM AL_LANG where OBJECT_TYPE=0 and NAME LIKE '"+regex+"'");
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
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a",Locale.ENGLISH);// 24小时�?
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
		cal = null;
		System.out.println("StartTime : "+format.format(date));
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
		System.out.println("MAX Object key - counter : "+counter);
		return counter;
	}

}

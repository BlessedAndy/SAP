package com.sap.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * JDBC 操作数据库的工具类
 * @author Andy
 * @version 1.0
 *
 */
public class DBUtil {
	/**
	 * 注意：如果系统并发量很大，将资源定义为static静态的会有问题。
	 */
	//定义需要的变量, 如果并发量不大, 也可以将资源做成静态的
	private static Connection conn = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	
	//连接数据库的参数
	private static String OracleDriver = "" ;
	private static String HANADriver = "" ;
	private static String OracleURL = "";
	private static String HANAURL = "";
	private static String OracleUserName = "";
	private static String HANAUserName = "";
	private static String OraclePasswd = "";
	private static String HANAPasswd = "";
	
	private static Properties prop = null;
	private static FileInputStream fis = null;
	
	//加载驱动，只需要一次
	static {
		try {
			//从dbinfo.properties 文件中读取配置信息
			prop = new Properties(); //在使用之前一定要先实例化。
//			/DataServices/src/com/sap/db/dbinfo.properties
			fis = new FileInputStream("dbinfo.properties");
			prop.load(fis);
//			OracleDriver = prop.getProperty("OracleDriver");
			HANADriver = prop.getProperty("HANADriver");
//			OracleURL = prop.getProperty("OracleURL");
			HANAURL = prop.getProperty("HANAURL");
//			OracleUserName = prop.getProperty("OracleUserName");
			HANAUserName = prop.getProperty("HANAUserName");
//			OraclePasswd = prop.getProperty("OraclePasswd");
			HANAPasswd = prop.getProperty("HANAPasswd");
			Class.forName(HANADriver);
//			Class.forName(OracleDriver);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fis = null;
		}
	}
	
	//得到HANA连接
	public static Connection getHANAConnection(){
		try {
			conn = DriverManager.getConnection(HANAURL,HANAUserName,HANAPasswd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//得到连接
	public static Connection getOracleConnection(){
		try {
			conn = DriverManager.getConnection(OracleURL,OracleUserName,OraclePasswd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 如果有多个 update / delete /insert [需要考虑事务]
	 * @param sql
	 * @param parameters
	 */
	public static void executeHANAupdate2(String sql[], String[][] parameters){
		
		try {
			
			//1.获得连接
			conn = getHANAConnection();
			//因为这时用户传入的可能是多个sql语句。
			conn.setAutoCommit(false);
			//...
			for(int i = 0; i < sql.length; i++){
				if(parameters[i]!=null){
					ps = conn.prepareStatement(sql[i]);
					for(int j=0; j < parameters[i].length; j++){
						
						ps.setString(j+1, parameters[i][j]);
					}
					ps.executeUpdate();
				}
				
			}
			
			conn.commit();
			
		} catch (Exception e) {
			
//			conn.rollback();
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally{
			close(rs,ps,conn);
		}
	}
	
	//先写一个 update / delete / insert
	//sql 格式： update 表明  set 字段名 = ? where 字段 = ?
	//prameters 应该是 {"abc",23}; 这种形式
	public static void executeHANAUpdate(String sql, String[] parameters ){
		
		//1.创建一个ps
		conn = getHANAConnection();
		try {
			ps = conn.prepareStatement(sql);
			//给？赋值
			if(parameters != null){
				for(int i = 0; i < parameters.length; i++){
					ps.setString(i+1, parameters[i]);
				}
			}
			
			//执行
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();//开发阶段打印异常
			//抛出运行时异常, 可以给该函数的调用者一个可以处理，也可以放弃处理的选择。
			throw new RuntimeException(e.getMessage());
		}finally{
			//关闭资源
			close(rs,ps,conn);
		}
		
	}
	
	//关闭资源的函数
	public static void close(ResultSet rs, Statement ps, Connection conn){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if(ps != null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ps = null;
		}
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
	
	
	public static PreparedStatement getPreparedStatement(String sql){
		try {
			ps = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
	
	//
	public static ResultSet getQueryResultSet(){
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	
}

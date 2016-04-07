package com.sap.eim.dataservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sap.db.DBUtil;

/**
 * 
 * @author Andy Zhang
 * 
 * Check whether the target row count equals the source row count.
 *
 */
public class RowCheck {
	
	static String countSQLPre = "SELECT COUNT(*) FROM ";
	
	String oracleCount = "select COUNT(*) from Y52T_GC_CLAIM where T_CRT_TM >= DATE'2016-03-01' and T_CRT_TM < DATE'2016-04-01'";
	
	
	public static void main(String[] args) {
		System.out.println("Y01PWEB_APP_BASE Row Count : "+hanaRowCount("Y01PWEB_APP_BASE"));
//		System.out.println("Y01PWEB_APP_BASE Row Count : "+oracleRowCount("Y01WEB_APP_BASE"));
	}
	
	public static int hanaRowCount(String tableName){
		Connection conn = DBUtil.getHANAConnection();
		ResultSet rs = null;
		try {
			String countSQL  = countSQLPre + tableName;
			PreparedStatement pstmt = conn.prepareStatement(countSQL);
//			pstmt.executeUpdate();
		    rs = pstmt.executeQuery();
		    rs.next();
		    if (!conn.getAutoCommit()) {   
		        conn.commit();
		      }
//		    conn.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	static int oracleRowCount(String tableName){
		Connection conn = DBUtil.getOracleConnection();
		ResultSet rs = null;
		try {
			String countSQL  = countSQLPre + tableName;
			PreparedStatement pstmt = conn.prepareStatement(countSQL);
//			pstmt.executeUpdate();
		    rs = pstmt.executeQuery();
		    rs.next();
		    if (!conn.getAutoCommit()) {   
		        conn.commit();
		      }
//		    conn.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}

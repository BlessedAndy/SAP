package com.sap.db;

import static org.junit.Assert.*;

import java.sql.ResultSet;

import org.junit.Test;

public class DBUtilTest {

	@Test
	public void testGetConnection() {
		
		System.out.println(DBUtil.getDB2Connection());
		/*System.out.println(DBUtil.getHANAConnection().toString());
		System.out.println(DBUtil.getOracleConnection().toString());
		ResultSet result = DBUtil.getQueryResultSet();
		System.out.println(result.toString());*/
	}

	@Test
	public void testExecuteupdate2() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecuteUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testClose() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPreparedStatement() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQueryResultSet() {
		fail("Not yet implemented");
	}

}

/**
 * 
 */
package com.sap.db;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.sap.eim.dataservices.ScheduleUtil;

/**
 * @author Andy Zhang
 *
 */
public class ScheduleUtilTest {

	/**
	 * Test method for {@link com.sap.eim.dataservices.ScheduleUtil#getJobNames()}.
	 */
	@Test
	public void testGetJobNames() {
		ArrayList<String> JobNames = ScheduleUtil.getJobNames("hive_%");
		for (String jobName : JobNames){
			System.out.println(jobName);
		}
	}

}

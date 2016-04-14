package com.sap.eim.dataservices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sap.db.DBUtil;

/**
 * Convert Function of ETL
 * @author Andy Zhang
 *
 */
public class ETL {
	
	public static void main(String[] args) {
		ETL etl = new ETL();
		etl.varcharToInt();
	}
	
	void varcharToInt(){
		
		String tableName = "LOG_TABLE";
		String sql = "SELECT * FROM " + tableName + "";
		ResultSet result = null;
		try {
			result = DBUtil.getHANAConnection().prepareStatement(sql).executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Row> insertResult = new ArrayList<Row>();

		try {
			for (int i = 0; result.next(); i++) {
				if (result.getString(1) != null) {
					Row newRow = new Row();
					newRow.tableName = result.getString(1);
					newRow.batchNo = (int) Long.parseLong(result.getString(2));
					newRow.last_update = result.getString(3);
					System.out.println(newRow.tableName + "  :  "  + newRow.batchNo + "  :  " + newRow.last_update);
					insertResult.add(newRow);
				} else {
					System.out.println("NULL!");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*for(Row row : insertResult){
			System.out.println("Row : tableName : " + row.tableName + " batch no : " + row.batchNo + " last update : " + row.last_update);
		}*/
		
	}
	
	class Row{
		String tableName;
		int batchNo;
		String last_update;		
	}

}

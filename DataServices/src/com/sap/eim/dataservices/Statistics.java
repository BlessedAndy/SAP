package com.sap.eim.dataservices;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.sap.util.ExportExcelUtil;

public class Statistics {
	
	public static void main(String[] args) {
		exportStatistics();
	}
	
	static void exportStatistics(){
		ExportExcelUtil<List> ex = new ExportExcelUtil<List>();
		// ID CUID Name Size Folder Owner Created at Modified at Description isInstance Universe
		String[] Headers = { "PREFIX", "JOB NO", "JOB NAME"};
		List dataSet = new ArrayList<String[]>();
		String[] pres = {"01","02","03","04","07","11","13","14","15",
				"17","19","24","32","34","35","45","46","48","50","52","56","57","58","59"};
				
		for(String pre : pres){
			ArrayList<String> JobNames = ScheduleUtil.getJobNames("JB_Y"+pre+"%_DELTA");  //SQL 语句里like后面的通配符regex
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

}

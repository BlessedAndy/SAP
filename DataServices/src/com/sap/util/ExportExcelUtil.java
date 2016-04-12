package com.sap.util;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 
 * @author Andy
 * @version Oct 14, 2014 - 7:32:43 PM
 * @param <T>
 */
public class ExportExcelUtil<T> {

    public void exportExcel(Collection<T> dataset, OutputStream out) {
        exportExcel("SAP DataServices Data Export", null, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String[] headers, Collection<T> dataset,OutputStream out) {
        exportExcel("SAP DataServices Data Export", headers, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String[] headers, Collection<T> dataset,OutputStream out, String pattern) {
        exportExcel("SAP DataServices Data Export", headers, dataset, out, pattern);
    }

    /**
     * @param title
     *            表格标题名
     * @param headers
     *            表格属性列名数组
     * @param dataset
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    @SuppressWarnings("unchecked")
    public void exportExcel(String title, String[] headers,Collection<T> dataset, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 27);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.AUTOMATIC.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName(HSSFFont.FONT_ARIAL);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
                0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("SAP BusinessObjects Upgrade Tool"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("Andy Zhang");

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            /**
             * 这里要写List里放的String[]这种的对应方法 了
             */
            String[] oneRow = (String[]) t;
            for(short i = 0; i < oneRow.length; i++){
            	 HSSFCell cell = row.createCell(i);
                 cell.setCellStyle(style2);
            	 cell.setCellValue(oneRow[i]);
            	
            }

        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

  /*  public static void main(String[] args) throws SDKException, IOException {
		ExportExcelUtil<List> ex = new ExportExcelUtil<List>();
		// ID CUID Name Size Folder Owner Created at Modified at Description isInstance Universe
		String[] webiHeaders = { "ID", "CUID", "Name", "Size", "Folder", "Owner",
				"Created at", "Modified at", "Description", "isInstance",
				"Universe" };
		
		 * Testing code
		 
		IEnterpriseSession enterpriseSession = Logon.testLogon();
		List dataset = RetriveUtil.webiRowDetail(enterpriseSession);
		
		OutputStream out = new FileOutputStream("C://Users//I310003//Desktop//Test//BueinessObject XI 3.1 Data.xls");
		ex.exportExcel(webiHeaders, dataset, out);
		out.close();
		System.out.println("excel导出成功！");
        } */
}
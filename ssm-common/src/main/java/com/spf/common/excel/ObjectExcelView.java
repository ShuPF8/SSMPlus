package com.spf.common.excel;

import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Excel导出
 */
public class ObjectExcelView extends XlsxAbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Sheet sheet;
        Cell cell;
        workbook.createName().setNameName(model.get("fileName").toString());
        sheet = workbook.createSheet(model.get("sheetName").toString());

        List<String> titles = (List<String>) model.get("titles");
        int len = titles.size();
        CellStyle headerStyle = workbook.createCellStyle(); // 标题样式
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font headerFont = workbook.createFont(); // 标题字体
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 11);
        headerStyle.setFont(headerFont);
        int width = 20;
        short height = 25 * 20;
        sheet.setDefaultColumnWidth(width);
        for (int i = 0; i < len; i++) { // 设置标题
            String title = titles.get(i);
            cell = getCell(sheet, 0, i);
            cell.setCellStyle(headerStyle);
            setText(cell, title);
        }
        sheet.getRow(0).setHeight(height);
        CellStyle contentStyle = workbook.createCellStyle(); // 内容样式
        contentStyle.setAlignment(HorizontalAlignment.CENTER);

        List<Map<String, Object>> varList = (List<Map<String, Object>>) model.get("varList");
        int varCount = varList.size();
        for (int i = 0; i < varCount; i++) {
            Map<String, Object> vpd = varList.get(i);
            for (int j = 0; j < len; j++) {
                Object varstr = vpd.get("var" + (j + 1)) != null ? vpd.get("var" + (j + 1)) : "";
                cell = getCell(sheet, i + 1, j);
                cell.setCellStyle(contentStyle);
                setText(cell, varstr.toString());
            }
        }
    }


}

package com.bdd.Util;

import com.bdd.Constant.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelToJson {
    private String jsonString;
    private Map<String, List<Map<String, Object>>> mapData;

    public ExcelToJson(String path) {
        String excelFilePath = Project.USER_DIR.concat(path);
        mapData = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                List<Map<String, Object>> sheetData = new ArrayList<>();
                Row headerRow = sheet.getRow(0);
                List<String> headers = new ArrayList<>();

                //Read headers
                for (Cell cell : headerRow) {
                    headers.add(cell.getStringCellValue());
                }

                //Read row
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    Map<String, Object> rowData = new HashMap<>();

                    for (int j = 0; j < headers.size(); j++) {
                        Cell cell = row.getCell(j);
                        Object cellValue = getValueFormat(cell);
                        rowData.put(headers.get(j), cellValue);
                    }
                    sheetData.add(rowData);
                }
                mapData.put(sheet.getSheetName(), sheetData);
            }

            //Convert to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getValueFormat(Cell cell) {
        Object cellValue;
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = cell.getDateCellValue();
                    } else {
                        cellValue = cell.getNumericCellValue();
                    }
                    break;
                case BOOLEAN:
                    cellValue = cell.getBooleanCellValue();
                    break;
                case FORMULA:
                    cellValue = cell.getCellFormula();
                    break;
                default:
                    cellValue = null;
            }
        } else {
            cellValue = null;
        }
        return cellValue;
    }

    public String getJson() {
        return jsonString;
    }

    public Map<String, List<Map<String, Object>>> getMapData() {
        return mapData;
    }
}

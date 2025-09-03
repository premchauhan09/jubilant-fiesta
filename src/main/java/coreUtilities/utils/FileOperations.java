package coreUtilities.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.exception.FilloException;

public class FileOperations {

    public Map<String, String> readExcelPOI(String excelFilePath, String sheetName) throws FilloException {
        HashMap<String, String> dataMap = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            for (Row row : sheet) {
                Cell keyCell = row.getCell(0);
                Cell valueCell = row.getCell(1);

                if (keyCell == null || valueCell == null) continue;

                String key = getCellValueAsString(keyCell);
                String value = getCellValueAsString(valueCell);

                dataMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }
}

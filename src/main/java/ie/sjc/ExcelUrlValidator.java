package ie.sjc;

import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExcelUrlValidator {
    public static void main(String[] args) throws Exception {
        try (InputStream is = new FileInputStream("/home/sc47/workspace/dev-git/poiexcel/src/main/resources/9.30.xlsx")) {
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(0);
            int firstRow = sheet.getFirstRowNum();
            int lastRow = sheet.getLastRowNum();

            for (int i = firstRow; i <= lastRow; i ++) {
                Cell cell = sheet.getRow(i).getCell(4);
                if (cell == null) {
                    //setCellColor(wb, cell, IndexedColors.RED);
                } else {
                    String url = cell.getRichStringCellValue().getString();
                    if (CheckUrlConnection.isUrlExists(url)) {
                        setCellColor(wb, cell, IndexedColors.GREEN);
                    } else {
                        setCellColor(wb, cell, IndexedColors.RED);
                    }
                }
            }

            try (OutputStream outputStream = new FileOutputStream("/home/sc47/workspace/dev-git/poiexcel/src/main/resources/9.30.processed.xlsx")) {
                wb.write(outputStream);
            }
        }

    }

    public static void setCellColor(Workbook wb, Cell cell, IndexedColors colours) {
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(colours.getIndex());
        style.setFillPattern(FillPatternType.BIG_SPOTS);
        cell.setCellStyle(style);
    }
}

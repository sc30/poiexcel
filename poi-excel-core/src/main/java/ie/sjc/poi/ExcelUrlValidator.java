package ie.sjc.poi;

import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.Date;

public class ExcelUrlValidator {
    public static void main(String[] args) throws Exception {
        check("/home/sc47/workspace/dev-git/poiexcel/src/main/resources/9.30.xlsx", 4);
    }

    public static String check(String fileLocation, int column) throws Exception {
        JsoupReadWebPage jsoupReadWebPage = new JsoupReadWebPage(JsoupReadWebPage.k3_hardcoded, JsoupReadWebPage.sooxie_hardcoded);

        try (InputStream is = new FileInputStream(fileLocation)) {
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(0);
            int firstRow = sheet.getFirstRowNum();
            int lastRow = sheet.getLastRowNum();

            System.out.println("第一行为：" + firstRow);
            System.out.println("最后一行为： " + lastRow);

            for (int i = firstRow; i <= lastRow; i ++) {
                System.out.println("---------------------------------");
                System.out.println("正在处理第" + i + "行");
                Cell cell = sheet.getRow(i).getCell(column);
                if (cell == null) {
                    //setCellColor(wb, cell, IndexedColors.RED);
                } else {
                    String url = cell.getRichStringCellValue().getString();
                    if (CheckUrlConnection.isUrlExists(url)) {
                        setCellColor(wb, cell, IndexedColors.GREEN);
                        String itemStatus = jsoupReadWebPage.getStatus(url);
                        Cell createCell = sheet.getRow(i).createCell(column + 1);
                        createCell.setCellValue(itemStatus);
                    } else {
                        setCellColor(wb, cell, IndexedColors.RED);
                    }
                }
                System.out.println("第" + i + "行处理完毕");
                System.out.println("---------------------------------");
            }

            Date date = new Date();
            String processedFile = fileLocation + date.toString() + ".xlsx";
            try (OutputStream outputStream = new FileOutputStream(processedFile)) {
                wb.write(outputStream);
            }
            return processedFile;
        }
    }

    public static void setCellColor(Workbook wb, Cell cell, IndexedColors colours) {
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(colours.getIndex());
        style.setFillPattern(FillPatternType.LEAST_DOTS);
        cell.setCellStyle(style);
    }
}

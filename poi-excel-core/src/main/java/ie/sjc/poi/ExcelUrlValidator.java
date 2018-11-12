package ie.sjc.poi;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class ExcelUrlValidator {

    private int processRow;
    private int restTimeInSeconds;

    public ExcelUrlValidator(int processRow, int restTimeInSeconds) {
        this.processRow = processRow;
        this.restTimeInSeconds = restTimeInSeconds;
    }

    public static void main(String[] args) throws Exception {
        ExcelUrlValidator excelUrlValidator = new ExcelUrlValidator(25, 2);
        excelUrlValidator.check("/home/sc47/workspace/dev-git/poiexcel/src/main/resources/9.30.xlsx", 4);
    }

    public static void setCellColor(Workbook wb, Cell cell, IndexedColors colours) {
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(colours.getIndex());
        style.setFillPattern(FillPatternType.LEAST_DOTS);
        cell.setCellStyle(style);
    }

    public String check(String fileLocation, int column) throws Exception {
        JsoupReadWebPage jsoupReadWebPage = new JsoupReadWebPage(JsoupReadWebPage.k3_hardcoded, JsoupReadWebPage.sooxie_hardcoded);

        String outputFile = null;
        int index = fileLocation.lastIndexOf('.');
        if (index > 0 && index < fileLocation.length() - 1) {
            outputFile = fileLocation.substring(0, index + 1);
        }

        if (outputFile == null) {
            return "文件不合法，重命名文件名字来解决问题！";
        }

        try (InputStream is = new FileInputStream(fileLocation)) {
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(0);
            int firstRow = sheet.getFirstRowNum();
            int lastRow = sheet.getLastRowNum();

            System.out.println("第一行为：" + (firstRow + 1));
            System.out.println("最后一行为： " + (lastRow + 1));

            for (int i = firstRow; i <= lastRow; i++) {

                System.out.println("---------------------------------");
                System.out.println("正在处理第" + (i + 1) + "行");
                Cell cell = sheet.getRow(i).getCell(column);
                if (cell != null) {
                    String url = cell.getRichStringCellValue().getString();
                    if (jsoupReadWebPage.requestGetStatus(url)) {
                        setCellColor(wb, cell, IndexedColors.GREEN);
                        String itemStatus = jsoupReadWebPage.getItemStatus(url);
                        Cell createCell = sheet.getRow(i).createCell(column + 1);
                        createCell.setCellValue(itemStatus);
                    } else {
                        setCellColor(wb, cell, IndexedColors.RED);
                        Cell errorCell = sheet.getRow(i).createCell(column + 1);
                        errorCell.setCellValue("网页链接不可用、或者网页服务器自动关闭了链接所以返回了错误的信息到程序中,请手动检测打开链接再次检测！");
                    }
                }
                System.out.println("第" + (i + 1) + "行处理完毕");
                System.out.println("---------------------------------");

                if (i % getProcessRow() == 9) {
                    System.out.println("等待" + getRestTimeInSeconds() + "s让程序更稳定");
                    TimeUnit.SECONDS.sleep(getRestTimeInSeconds());
                    System.out.println(getRestTimeInSeconds() + "s等待结束");
                }
            }


            String processedFile = outputFile + "处理完成.xlsx";
            try (OutputStream outputStream = new FileOutputStream(processedFile)) {
                wb.write(outputStream);
            }
            System.out.println("处理完成，文件生成在" + processedFile);
            return processedFile;
        }
    }

    public int getProcessRow() {
        return processRow;
    }

    public void setProcessRow(int processRow) {
        this.processRow = processRow;
    }

    public int getRestTimeInSeconds() {
        return restTimeInSeconds;
    }

    public void setRestTimeInSeconds(int restTimeInSeconds) {
        this.restTimeInSeconds = restTimeInSeconds;
    }
}

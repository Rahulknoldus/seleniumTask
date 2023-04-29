package DDT;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteExcelFile {


    public void writeExcelFile(String sheetName, String cellValue, int startRow, int col) throws IOException {
        String excelPath = "src/main/resources/Book1.xlsx";
        File file = new File(excelPath);
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheet(sheetName);
        int row = startRow;
        if (sheet.getRow(row) == null) {
            sheet.createRow(row);
        }
        while (sheet.getRow(row) != null && sheet.getRow(row).getCell(col) != null && !sheet.getRow(row).getCell(col).toString().isEmpty()) {
            row++;
            if (sheet.getRow(row) == null) {
                sheet.createRow(row);
            }
        }
        sheet.getRow(row).createCell(col).setCellValue(cellValue);
        FileOutputStream fos = new FileOutputStream(new File(excelPath));
        wb.write(fos);
        wb.close();
        fos.close();
    }
}

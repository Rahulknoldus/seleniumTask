package DDT;

import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadExcelFile {
    XSSFWorkbook wb;
    XSSFSheet sheet;

    public ReadExcelFile(String excelPath){
        try{
            File src = new File(excelPath);
            FileInputStream fs = new FileInputStream(src);
            wb = new XSSFWorkbook(fs);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public String getData(int sheetNumber, int rows, int column) throws InterruptedException {
        sheet = wb.getSheetAt(sheetNumber);
        String data = sheet.getRow(rows).getCell(column).getStringCellValue();
        return data;
    }

    public int getRowCount(int sheetIndex){
        int row = wb.getSheetAt(sheetIndex).getLastRowNum();
        row = row+1;
        return row;
    }
}

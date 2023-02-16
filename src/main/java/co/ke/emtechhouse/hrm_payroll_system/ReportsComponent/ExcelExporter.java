//package co.ke.emtechhouse.hrm_payroll_system.ReportsComponent;
//
//import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.CommitedSalary.Salary;
//import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.CommitedSalary.SalaryService;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//public class ExcelExporter {
//    private XSSFWorkbook xssfWorkbook;
//    private XSSFSheet xssfSheet;
//
//    //    create header line
//    private void headerLine(){
//        xssfSheet = xssfWorkbook.createSheet("Salary Record");
//        Row row = xssfSheet.createRow(0);
//
//        CellStyle cellStyle = xssfWorkbook.createCellStyle();
//        XSSFFont font = xssfWorkbook.createFont();
//        font.setBold(true);
//        font.setFontHeight(16);
//        cellStyle.setFont(font);
//
//        createCell(row, 0, "ID", cellStyle);
//        createCell(row, 1, "Basic Salary", cellStyle);
//        createCell(row, 2, "Paye Deductions", cellStyle);
//        createCell(row, 3, "NHIF Deductions", cellStyle);
//        createCell(row, 4, "NSSF Deductions", cellStyle);
//        createCell(row, 5, "Netpay", cellStyle);
//    }
//
//    private void createCell(Row row, int countColumn, Object value, CellStyle cellStyle) {
//        xssfSheet.autoSizeColumn(countColumn);
//        Cell cell = row.createCell(countColumn);
//
//        if (value instanceof  Integer ){
//            cell.setCellValue((Integer) value );
//        }
//        else if (value instanceof Boolean){
//            cell.setCellValue((Boolean) value);
//        }
//        else
//        {
//            cell.setCellValue((String) value );
//        }
//
//    }
//    //    create Data which comes from database
//    private  void writeDatLines(Salary salary){
//        int rowCount = 1;
//        CellStyle style = xssfWorkbook.createCellStyle();
//        XSSFFont font = xssfWorkbook.createFont();
//        style.setFont(font);
//
//        Row row = xssfSheet.createRow(rowCount ++);
//        int countColumn = 0;
//
//        createCell(row, countColumn++, salary.getId().toString(), style);
//        createCell(row, countColumn++, salary.getBasic_salary().toString(), style);
//        createCell(row, countColumn++, salary.getPaye_deductions().toString(), style);
//        createCell(row, countColumn++, salary.getNhif_deductions().toString(), style);
//        createCell(row, countColumn++, salary.getNssf_deductions().toString(), style);
//        createCell(row, countColumn++, salary.getNet_pay().toString(), style);
//    }
//    //    Constructor for initializing objects
//    public ExcelExporter(Salary salary){
//        xssfWorkbook = new XSSFWorkbook();
//    }
//    //    finally create a method which have a calling from controller class
//    public ByteArrayInputStream exportData(HttpServletResponse response, Salary salary) throws IOException {
//
////        calling method headerLine
//        headerLine();
//        ByteArrayOutputStream out  = new ByteArrayOutputStream();
//
//        //        Calling method writeDataLines
//        writeDatLines(salary);
//
//        ServletOutputStream outputStream = response.getOutputStream();
////this  is for exported data
//        xssfWorkbook.write(outputStream);
////this is to send email
//        xssfWorkbook.write(out);
//        xssfWorkbook.close();
//
//        outputStream.close();
////        becouse all data going with byte format
//        return new ByteArrayInputStream(out.toByteArray());
//
//
//    }
//}
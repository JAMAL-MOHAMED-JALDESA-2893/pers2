//package co.ke.emtechhouse.hrm_payroll_system.ReportsComponent;
//
//import co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.CommitedSalary.Salary;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//public class UserExcelExporter {
//    private XSSFWorkbook workbook;
//    private XSSFSheet sheet;
//    private List<Salary> listSalaries;
//
//    public UserExcelExporter(List<Salary> listSalaries) {
//        this.listSalaries = listSalaries;
//        workbook = new XSSFWorkbook();
//    }
//
//    private void writeHeaderLine() {
//        sheet = workbook.createSheet("Salarys");
//
//        Row row = sheet.createRow(0);
//
//        CellStyle style = workbook.createCellStyle();
//        XSSFFont font = workbook.createFont();
//        font.setBold(true);
//        font.setFontHeight(16);
//        style.setFont(font);
//
////        createCell(row, 0, "Salary ID", style);
//        createCell(row, 1, "E-mail", style);
//        createCell(row, 2, "Full Name", style);
//        createCell(row, 3, "Roles", style);
//        createCell(row, 4, "Enabled", style);
//
//    }
//
//    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
//        sheet.autoSizeColumn(columnCount);
//        Cell cell = row.createCell(columnCount);
//        if (value instanceof Integer) {
//            cell.setCellValue((Integer) value);
//        } else if (value instanceof Boolean) {
//            cell.setCellValue((Boolean) value);
//        }else {
//            cell.setCellValue((String) value);
//        }
//        cell.setCellStyle(style);
//    }
//
//    private void writeDataLines() {
//        int rowCount = 1;
//
//        CellStyle style = workbook.createCellStyle();
//        XSSFFont font = workbook.createFont();
//        font.setFontHeight(14);
//        style.setFont(font);
//
//        for (Salary salary : listSalaries) {
//            Row row = sheet.createRow(rowCount++);
//            int columnCount = 0;
//            createCell(row, columnCount++, salary.getId(), style);
//            createCell(row, columnCount++, salary.getBasic_salary(), style);
//            createCell(row, columnCount++, salary.getPaye_deductions(), style);
//            createCell(row, columnCount++, salary.getNhif_deductions(), style);
//            createCell(row, columnCount++, salary.getNet_pay(), style);
//        }
//    }
//
//    public void export(HttpServletResponse response) throws IOException {
//        writeHeaderLine();
//        writeDataLines();
//
//        ServletOutputStream outputStream = response.getOutputStream();
//        workbook.write(outputStream);
//        workbook.close();
//
//        outputStream.close();
//
//    }
//}
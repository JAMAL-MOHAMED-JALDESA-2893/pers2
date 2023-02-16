package co.ke.emtechhouse.hrm_payroll_system.PayrollComponent.PayrollModule;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.web.multipart.MultipartFile;

public class Excellimports {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String SHEET = "Sheet1";
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            System.out.println("No file!!");
            return false;
        }
            System.out.println("I got the file");
        return true;
    }



    public static List<Payroll> excelToPayroll (InputStream is) {


    try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);



            Iterator<Row> rows = sheet.rowIterator();
            Row headerRow = rows.next();

            List<Payroll> payrolls = new ArrayList<>();
            int rowNumber = 0;

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                //Skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                Payroll payroll = new Payroll();
                int cellIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIndex) {
                        case 0:
                            payroll.setEmployee_id(Long.parseLong(currentCell.getStringCellValue()));
                            ////                            payroll.setSalary(Double.valueOf(currentCell.getStringCellValue()));

                            System.out.println("Cell values 1"+currentCell.getStringCellValue());
                            break;
                        case 1:
                            payroll.setFirst_name(currentCell.getStringCellValue());
                            System.out.println("Furniture AC is : " + payroll.getFirst_name());
                            break;
                        case 2:
                            payroll.setOther_names(currentCell.getStringCellValue());
                            break;
                        case 3:
                            payroll.setId_no(currentCell.getStringCellValue());
                            break;
                        case 4:
                            payroll.setAcct_no(currentCell.getStringCellValue());
                            break;
                        case 5:
                            payroll.setBank(currentCell.getStringCellValue());
                            break;
                        case 6:
                            payroll.setNssf_no(currentCell.getStringCellValue());
                            break;
                        case 7:
                            payroll.setNhif_no(currentCell.getStringCellValue());
                            break;
                        case 8:
                            payroll.setPin_no(currentCell.getStringCellValue());
                            break;
                        case 9:
                            System.out.println("Cell values 9"+currentCell.getStringCellValue());
                            payroll.setPeriod_m(currentCell.getStringCellValue());
                            break;
//                        case 10:
//                            payroll.setPeriod_y(currentCell.getStringCellValue());
//                            System.out.println("Cell values"+currentCell.getStringCellValue());
//                            break;
//                        case 11:
//                            System.out.println(currentCell.getStringCellValue());
////                            payroll.setSalary(Double.valueOf(currentCell.getStringCellValue()));
//
//                            break;
//                        case 12:
//                            payroll.setNssf(Double.valueOf(currentCell.getStringCellValue()));
//                            break;
//                        case 13:
//                            payroll.setNhif(Double.valueOf(currentCell.getStringCellValue()));
//                            break;
//                        case 14:
//                            payroll.setPaye(Double.valueOf(currentCell.getStringCellValue()));
//                            break;
//                        case 15:
//                            payroll.setHelb(Double.valueOf(currentCell.getStringCellValue()));
//                            break;
//                        case 16:
//                            payroll.setTotal_deductions_net_salary(Double.valueOf(currentCell.getStringCellValue()));
//                            break;
//                        case 17:
//                            payroll.setNet_salary(Double.valueOf(currentCell.getStringCellValue()));
//                            break;

                        default:
                            break;
                    }
                    cellIndex++;
                    payroll.setCreated_at(LocalDateTime.now());
                }
                payrolls.add(payroll);
                System.out.println("Inserted Values" + payrolls);
                if (!rows.hasNext())
                    break;
            }
            workbook.close();
            return payrolls;
        }catch (IOException e){
            throw new RuntimeException("Fail to parse Excel file: "+e.getMessage());
        }
    }
}


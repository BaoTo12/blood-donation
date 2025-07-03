package com.example.blood_donation.service.report;

import com.example.blood_donation.entity.Donation;
import com.example.blood_donation.repository.DonationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ExcelPoiService {

    DonationRepository donationRepository;

    // Fundamental knowledge
    /*
     * POI-OOXML stands for Poor Obfuscation Implementation -Office Open XML
     * Here is how POI treats Office Document
     * POI:s metal model
     *      Workbook (the entire file) - XSSFWorkbook
                Sheets (individual worksheets) - XSSFSheet
                    Rows (horizontal lines of data) - XSSFRow
                        Cells (individual data points) - XSSFCell

     * 1. Memory Management and Performance
     *  - The XSSFWorkbook class loads the entire Excel file structure into memory,
     * which can be problematic for large files. --> you might need to consider POI's streaming API (SXSSFWorkbook)
     * instead of the standard XSSFWorkbook
     *  - Using try-with-resources statements (try (Workbook workbook = new XSSFWorkbook()))
     * 2. Error Handling and Debugging
     *  - Common errors include
     *      + Access rows or cells that don't exist.
     *      + Read data in the wrong format.
     *      + Run out of memory when dealing with large files
     *
     * */

    public ByteArrayOutputStream generateDonationExcelReport() {
        List<Donation> donations = donationRepository.findAll();
        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Donation Report");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"Donation Type", "Amount", "Donation Date"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                // You can add styling here to make headers bold, colored, etc.
            }

            int rowIndex = 1;
            for (Donation donation : donations) {
                Row dataRow = sheet.createRow(rowIndex++);
                // fill data for row
                dataRow.createCell(0).setCellValue(donation.getDonationType());
                dataRow.createCell(1).setCellValue(donation.getAmount());
                dataRow.createCell(2).setCellValue(donation.getCreated_at());
            }

            // Auto-resize columns to fit content - like double-clicking column borders
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream;
        } catch (IOException e) {
            System.err.println("INSIDE EXCEL REPORT");
            throw new RuntimeException(e);
        }
    }
}

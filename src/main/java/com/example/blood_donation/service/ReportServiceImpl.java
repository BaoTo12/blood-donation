package com.example.blood_donation.service;

import com.example.blood_donation.service.report.ExcelPoiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    ExcelPoiService excelPoiService;

    @Value("${report.storage.path:./reports}")
    @NonFinal
    String reportStoragePath;

    @Scheduled(cron = "0 0 2 * * 1")
    public void generateWeeklyDonationReport() {

    }

    public Path generateDonationReportToFile() throws IOException {
        log.info("Generating on-demand donation report");

        ByteArrayOutputStream excelData = excelPoiService.generateDonationExcelReport();

        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
        String filename = String.format("donation_report_%s.xlsx", timeStamp);

        Path reportDirectory = Paths.get(reportStoragePath);
        // Step 3: Ensure the reports directory exists
        // This is crucial - if the directory doesn't exist, file creation will fail
        if (!Files.exists(reportDirectory)) {
            Files.createDirectories(reportDirectory);
            log.info("Created report directory: {}", reportDirectory);
        }

        // Step 4: Create the complete file path
        Path filePath = reportDirectory.resolve(filename);

        // Step 5: Write the Excel data to the file
        // This is where we transform the in-memory data into an actual file
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile())) {
            excelData.writeTo(fileOutputStream);
            log.info("Weekly donation report saved successfully: {}", filePath);
        }

        return filePath;
    }
}

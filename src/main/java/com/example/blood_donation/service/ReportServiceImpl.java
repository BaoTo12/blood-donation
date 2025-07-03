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
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

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
        // Get data
        ByteArrayOutputStream excelData = excelPoiService.generateDonationExcelReport();

        // create fileName with time
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
        String filename = String.format("weekly_donation_report_%s.xlsx", timeStamp);

        // Creates a Path object pointing to the directory where reports will be stored
        // this just gives location
        Path reportDirectory = Paths.get(reportStoragePath);
        if (!Files.exists(reportDirectory)) {
            try {
                Files.createDirectories(reportDirectory);
                // properly joins paths using the correct separator for my OS
                // this means we the reportDirectory is "/home/app/reports"
                // and my file name is "weekly_donation_report_2024_12_15.xlsx"
                // then the filePath become "/home/app/reports/weekly_donation_report_2024_12_15.xlsx"
                Path filePath = reportDirectory.resolve(filename);
                try (FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
                    excelData.writeTo(outputStream);
                }

            } catch (IOException e) {
                // TODO: TEMP
                throw new RuntimeException(e);
            }
        }
        // This removes reports older than 30 days
        cleanupOldReports(reportDirectory);
    }

    private void cleanupOldReports(Path reportDirectory) {
        try {
            LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);

            // Gets all files and subdirectories as a stream
            try (Stream<Path> paths = Files.walk(reportDirectory)) {
                // Excludes directories, only keeps actual files.
                paths.filter(Files::isRegularFile)
                        // Keep only Excel files
                        .filter(path -> path.getFileName().toString().endsWith(".xlsx"))
                        .filter(path -> {
                            try {
                                // Gets when the file was last changed
                                FileTime lastModified = Files.getLastModifiedTime(path);
                                return lastModified.toInstant().isBefore(cutoffDate.atZone(ZoneId.systemDefault()).toInstant());
                            } catch (IOException e) {
                                log.warn("Could not check modification time for file: {}", path, e);
                                return false;
                            }
                        })
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                                log.info("Deleted old report file: {}", path);
                            } catch (IOException e) {
                                log.warn("Could not delete old report file: {}", path, e);
                            }
                        });
            }
        } catch (IOException e) {
            log.warn("Error during old report cleanup", e);
        }
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

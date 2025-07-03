package com.example.blood_donation.service;

import java.io.IOException;
import java.nio.file.Path;

public interface ReportService {
    public Path generateDonationReportToFile() throws IOException;
}

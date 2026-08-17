package br.com.ximed.inspection_api.report;

import br.com.ximed.inspection_api.exception.ResourceNotFoundException;
import br.com.ximed.inspection_api.inspection.domain.Inspection;
import br.com.ximed.inspection_api.inspection.repository.InspectionRepository;
import br.com.ximed.inspection_api.storage.AzureBlobService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final InspectionRepository inspectionRepository;
    private final ReportGenerator reportGenerator;
    private final AzureBlobService storageService;
    private final ReportRepository reportRepository;

    @Transactional
    public Report generateAndSaveReport(UUID inspectionId) {
        Inspection inspection = inspectionRepository
                .findById(inspectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Inspeção não encontrada"));

        // 1. Generate HTML String
        String htmlContent = reportGenerator.generateHtml(inspection);
        
        // 2. Generate PDF bytes
        byte[] pdfBytes = reportGenerator.generatePdf(htmlContent);
        
        // 3. Upload files
        String htmlFileName = "report-" + inspection.getCode() + ".html";
        String pdfFileName = "report-" + inspection.getCode() + ".pdf";
        
        String htmlUrl = storageService.uploadFile(htmlContent.getBytes(StandardCharsets.UTF_8), htmlFileName);
        String pdfUrl = storageService.uploadFile(pdfBytes, pdfFileName);
        
        // 4. Save to DB
        Report report = Report.builder()
                .inspection(inspection)
                .htmlUrl(htmlUrl)
                .pdfUrl(pdfUrl)
                .build();
                
        return reportRepository.save(report);
    }

    @Transactional(readOnly = true)
    public List<Report> getReports(UUID inspectionId) {
        return reportRepository.findByInspectionIdOrderByCreatedAtDesc(inspectionId);
    }
}

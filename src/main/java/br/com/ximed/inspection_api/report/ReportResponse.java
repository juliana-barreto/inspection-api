package br.com.ximed.inspection_api.report;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReportResponse(
        UUID id,
        UUID inspectionId,
        String htmlUrl,
        String pdfUrl,
        LocalDateTime createdAt
) {
    public static ReportResponse fromEntity(Report report) {
        return new ReportResponse(
                report.getId(),
                report.getInspection().getId(),
                report.getHtmlUrl(),
                report.getPdfUrl(),
                report.getCreatedAt()
        );
    }
}

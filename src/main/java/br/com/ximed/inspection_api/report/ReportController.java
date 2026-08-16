package br.com.ximed.inspection_api.report;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inspections/{inspectionId}/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Geração e listagem de relatórios")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    @Operation(summary = "Gerar e salvar um novo relatório (HTML/PDF)")
    public ResponseEntity<ReportResponse> generateReport(@PathVariable UUID inspectionId) {
        Report report = reportService.generateAndSaveReport(inspectionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReportResponse.fromEntity(report));
    }

    @GetMapping
    @Operation(summary = "Listar relatórios gerados para uma inspeção")
    public ResponseEntity<List<ReportResponse>> getReports(@PathVariable UUID inspectionId) {
        List<ReportResponse> reports = reportService.getReports(inspectionId).stream()
                .map(ReportResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reports);
    }
}

package br.com.ximed.inspection_api.report;

import br.com.ximed.inspection_api.company.Sector;
import br.com.ximed.inspection_api.inspection.domain.InspectionLocation;
import br.com.ximed.inspection_api.inspection.domain.Inspection;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportGenerator {

    private final TemplateEngine templateEngine;

    public ReportGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String generateHtml(Inspection inspection) {
        Context context = new Context();
        context.setVariable("inspection", inspection);
        
        List<Sector> uniqueSectors = inspection.getInspectionLocations().stream()
            .map(InspectionLocation::getSector)
            .distinct()
            .collect(Collectors.toList());
        context.setVariable("analyzedSectors", uniqueSectors);

        List<InspectionLocation> sortedLocations = inspection.getInspectionLocations().stream()
            .sorted(Comparator.comparing(loc -> loc.getVisitOrder() != null ? loc.getVisitOrder() : 9999))
            .collect(Collectors.toList());
            
        java.util.Map<Sector, List<InspectionLocation>> groupedBySector = sortedLocations.stream()
            .collect(Collectors.groupingBy(
                InspectionLocation::getSector,
                java.util.LinkedHashMap::new,
                Collectors.toList()
            ));
            
        context.setVariable("groupedLocations", groupedBySector);

        return templateEngine.process("report-template", context);
    }

    public byte[] generatePdf(String html) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, "");
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
}

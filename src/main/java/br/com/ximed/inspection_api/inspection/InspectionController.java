package br.com.ximed.inspection_api.inspection;

import br.com.ximed.inspection_api.inspection.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inspections")
@RequiredArgsConstructor
@Tag(name = "Inspections", description = "Gerenciamento das inspeções de segurança")
public class InspectionController {

    private final InspectionService inspectionService;

    @PostMapping
    @Operation(summary = "Criar relatório de inspeção")
    public ResponseEntity<InspectionResponse> create(@Valid @RequestBody InspectionRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inspectionService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um relatório de inspeção")
    public ResponseEntity<InspectionResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                inspectionService.findById(id)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar os dados gerais do relatório de inspeção")
    public ResponseEntity<InspectionResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody InspectionRequest request
    ) {
        return ResponseEntity.ok(
                inspectionService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um relatório de inspeção")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        inspectionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{inspectionId}/locations")
    @Operation(summary = "Adicionar uma localização à inspeção")
    public ResponseEntity<InspectionLocationResponse> addLocation(
            @PathVariable UUID inspectionId,
            @Valid @RequestBody InspectionLocationRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inspectionService.addLocation(inspectionId, request));
    }

    @PostMapping("/{inspectionId}/locations/{locationId}/items")
    @Operation(summary = "Adicionar um item à localização inspecionada")
    public ResponseEntity<InspectionItemResponse> Items(
            @PathVariable UUID inspectionId,
            @PathVariable UUID locationId,
            @Valid @RequestBody InspectionItemRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inspectionService.addItem(inspectionId, locationId, request));
    }

    @PatchMapping("/{id}/submit")
    @Operation(summary = "Enviar relatório de inspeção para aprovação")
    public ResponseEntity<InspectionResponse> submit(@PathVariable UUID id) {
        return ResponseEntity.ok(inspectionService.submitForApproval(id));
    }

    @PatchMapping("/{id}/pause")
    @Operation(summary = "Pausar relatório de inspeção")
    public ResponseEntity<InspectionResponse> pause(@PathVariable UUID id) {
        return ResponseEntity.ok(inspectionService.pause(id));
    }
}
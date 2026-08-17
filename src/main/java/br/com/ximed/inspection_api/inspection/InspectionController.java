package br.com.ximed.inspection_api.inspection;

import br.com.ximed.inspection_api.inspection.domain.enums.InspectionStatus;
import br.com.ximed.inspection_api.inspection.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping
    @Operation(summary = "Listar e filtrar relatórios de inspeção")
    public ResponseEntity<List<InspectionSummaryResponse>> findAll(
            @RequestParam(required = false) UUID siteId,
            @RequestParam(required = false) InspectionStatus status
    ) {
        return ResponseEntity.ok(inspectionService.findAll(siteId, status));
    }

    @GetMapping("/active-draft")
    @Operation(summary = "Obter o rascunho de inspeção ativo do usuário")
    public ResponseEntity<InspectionResponse> getActiveDraft() {
        InspectionResponse draft = inspectionService.getActiveDraft();
        if (draft == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(draft);
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
    public ResponseEntity<InspectionItemResponse> addItem(
            @PathVariable UUID inspectionId,
            @PathVariable UUID locationId,
            @Valid @RequestBody InspectionItemRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inspectionService.addItem(inspectionId, locationId, request));
    }

    @DeleteMapping("/{inspectionId}/locations/{locationId}")
    @Operation(summary = "Excluir uma localização da inspeção")
    public ResponseEntity<Void> deleteLocation(
            @PathVariable UUID inspectionId,
            @PathVariable UUID locationId
    ) {
        inspectionService.deleteLocation(inspectionId, locationId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{inspectionId}/locations/{locationId}/items/{itemId}")
    @Operation(summary = "Excluir um item da localização")
    public ResponseEntity<Void> deleteItem(
            @PathVariable UUID inspectionId,
            @PathVariable UUID locationId,
            @PathVariable UUID itemId
    ) {
        inspectionService.deleteItem(inspectionId, locationId, itemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{inspectionId}/locations/{locationId}")
    @Operation(summary = "Atualizar uma localização da inspeção")
    public ResponseEntity<InspectionLocationResponse> updateLocation(
            @PathVariable UUID inspectionId,
            @PathVariable UUID locationId,
            @Valid @RequestBody InspectionLocationRequest request
    ) {
        return ResponseEntity.ok(inspectionService.updateLocation(inspectionId, locationId, request));
    }

    @PutMapping("/{inspectionId}/locations/{locationId}/items/{itemId}")
    @Operation(summary = "Atualizar um item da localização")
    public ResponseEntity<InspectionItemResponse> updateItem(
            @PathVariable UUID inspectionId,
            @PathVariable UUID locationId,
            @PathVariable UUID itemId,
            @Valid @RequestBody InspectionItemRequest request
    ) {
        return ResponseEntity.ok(inspectionService.updateItem(inspectionId, locationId, itemId, request));
    }



    @PatchMapping("/{id}/submit")
    @Operation(summary = "Enviar relatório de inspeção para aprovação")
    public ResponseEntity<InspectionResponse> submit(@PathVariable UUID id) {
        return ResponseEntity.ok(inspectionService.submitForApproval(id));
    }




    @PostMapping(value = "/{inspectionId}/locations/{locationId}/items/{itemId}/evidences", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Adicionar uma foto/evidência a um item da inspeção")
    public ResponseEntity<EvidenceResponse> addEvidence(
            @PathVariable UUID inspectionId,
            @PathVariable UUID locationId,
            @PathVariable UUID itemId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "caption", required = false) String caption
    ) throws java.io.IOException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inspectionService.addEvidence(inspectionId, locationId, itemId, file, caption));
    }

}
package br.com.ximed.inspection_api.inspection.repository;

import br.com.ximed.inspection_api.inspection.domain.InspectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface InspectionItemRepository extends JpaRepository<InspectionItem, UUID> {
}

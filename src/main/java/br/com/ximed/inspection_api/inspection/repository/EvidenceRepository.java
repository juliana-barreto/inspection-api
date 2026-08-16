package br.com.ximed.inspection_api.inspection.repository;

import br.com.ximed.inspection_api.inspection.domain.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, UUID> {
}

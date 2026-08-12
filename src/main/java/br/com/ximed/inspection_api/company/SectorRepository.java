package br.com.ximed.inspection_api.company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SectorRepository extends JpaRepository<Sector, UUID> {

    // Retorna todos os setores de uma filial
    List<Sector> findBySiteId(UUID siteId);
}
package br.com.ximed.inspection_api.company;

import br.com.ximed.inspection_api.inspection.domain.InspectionLocation;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sectors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @Column(name = "name", nullable = false)
    private String name;

    // Não deve ser removido em cascata, pois a exclusão de um setor não deve excluir as localizações de inspeção associadas.
    @Builder.Default
    @OneToMany(mappedBy = "sector")
    private List<InspectionLocation> inspectionLocations = new ArrayList<>();
}

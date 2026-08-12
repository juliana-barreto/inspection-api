package br.com.ximed.inspection_api.company;

import br.com.ximed.inspection_api.inspection.domain.Inspection;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    @Column(name = "cnae", nullable = false)
    private String cnae;

    @Column(name = "address", nullable = false)
    private String address;

    @Builder.Default
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sector> sectors = new ArrayList<>();

    // Não deve ser removido em cascata, pois a exclusão de uma filial não deve excluir as inspeções associadas.
    @Builder.Default
    @OneToMany(mappedBy = "site")
    private List<Inspection> inspections = new ArrayList<>();
}

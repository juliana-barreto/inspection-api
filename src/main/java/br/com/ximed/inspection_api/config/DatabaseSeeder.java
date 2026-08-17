package br.com.ximed.inspection_api.config;

import br.com.ximed.inspection_api.company.Company;
import br.com.ximed.inspection_api.company.CompanyRepository;
import br.com.ximed.inspection_api.company.Sector;
import br.com.ximed.inspection_api.company.SectorRepository;
import br.com.ximed.inspection_api.company.Site;
import br.com.ximed.inspection_api.company.SiteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final SiteRepository siteRepository;
    private final SectorRepository sectorRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (companyRepository.count() == 0) {
            log.info("Semeando dados iniciais de leitura (Company, Site, Sector)...");
            seedCompanyHierarchy();
        } else {
            log.info("Banco de dados já contém empresas. Seed ignorado.");
        }
    }

    private void seedCompanyHierarchy() {
        // Criando a Empresa (Cliente da Ximed)
        Company company = Company.builder()
                .corporateName("Rádio e Televisão Record S.A.")
                .tradeName("Record TV")
                .build();
        company = companyRepository.save(company);

        // Criando Filial 1 (Sede Administrativa e Jornalismo - São Paulo)
        Site site1 = Site.builder()
                .company(company)
                .name("Sede Administrativa - Barra Funda")
                .cnpj("60.500.000/0001-12") // CNPJ Fictício formatado
                .cnae("6021-7/00") // Atividades de televisão aberta
                .address("Rua da Várzea, 240, Barra Funda - São Paulo/SP")
                .build();
        site1 = siteRepository.save(site1);

        // Setores da Filial 1 (Foco em ergonomia, elétrica e estúdios de jornalismo)
        List<Sector> site1Sectors = List.of(
                Sector.builder().site(site1).name("Estúdios de Jornalismo").build(),
                Sector.builder().site(site1).name("Redação e Ilhas de Edição").build(),
                Sector.builder().site(site1).name("Controle Mestre (Master)").build(),
                Sector.builder().site(site1).name("Manutenção de Transmissores").build(),
                Sector.builder().site(site1).name("Administrativo e RH").build()
        );
        sectorRepository.saveAll(site1Sectors);

        // Criando Filial 2 (Complexo de Estúdios de Teledramaturgia - Rio de Janeiro)
        Site site2 = Site.builder()
                .company(company)
                .name("Complexo de Estúdios (RecNov)")
                .cnpj("60.500.000/0002-95") // CNPJ Fictício formatado
                .cnae("6021-7/00")
                .address("Estrada dos Bandeirantes, 23505, Vargem Grande - Rio de Janeiro/RJ")
                .build();
        site2 = siteRepository.save(site2);

        // Setores da Filial 2 (Foco em riscos físicos, maquinário pesado e infraestrutura)
        List<Sector> site2Sectors = List.of(
                Sector.builder().site(site2).name("Cenografia e Marcenaria").build(),
                Sector.builder().site(site2).name("Estúdios de Teledramaturgia").build(),
                Sector.builder().site(site2).name("Cidade Cenográfica (Externa)").build(),
                Sector.builder().site(site2).name("Geradores e Subestação Elétrica").build(),
                Sector.builder().site(site2).name("Acervo de Figurinos").build()
        );
        sectorRepository.saveAll(site2Sectors);

        log.info("Seed de hierarquia concluído com sucesso para a Record TV.");
    }
}
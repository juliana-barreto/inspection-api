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
        // Criando a Empresa Fictícia
        Company company = Company.builder()
                .corporateName("Apex Soluções Industriais e Serviços Ltda.")
                .tradeName("Apex Industrial")
                .build();
        company = companyRepository.save(company);

        // Criando Filial 1 (Sede)
        Site site1 = Site.builder()
                .company(company)
                .name("Sede / Rio de Janeiro")
                .cnpj("12.345.678/0001-90")
                .cnae("7020-4/00")
                .address("Avenida Pastor Martin Luther King Jr., 126, Del Castilho - Rio de Janeiro/RJ")
                .build();
        site1 = siteRepository.save(site1);

        // Setores da Filial 1
        List<Sector> site1Sectors = List.of(
                Sector.builder().site(site1).name("Escritório de RH").build(),
                Sector.builder().site(site1).name("Departamento de TI").build(),
                Sector.builder().site(site1).name("Operações e Logística").build(),
                Sector.builder().site(site1).name("Administrativo e Financeiro").build()
        );
        sectorRepository.saveAll(site1Sectors);

        // Criando Filial 2 (São Paulo)
        Site site2 = Site.builder()
                .company(company)
                .name("Filial / São Paulo")
                .cnpj("12.345.678/0002-71")
                .cnae("7020-4/00")
                .address("Avenida das Nações Unidas, 4500, Pinheiros - São Paulo/SP")
                .build();
        site2 = siteRepository.save(site2);

        // Setores da Filial 2
        List<Sector> site2Sectors = List.of(
                Sector.builder().site(site2).name("Almoxarifado e Estoque").build(),
                Sector.builder().site(site2).name("Manutenção e Infraestrutura").build(),
                Sector.builder().site(site2).name("Controle de Qualidade").build(),
                Sector.builder().site(site2).name("Produção e Montagem").build()
        );
        sectorRepository.saveAll(site2Sectors);

        log.info("Seed de hierarquia concluído com sucesso.");
    }
}


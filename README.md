# Inspection API

API RESTful para gestão e realização de inspeções de segurança e conformidade.

## Diagrama de Entidade e Relacionamento (ERD)

```mermaid
erDiagram

    COMPANY ||--o{ SITE : possui
    SITE ||--o{ SECTOR : possui

    SITE ||--o{ INSPECTION : recebe
    SECTOR ||--o{ INSPECTION_AREA : agrupa

    INSPECTION ||--o{ INSPECTION_AREA : possui
    INSPECTION_AREA ||--o{ INSPECTION_ITEM : possui
    INSPECTION_ITEM ||--o{ EVIDENCE : possui


    COMPANY {
        uuid id PK
        varchar corporate_name
        varchar trade_name
    }

    SITE {
        uuid id PK
        uuid company_id FK
        varchar name
        varchar cnpj
        varchar cnae
        varchar address
    }

    SECTOR {
        uuid id PK
        uuid site_id FK
        varchar name
        text description
    }

    INSPECTION {
        uuid id PK
        uuid site_id FK

        varchar identification
        text objective
        boolean is_multisectoral

        varchar inspector_name
        varchar inspector_job_title
        varchar inspector_technical_registration

        timestamp started_at
        timestamp ended_at
        varchar status

        timestamp created_at
        timestamp updated_at
    }

    INSPECTION_AREA {
        uuid id PK
        uuid inspection_id FK
        uuid sector_id FK

        varchar location_name
        varchar sublocation_name

        text environment_description
        text activities_summary

        text exposed_job_roles
        integer exposed_workers_count

        integer visit_order

        timestamp created_at
        timestamp updated_at
    }

    INSPECTION_ITEM {
        uuid id PK
        uuid inspection_area_id FK

        varchar situation

        text description
        varchar risk_type
        text hazard_description
        text possible_harm

        varchar probability
        varchar severity
        varchar risk_level

        varchar nr_code
        varchar nr_item

        text observation

        text corrective_measure
        varchar responsible_name
        date deadline

        timestamp created_at
        timestamp updated_at
    }

    EVIDENCE {
        uuid id PK
        uuid inspection_item_id FK

        varchar image_url
        text caption

        timestamp created_at
    }
```

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 4**
- **PostgreSQL**

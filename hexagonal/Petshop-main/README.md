<p align="center">
  <h1 align="center"> PETSHOP </h1>
  <p align="center">Sistema de Gestão para Pet Shop - Backend API + Frontend Angular</p>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=flat&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Boot-4.0.6-6DB33F?style=flat&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Angular-20.3-DD0031?style=flat&logo=angular&logoColor=white" />
  <img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" />
</p>

---

## Sumário

- [Sobre](#sobre)
- [Arquitetura](#arquitetura)
- [Tecnologias](#tecnologias)
- [Endpoints da API](#endpoints-da-api)
- [Modelos de Dados](#modelos-de-dados)
- [Frontend](#frontend)
- [Como Rodar](#como-rodar)
- [Testes](#testes)
- [CI/CD](#cicd)
- [Estrutura do Projeto](#estrutura-do-projeto)

---

## Sobre

O **PetShop** é um sistema completo para gestão de pet shop, desenvolvido com arquitetura hexagonal (Ports & Adapters). O projeto gerencia **clientes**, **produtos** e **pedidos**, com comunicação assíncrona via JMS e documentação Swagger/OpenAPI.

**Funcionalidades:**
- CRUD completo de Clientes (com validação de CPF e endereço)
- CRUD completo de Produtos (com paginação)
- CRUD completo de Pedidos (com máquina de estados)
- Processamento assíncrono de pedidos via ActiveMQ
- Frontend Angular com interface responsiva
- CI/CD automatizado com GitHub Actions

---

## Arquitetura

O projeto segue o padrão **Hexagonal (Ports & Adapters)**:

```
┌─────────────────────────────────────────────────────────┐
│                      DOMAIN                             │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐               │
│  │  Models  │  │   DTOs   │  │  Enums   │               │
│  └──────────┘  └──────────┘  └──────────┘               │
│  ┌──────────────────────┐  ┌──────────────────────┐     │
│  │   Port IN (Use Cases)│  │  Port OUT (Repos)    │     │
│  └──────────────────────┘  └──────────────────────┘     │
└─────────────────────────────────────────────────────────┘
         │                           │
    ┌────▼─────┐                ┌─────▼─────┐
    │adapter.in│                │adapter.out│
    │  (REST)  │                │  (JPA/JMS)│
    └──────────┘                └───────────┘
```

**Camadas:**

| Camada | Pacote | Responsabilidade |
|--------|--------|------------------|
| **Domain** | `domain.model`, `domain.dto`, `domain.enums` | Entidades JPA, DTOs, enums, exceções de negócio |
| **Ports IN** | `domain.port.in` | Interfaces de casos de uso (CustomerUseCase, OrderUseCase, ProductUseCase) |
| **Ports OUT** | `domain.port.out` | Interfaces de repositórios (CustomerRepository, OrderRepository, ProductRepository) |
| **Application** | `application.service`, `application.config` | Implementação dos casos de uso, configs do Spring |
| **Adapter IN** | `adapter.in.web` | Controllers REST, tratamento global de exceções |
| **Adapter OUT** | `adapter.out.persistence`, `adapter.out.messaging` | Repositórios JPA, produtores/consumidores JMS |

---

## Tecnologias

### Backend

| Tecnologia | Versão | Finalidade |
|------------|--------|------------|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.6 | Framework principal |
| Spring Data JPA | - | ORM com Hibernate |
| H2 Database | 2.4.240 | Banco de dados em memória |
| ActiveMQ | - | Mensageria JMS |
| SpringDoc OpenAPI | 2.8.6 | Documentação Swagger |
| Lombok | - | Redução de boilerplate |
| Maven | - | Build tool |
| JaCoCo | 0.8.12 | Cobertura de código |
| JUnit 5 + Mockito | - | Testes |

### Frontend

| Tecnologia | Versão | Finalidade |
|------------|--------|------------|
| Angular | 20.3 | Framework SPA |
| TypeScript | 5.9 | Tipagem estática |
| RxJS | 7.8 | Programação reativa |
| FormsModule | - | Formulários template-driven |

---

## Endpoints da API

### Clientes (`/api/customers`)

| Método | Caminho | Descrição | Request Body | Response |
|--------|---------|-----------|--------------|----------|
| `POST` | `/api/customers` | Criar cliente | `CreateCustomerRequest` | `CustomerDTO` (201) |
| `GET` | `/api/customers` | Listar todos | - | `List<CustomerDTO>` (200) |
| `GET` | `/api/customers/{id}` | Buscar por ID | - | `CustomerDTO` (200) |
| `PUT` | `/api/customers/{id}` | Atualizar | `CreateCustomerRequest` | `CustomerDTO` (200) |
| `DELETE` | `/api/customers/{id}` | Deletar | - | 204 |
| `GET` | `/api/customers/exists-cpf/{cpf}` | Verificar CPF | - | `Boolean` (200) |

### Produtos (`/api/products`)

| Método | Caminho | Descrição | Request Body | Response |
|--------|---------|-----------|--------------|----------|
| `POST` | `/api/products` | Criar produto | `CreateProductRequest` | `ProductDTO` (201) |
| `GET` | `/api/products?page={page}&size={size}` | Listar com paginação | - | `Page<ProductDTO>` (200) |
| `GET` | `/api/products/{id}` | Buscar por ID | - | `ProductDTO` (200) |
| `PUT` | `/api/products/{id}` | Atualizar | `CreateProductRequest` | `ProductDTO` (200) |
| `DELETE` | `/api/products/{id}` | Deletar | - | 204 |

### Pedidos (`/api/orders`)

| Método | Caminho | Descrição | Request Body | Response |
|--------|---------|-----------|--------------|----------|
| `POST` | `/api/orders` | Criar pedido | `CreateOrderRequest` | `OrderDTO` (201) |
| `GET` | `/api/orders` | Listar todos | - | `List<OrderDTO>` (200) |
| `GET` | `/api/orders/{id}` | Buscar por ID | - | `OrderDTO` (200) |
| `PATCH` | `/api/orders/{id}/status` | Atualizar status | `UpdateOrderStatusRequest` | `OrderDTO` (200) |

### Máquina de Estados do Pedido

```
PENDING → PROCESSING → COMPLETED
                  ↘ FAILED
PENDING → FAILED
```

---

## Modelos de Dados

### Customer

| Campo | Tipo | Validação |
|-------|------|-----------|
| `name` | `String` | `@NotBlank` |
| `phone` | `String` | `@NotBlank` |
| `cpf` | `String` | `@CPF`, único |
| `bornDate` | `LocalDate` | `@NotNull` |
| `address` | `Address` | `@Valid`, `@NotNull` |

### Address

| Campo | Tipo | Validação |
|-------|------|-----------|
| `street` | `String` | `@NotBlank` |
| `number` | `String` | `@NotBlank` |
| `city` | `String` | `@NotBlank` |
| `state` | `String` | `@NotBlank` |
| `zipCode` | `String` | `@NotBlank`, mínimo 8 dígitos |
| `addressType` | `AddressType` | `@NotNull` |

### Product

| Campo | Tipo | Validação |
|-------|------|-----------|
| `name` | `String` | `@NotBlank` |
| `description` | `String` | - |
| `category` | `Category` | `@NotNull` |
| `price` | `BigDecimal` | `@NotNull`, `@Positive` |

### Order

| Campo | Tipo | Validação |
|-------|------|-----------|
| `customerName` | `String` | `@NotBlank` |
| `productId` | `Long` | `@NotNull` |
| `quantity` | `Integer` | `@NotNull`, `@Min(1)` |

### Enums

**Category:**

| Valor | Descrição |
|-------|-----------|
| `FOOD` | Alimentação |
| `TOY` | Brinquedos |
| `ACCESSORY` | Acessórios |
| `MEDICINE` | Medicamentos |

**OrderStatus:**

| Valor | Transições Permitidas |
|-------|----------------------|
| `PENDING` | PROCESSING, FAILED |
| `PROCESSING` | COMPLETED, FAILED |
| `COMPLETED` | *(terminal)* |
| `FAILED` | *(terminal)* |

**AddressType:**

| Valor | Descrição |
|-------|-----------|
| `HOME` | Residencial |
| `JOB` | Comercial |
| `OTHERS` | Outros |

---

## Frontend

### Rotas

| Rota | Componente | Descrição |
|------|------------|-----------|
| `/` | Redirect → `/orders` | Rota padrão |
| `/orders` | OrderListComponent | Lista de pedidos |
| `/orders/new` | OrderCreateComponent | Criar pedido |
| `/customers` | CustomerList | Lista de clientes |
| `/customers/new` | CustomerCreate | Cadastrar cliente |
| `/customers/:id/edit` | CustomerEdit | Editar cliente |
| `/products` | ProductList | Lista de produtos |
| `/products/new` | ProductCreate | Cadastrar produto |

### Funcionalidades

- **Pedidos:** Filtro por status, badges coloridos, criação com seleção de produto
- **Clientes:** Busca por nome, validação de CPF, endereço completo com tipo
- **Produtos:** Busca por nome, categorias com badges, paginação no backend
- **Navegação:** Sidebar lateral com links ativos destacados

---

## Como Rodar

### Pré-requisitos

- Java 21
- Maven 3.8+
- Node.js 18+ (para o frontend)

### Backend

```bash
# Instalar dependências e rodar
mvn spring-boot:run

# A aplicação estará disponível em:
# API: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui.html
# H2 Console: http://localhost:8080/h2-console
```

### Frontend

```bash
cd frontend

# Instalar dependências
npm install

# Rodar em modo de desenvolvimento
ng serve

# A aplicação estará disponível em:
# http://localhost:4200
```

### Dados Iniciais

O projeto inclui um `data.sql` com dados de exemplo:
- 5 clientes
- 10 produtos (em 4 categorias)
- 5 pedidos (com status variados)
- 5 endereços

---

## Testes

```bash
# Rodar todos os testes
mvn test

# Gerar relatório de cobertura
mvn jacoco:report

# Relatório disponível em:
# target/site/jacoco/index.html
```

### Cobertura de Testes

| Classe | Tipo | Testes |
|--------|------|--------|
| `CustomerControllerTest` | @WebMvcTest | 8 testes (CRUD completo) |
| `CustomerServiceTest` | Unit (Mockito) | 8 testes |
| `OrderServiceTest` | Unit (Mockito) | 3 testes |

---

## CI/CD

Pipeline automatizado via **GitHub Actions** (`.github/workflows/ci-cd.yml`):

| Etapa | Comando |
|-------|---------|
| 1. Checkout | `actions/checkout@v4` |
| 2. Setup JDK 21 | `actions/setup-java@v4` (Temurin) |
| 3. Build | `mvn clean compile -B` |
| 4. Testes | `mvn test -B` |
| 5. Cobertura | `mvn jacoco:report -B` |
| 6. Upload Coverage | `codecov/codecov-action@v4` |
| 7. Package | `mvn package -DskipTests -B` |
| 8. Upload Artifact | `actions/upload-artifact@v4` |

**Trigger:** Push para `main`/`develop` ou PR para `main`.

---

## Estrutura do Projeto

```
petshop/
├── .github/workflows/
│   └── ci-cd.yml                    # Pipeline CI/CD
├── frontend/                         # Angular 20
│   ├── src/app/
│   │   ├── customers/               # Componentes de cliente
│   │   ├── orders/                  # Componentes de pedido
│   │   ├── products/                # Componentes de produto
│   │   └── shared/                  # Models e services compartilhados
│   └── ...
├── src/main/java/com/petshop/company/
│   ├── PetshopApplication.java      # Entry point
│   ├── adapter/
│   │   ├── in/web/
│   │   │   ├── controller/          # REST Controllers
│   │   │   └── exception/           # GlobalExceptionHandler
│   │   └── out/
│   │       ├── persistence/         # JPA Repositories + Mappers
│   │       └── messaging/           # JMS Producer/Listener
│   ├── application/
│   │   ├── config/                  # Configs (OpenAPI, JMS, Web)
│   │   └── service/                 # Business logic
│   ├── domain/
│   │   ├── dto/                     # Data Transfer Objects
│   │   ├── exception/               # Custom Exceptions
│   │   ├── model/                   # JPA Entities
│   │   │   └── enums/               # Category, OrderStatus, AddressType
│   │   └── port/
│   │       ├── in/                  # Use Case interfaces
│   │       └── out/                 # Repository interfaces
│   └── consumer/
│       └── OrderConsumer.java       # JMS consumer
├── src/main/resources/
│   ├── application.properties       # Configurações
│   └── data.sql                     # Dados iniciais
├── src/test/                        # Testes unitários e de integração
├── pom.xml                          # Dependências Maven
└── README.md
```

---

## Licença

Este projeto está sob a licença Apache 2.0. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

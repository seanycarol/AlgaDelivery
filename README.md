
# AlgaDelivery

**AlgaDelivery** é uma aplicação baseada em microsserviços que simula um sistema de gestão e rastreamento de entregas. O projeto foi desenvolvido com foco no aprendizado de **arquitetura de microsserviços**, **Domain-Driven Design (DDD)** e **padrões de resiliência**, utilizando tecnologias modernas como **Spring Boot**, **Kafka**, **PostgreSQL**, e **Docker**.

---

## Estrutura do Projeto

O projeto é composto por 4 microsserviços principais:

```
AlgaDelivery/
├── Microservices/
│   ├── Courier-Management/  # Gerencia os entregadores e atribuições
│   ├── Delivery-Tracking/   # Lida com pedidos e rastreamento de entregas
│   ├── Gateway/             # API Gateway com Spring Cloud Gateway
│   └── Service-Registry/    # Registro de serviços com Eureka  
```

---

## Pré-requisitos

- Docker e Docker Compose instalados
- Java 21 (para rodar os microsserviços localmente)
  
##  Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Web / RestClient**
- **Spring Data JPA**
- **Spring Cloud Gateway**
- **Spring Cloud Eureka**
- **Maven**
- **Apache Kafka**
- **PostgreSQL**
- **Docker e Docker Compose**
- **Resilience4j** (Circuit Breaker, Retry, Timeout)
- **REST Assured** (Testes de integração)

---

## Funcionalidades Implementadas

### Microsserviços

- **Courier-Management**
  - Cadastro e gerenciamento de entregadores
  - Atribuição de entregas via eventos Kafka
  - Cálculo de pagamentos por entrega

- **Delivery-Tracking**
  - Registro de pedidos e status de entrega
  - Emissão de eventos de domínio conforme o ciclo de vida da entrega

- **Gateway**
  - Roteamento inteligente via Spring Cloud Gateway
  - Padrões de Resiliência com Circuit Breaker, Retry e Timeout
  - Manipulação de requisições e respostas com filtros

- **Service-Registry**
  - Registro e descoberta de serviços com Spring Eureka

### Comunicação entre Microsserviços

- **Síncrona (REST)**:
  - `Delivery-Tracking` chama `Courier-Management` via RestClient
- **Assíncrona (Kafka)**:
  - Eventos de domínio publicados por `Delivery-Tracking`
  - Consumidos por `Courier-Management`

---

## Arquitetura

<img width="982" height="639" alt="algadelivery-diagrama" src="https://github.com/user-attachments/assets/0d46638d-79be-4aaa-af6b-61ae01e969fb" />

---

## Como Executar com Docker

1. **Clone o projeto:**
   ```bash
   git clone https://github.com/seanycarol/AlgaDelivery.git
   cd AlgaDelivery
   ```

2. **Suba o ambiente com Docker Compose:**
   ```bash
   docker-compose up -d
   ```

3. **Execute cada microsserviço na IDE ou por terminal:**
   - `Courier-Management`: Porta 8081
   - `Delivery-Tracking`: Porta 8080
   - `Gateway`: Porta 9999  
   - `Kafka`: Porta 8084
   - `Service-Registry (Eureka)`: Porta 8761 

4. **Parar o ambiente com Docker Compose:**
   ```bash
   docker-compose down
   ```
---

## Principais Conceitos Aplicados

- **Domain-Driven Design (DDD)**
  - Estratégico: separação de contextos (`Delivery`, `Courier`)
  - Tático: entidades, agregados, eventos de domínio, serviços de domínio

- **Arquitetura Orientada a Eventos**
  - Integração entre microsserviços desacoplada via Kafka

- **Padrões de Microsserviços**
  - Service Registry com Eureka
  - API Gateway com Spring Cloud Gateway
  - Resilience Patterns com Resilience4j

---

## Testes

```
AlgaDelivery/
├── Docs/                      # Contém a coleção Postman (para testar as APIs)
│   └── postman-collection.yml
```

- Testes de integração com REST Assured
- Testes de persistência com banco PostgreSQL
- Organização modular e orientada a boas práticas

---

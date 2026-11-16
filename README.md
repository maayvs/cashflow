# 💰 Cashflow

API REST de controle financeiro pessoal, desenvolvida com **Java** e **Spring Boot**.

Permite cadastrar receitas e despesas, consultar, atualizar, excluir e calcular o saldo, com os dados salvos em um banco **MySQL** rodando em **Docker**.

> 🚧 **Projeto em desenvolvimento.** Novas funcionalidades estão sendo adicionadas. Veja o [roadmap](#-roadmap).

---

## ✨ Funcionalidades

- ✅ CRUD completo de transações (receitas e despesas)
- ✅ Cálculo de saldo (receitas − despesas)
- ✅ Validações de negócio (descrição obrigatória, valor maior que zero, tipo obrigatório)
- ✅ Tratamento global de erros, com respostas padronizadas e status HTTP corretos
- ✅ Persistência em MySQL com Spring Data JPA e Hibernate
- ✅ Banco de dados em container Docker, com Docker Compose

---

## 🛠️ Tecnologias

| Tecnologia | Uso |
|---|---|
| **Java 25** | Linguagem |
| **Spring Boot 4.1** | Framework principal |
| **Spring Web** | API REST |
| **Spring Data JPA + Hibernate** | Persistência e mapeamento objeto-relacional |
| **MySQL 8.4** | Banco de dados |
| **Docker / Docker Compose** | Ambiente do banco de dados |
| **Maven** | Gerenciamento de dependências e build |

---

## 🏛️ Arquitetura

O projeto é organizado em camadas, cada uma com uma responsabilidade:

```
src/main/java/com/mayara/cashflow
├── controller/   → Recebe as requisições HTTP e devolve as respostas
├── service/      → Regras de negócio
├── repository/   → Acesso ao banco de dados (Spring Data JPA)
├── model/        → Entidades do domínio (Transaction, TransactionType)
├── dto/          → Objetos de entrada e saída da API
└── exception/    → Exceções personalizadas e tratamento global de erros
```

**Fluxo de uma requisição:**

```
Cliente → Controller → Service → Repository → MySQL
```

---

## 📌 Endpoints

| Método | Endpoint | Descrição | Respostas |
|---|---|---|---|
| `POST` | `/transactions` | Cadastra uma transação | `201`, `400` |
| `GET` | `/transactions` | Lista todas as transações | `200` |
| `GET` | `/transactions/{id}` | Busca uma transação pelo id | `200`, `404` |
| `PUT` | `/transactions/{id}` | Atualiza uma transação | `200`, `400`, `404` |
| `DELETE` | `/transactions/{id}` | Exclui uma transação | `204`, `404` |
| `GET` | `/transactions/balance` | Calcula o saldo | `200` |

### Exemplo: cadastrar uma transação

**Requisição**

```http
POST /transactions
Content-Type: application/json
```

```json
{
  "description": "Salário",
  "amount": 4500.00,
  "date": "2026-11-05",
  "type": "INCOME"
}
```

O campo `type` aceita `INCOME` (receita) ou `EXPENSE` (despesa).

**Resposta: `201 Created`**

```json
{
  "id": 1,
  "description": "Salário",
  "amount": 4500.00,
  "date": "2026-11-05",
  "type": "INCOME"
}
```

### Exemplo: resposta de erro

Todos os erros seguem o mesmo formato:

```json
{
  "status": 400,
  "message": "O valor deve ser maior que zero.",
  "timestamp": "2026-11-16T01:30:00"
}
```

| Status | Quando acontece |
|---|---|
| `400 Bad Request` | Dados inválidos ou JSON malformado |
| `404 Not Found` | Transação não encontrada |

---

## 🚀 Como executar

### Pré-requisitos

- [Java 25](https://adoptium.net/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

### Passo a passo

**1. Clone o repositório**

```bash
git clone https://github.com/maayvs/cashflow.git
cd cashflow
```

**2. Suba o banco de dados**

```bash
docker compose up -d
```

O MySQL fica disponível na porta **3307**.

**3. Rode a aplicação**

No Windows:

```bash
mvnw.cmd spring-boot:run
```

No Linux ou Mac:

```bash
./mvnw spring-boot:run
```

Ou, pela IDE, execute a classe `CashflowApplication`.

A API fica disponível em **http://localhost:8080**.

---

## 🗺️ Roadmap

- [x] CRUD de transações
- [x] Cálculo de saldo
- [x] Tratamento global de erros
- [x] Persistência com MySQL + Hibernate
- [x] Banco de dados com Docker Compose
- [ ] Validações com Bean Validation
- [ ] Categorias de transações
- [ ] Relatórios mensais (saldo do mês, gastos por categoria)
- [ ] Cadastro de usuários e login com Spring Security + JWT
- [ ] Testes automatizados (JUnit, Mockito e Testcontainers)
- [ ] Documentação da API com Swagger (SpringDoc)

---

## 👩‍💻 Autora

**Mayara Vieira da Silva**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/mayara-vieira-da-silva-471920226/)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/maayvs)

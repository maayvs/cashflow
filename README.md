# 💰 Cashflow

API REST de controle financeiro pessoal, desenvolvida com **Java** e **Spring Boot**.

Permite cadastrar receitas e despesas, organizá-las por categoria, gerar relatórios mensais e calcular o saldo, com os dados salvos em um banco **MySQL** rodando em **Docker**. Conta com cadastro de usuários e login com **token JWT**.

> 🚧 **Projeto em desenvolvimento.** Novas funcionalidades estão sendo adicionadas. Veja o [roadmap](#-roadmap).

---

## ✨ Funcionalidades

- ✅ CRUD completo de transações (receitas e despesas)
- ✅ Categorias, com relacionamento entre transações e categorias
- ✅ Relatórios mensais: resumo do mês e totais por categoria
- ✅ Cálculo de saldo (receitas − despesas)
- ✅ Validações automáticas com Bean Validation, retornando todos os erros de uma vez
- ✅ Tratamento global de erros, com respostas padronizadas e status HTTP corretos
- ✅ Cadastro de usuários com senha criptografada (BCrypt)
- ✅ Login com geração de token JWT (Spring Security)
- ✅ Persistência em MySQL com Spring Data JPA e Hibernate
- ✅ Banco de dados em container Docker, com Docker Compose

---

## 🛠️ Tecnologias

| Tecnologia | Uso |
|---|---|
| **Java 25** | Linguagem |
| **Spring Boot 4.1** | Framework principal |
| **Spring Web MVC** | API REST |
| **Spring Data JPA + Hibernate** | Persistência e mapeamento objeto-relacional |
| **Bean Validation** | Validação dos dados de entrada |
| **Spring Security** | Autenticação e criptografia de senhas (BCrypt) |
| **OAuth2 Resource Server (JWT)** | Geração e validação de tokens JWT |
| **MySQL 8.4** | Banco de dados |
| **Docker / Docker Compose** | Ambiente do banco de dados |
| **Maven** | Gerenciamento de dependências e build |

---

## 🏛️ Arquitetura

O projeto é organizado em camadas, cada uma com uma responsabilidade:

```
src/main/java/com/mayara/cashflow
├── config/       → Configurações de segurança e JWT
├── controller/   → Recebe as requisições HTTP e devolve as respostas
├── service/      → Regras de negócio
├── repository/   → Acesso ao banco de dados (Spring Data JPA)
├── model/        → Entidades do domínio (Transaction, Category, User)
├── dto/          → Objetos de entrada e saída da API
└── exception/    → Exceções personalizadas e tratamento global de erros
```

**Fluxo de uma requisição:**

```
Cliente → Controller → Service → Repository → MySQL
```

**Decisões de projeto:**

- **DTOs de entrada e de saída:** as entidades nunca saem da camada de serviço. Assim, a API controla exatamente o que recebe e o que devolve (por exemplo, a senha do usuário nunca aparece nas respostas).
- **Validação em camadas:** os DTOs validam os dados que chegam pela API (com mensagens claras para o cliente), e as entidades validam as próprias regras no construtor, para que nenhum objeto inválido exista, venha de onde vier.
- **Relatórios calculados no banco:** os totais por categoria usam uma consulta JPQL com `SUM`, `GROUP BY` e `LEFT JOIN`, para que o banco devolva apenas o resultado agregado.
- **Mensagem genérica no login:** e-mail inexistente e senha incorreta retornam a mesma mensagem, para não revelar quais e-mails estão cadastrados.

---

## 📌 Endpoints

### Status

| Método | Endpoint | Descrição | Respostas |
|---|---|---|---|
| `GET` | `/` | Verifica se a API está no ar | `200` |
| `GET` | `/about` | Informações sobre o projeto | `200` |

### Autenticação

| Método | Endpoint | Descrição | Respostas |
|---|---|---|---|
| `POST` | `/auth/register` | Cadastra um usuário | `201`, `400` |
| `POST` | `/auth/login` | Faz login e retorna um token JWT | `200`, `400`, `401` |

### Transações

| Método | Endpoint | Descrição | Respostas |
|---|---|---|---|
| `POST` | `/transactions` | Cadastra uma transação | `201`, `400`, `404` |
| `GET` | `/transactions` | Lista todas as transações | `200` |
| `GET` | `/transactions/{id}` | Busca uma transação pelo id | `200`, `404` |
| `PUT` | `/transactions/{id}` | Atualiza uma transação | `200`, `400`, `404` |
| `DELETE` | `/transactions/{id}` | Exclui uma transação | `204`, `404` |
| `GET` | `/transactions/balance` | Calcula o saldo geral | `200` |

### Categorias

| Método | Endpoint | Descrição | Respostas |
|---|---|---|---|
| `POST` | `/categories` | Cadastra uma categoria | `201`, `400` |
| `GET` | `/categories` | Lista as categorias | `200` |

### Relatórios

| Método | Endpoint | Descrição | Respostas |
|---|---|---|---|
| `GET` | `/reports/monthly?year=&month=` | Resumo do mês: receitas, despesas e saldo | `200`, `400` |
| `GET` | `/reports/expenses-by-category?year=&month=` | Despesas do mês agrupadas por categoria | `200`, `400` |
| `GET` | `/reports/income-by-category?year=&month=` | Receitas do mês agrupadas por categoria | `200`, `400` |

---

## 💡 Exemplos

### Cadastrar um usuário

```http
POST /auth/register
Content-Type: application/json
```

```json
{
  "name": "Mayara",
  "email": "mayara@email.com",
  "password": "senha12345"
}
```

**Resposta: `201 Created`** (a senha nunca é devolvida)

```json
{
  "id": 1,
  "name": "Mayara",
  "email": "mayara@email.com"
}
```

### Fazer login

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "email": "mayara@email.com",
  "password": "senha12345"
}
```

**Resposta: `200 OK`**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer"
}
```

### Cadastrar uma transação

```http
POST /transactions
Content-Type: application/json
```

```json
{
  "description": "Mercado",
  "amount": 250.00,
  "date": "2026-11-10",
  "type": "EXPENSE",
  "categoryId": 1
}
```

O campo `type` aceita `INCOME` (receita) ou `EXPENSE` (despesa). O campo `categoryId` é opcional.

**Resposta: `201 Created`**

```json
{
  "id": 1,
  "description": "Mercado",
  "amount": 250.00,
  "date": "2026-11-10",
  "type": "EXPENSE",
  "categoryId": 1,
  "categoryName": "Alimentação"
}
```

### Despesas por categoria

```http
GET /reports/expenses-by-category?year=2026&month=11
```

**Resposta: `200 OK`** (ordenado do maior para o menor total)

```json
[
  { "category": "Moradia", "total": 1500.00 },
  { "category": "Alimentação", "total": 370.00 },
  { "category": "Sem categoria", "total": 250.00 }
]
```

### Respostas de erro

Erros seguem um formato padronizado:

```json
{
  "status": 404,
  "message": "Transação não encontrada: id 99",
  "timestamp": "2026-11-16T01:30:00"
}
```

Erros de validação listam todos os campos com problema de uma vez:

```json
{
  "status": 400,
  "message": "Dados inválidos.",
  "errors": {
    "description": "A descrição é obrigatória.",
    "amount": "O valor deve ser maior que zero."
  },
  "timestamp": "2026-11-16T01:30:00"
}
```

| Status | Quando acontece |
|---|---|
| `400 Bad Request` | Dados inválidos, JSON malformado ou parâmetro obrigatório ausente |
| `401 Unauthorized` | E-mail ou senha inválidos no login |
| `404 Not Found` | Transação ou categoria não encontrada |

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

> ⚠️ As credenciais do banco e a chave do JWT no `application.properties` são apenas para desenvolvimento local. Em produção, elas devem vir de variáveis de ambiente.

---

## 🗺️ Roadmap

- [x] CRUD de transações
- [x] Cálculo de saldo
- [x] Tratamento global de erros
- [x] Persistência com MySQL + Hibernate
- [x] Banco de dados com Docker Compose
- [x] Validações com Bean Validation
- [x] Categorias de transações
- [x] Relatórios mensais (resumo do mês e totais por categoria)
- [x] Cadastro de usuários com senha criptografada (BCrypt)
- [x] Login com geração de token JWT
- [ ] Proteção dos endpoints com o token JWT
- [ ] Dados separados por usuário (cada um vê apenas as próprias transações e categorias)
- [ ] Testes automatizados (JUnit, Mockito e Testcontainers)
- [ ] Documentação da API com Swagger (SpringDoc)
- [ ] Configurações sensíveis via variáveis de ambiente

---

## 👩‍💻 Autora

**Mayara Vieira**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/mayara-vieira-da-silva-471920226/)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/maayvs)

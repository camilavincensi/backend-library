# 📚 Library API

API REST desenvolvida com **Spring Boot** para gerenciamento de biblioteca, permitindo autenticação JWT, gerenciamento de autores e livros, com operações completas de CRUD.

---

## 📚 Integrantes
Camila Vincensi

---
## 🚀 Tecnologias utilizadas

- Java 21+
- Spring Boot 3+
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- PostgreSQL
- Lombok
- Maven

---

## 📂 Estrutura do projeto

```bash
src/main/java/com/backend/library
│
├── controller     # Endpoints REST
├── service        # Regras de negócio
├── repository     # Acesso ao banco
├── entity         # Entidades JPA
├── dto            # Objetos de transferência
├── exception      # Exceções customizadas
├── security       # Configurações de segurança
├── filter         # Filtro JWT
└── config         # Configurações gerais
```

---

## 🔐 Autenticação

A API utiliza autenticação baseada em **JWT**.

### Login

**Endpoint**

```http
POST /auth/login
```

**Body**

```json
{
  "username": "admin",
  "password": "123456"
}
```

**Resposta**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Use o token no header:

```http
Authorization: Bearer {token}
```

---

# 👤 Endpoints de Autor

## Criar autor

```http
POST /authors
```

Body:

```json
{
  "name": "Machado de Assis"
}
```

---

## Listar autores

```http
GET /authors
```

Filtro opcional:

```http
GET /authors?name=machado
```

---

## Buscar autor por ID

```http
GET /authors/{id}
```

---

## Atualizar autor

```http
PUT /authors/{id}
```

Body:

```json
{
  "name": "Machado de Assis Atualizado"
}
```

---

## Deletar autor

```http
DELETE /authors/{id}
```

> Não é permitido deletar autores com livros vinculados.

---

# 📖 Endpoints de Livro

## Criar livro

```http
POST /books
```

Body:

```json
{
  "title": "Dom Casmurro",
  "authorId": 1
}
```

---

## Listar livros

```http
GET /books
```

Filtro opcional:

```http
GET /books?title=dom
```

---

## Buscar livro por ID

```http
GET /books/{id}
```

---

## Atualizar livro

```http
PUT /books/{id}
```

---

## Deletar livro

```http
DELETE /books/{id}
```

---

# ⚠️ Tratamento de erros

A API possui tratamento padronizado de exceções:

### 404 - Não encontrado

```json
{
  "message": "Author not found"
}
```

### 400 - Requisição inválida

```json
{
  "message": "Author name cannot be empty"
}
```

### 409 - Conflito

```json
{
  "message": "Cannot delete author because it has associated books"
}
```

---

# 🛠️ Como rodar o projeto

## 1. Clone o repositório

```bash
[git clone https://github.com/camilavincensi/backend-library]
```

---

## 2. Configure o banco

No `application.properties`:

```properties
spring.application.name=demo
spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
spring.datasource.username=postgres
spring.datasource.password=1234

spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update


```

---

## 3. Execute

```bash
./mvnw spring-boot:run
```

ou

```bash
mvn spring-boot:run
```

---

## 4. Acesse

```http
http://localhost:8080
```

---

# 📌 Funcionalidades

- Cadastro de autores
- Cadastro de livros
- Busca por nome/título
- Atualização de registros
- Exclusão segura
- JWT Authentication
- Tratamento global de exceções
- Logs detalhados
- Validações de negócio

---

# 👨‍💻 Autor

Desenvolvido por **Camila Vincensi**

Projeto acadêmico e de estudo com foco em:

- Arquitetura REST
- Spring Security
- JWT
- Boas práticas backend
- Clean Code
- Tratamento de exceções
- Logs estruturados

---

# CyberGuard — API

API REST para monitoramento e detecção de eventos suspeitos de segurança, desenvolvida com Java e Spring Boot.

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-brightgreen?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-green?logo=springsecurity&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Supabase-blue?logo=postgresql&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-brightgreen?logo=swagger&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Render-blue?logo=docker&logoColor=white)

**Frontend:** [cyberguard-frontend](COLOCAR_LINK_DO_REPOSITORIO_FRONTEND)

**API em produção:** [cyberguard-backend-hqaj.onrender.com](https://cyberguard-backend-hqaj.onrender.com)

**Documentação:** [Swagger UI](https://cyberguard-backend-hqaj.onrender.com/swagger-ui/index.html)

---

O **CyberGuard** é uma plataforma de monitoramento de segurança capaz de registrar eventos de autenticação, analisar comportamentos suspeitos e gerar alertas automaticamente.

A aplicação identifica padrões relacionados a ataques de **Brute Force**, **Password Spraying** e possíveis **comprometimentos de conta**, permitindo acompanhar e gerenciar os alertas através de uma interface web.

O projeto utiliza eventos simulados para demonstrar o funcionamento das regras de detecção e foi desenvolvido com foco na aplicação prática de conceitos de **desenvolvimento backend, segurança, autenticação, APIs REST e arquitetura full-stack**.

---

## Funcionalidades

- Cadastro de usuários
- Login utilizando email e senha
- Autenticação stateless utilizando JWT
- Criptografia de senhas com BCrypt
- Proteção de endpoints com Spring Security
- Validação dos dados recebidos pela API
- Registro e consulta de eventos de segurança
- Detecção automática de ataques de Brute Force
- Detecção automática de Password Spraying
- Detecção de possível comprometimento de conta
- Geração automática de alertas de segurança
- Classificação dos alertas por severidade
- Gerenciamento do status dos alertas
- Simulação de cenários de ataque para demonstração
- Ordenação de eventos e alertas por data
- Padronização dos timestamps utilizando UTC
- Tratamento centralizado de erros de validação
- Documentação interativa com Swagger / OpenAPI
- Persistência de dados com PostgreSQL
- Configuração de CORS para integração com o frontend
- Deploy containerizado com Docker no Render

---

## Tecnologias

| Backend | Segurança / Infraestrutura |
| --- | --- |
| Java 25 | Spring Security |
| Spring Boot 4.1 | JWT |
| Spring Web MVC | BCrypt |
| Spring Data JPA | PostgreSQL |
| Hibernate | Supabase |
| Maven | Docker |
| Lombok | Render |
| Bean Validation | Swagger / OpenAPI |

---

## Arquitetura

O projeto é organizado em camadas, cada uma com responsabilidades bem definidas:

```text
src/main/java/com/pedroaugusto/cyberguard_app
├── configuration
├── controller
├── dto
├── exception
├── model
├── repository
├── security
└── services
```

| Camada | Responsabilidade |
| --- | --- |
| `controller` | Exposição dos endpoints REST |
| `services` | Regras de negócio, análise dos eventos e geração de alertas |
| `repository` | Persistência e consultas utilizando Spring Data JPA |
| `model` | Entidades e enums do domínio |
| `dto` | Objetos utilizados na entrada e saída de dados |
| `security` | Geração, validação e filtragem dos tokens JWT |
| `configuration` | Configurações do Spring Security, CORS e OpenAPI |
| `exception` | Tratamento centralizado de erros da aplicação |

### Arquitetura em produção

```text
Usuário
   │
   ▼
Frontend — React / Vercel
   │
   │ HTTPS / REST / JWT
   ▼
Backend — Spring Boot / Render
   │
   │ JPA / Hibernate
   ▼
PostgreSQL — Supabase
```

A autenticação é **stateless**. Após realizar o login, o cliente recebe um token JWT que deve acompanhar as requisições para endpoints protegidos.

---

## Banco de Dados

O CyberGuard utiliza **PostgreSQL** para persistência dos dados.

Em desenvolvimento, a aplicação pode utilizar uma instância PostgreSQL configurada pelo desenvolvedor. Em produção, o banco de dados PostgreSQL está hospedado no **Supabase**.

As principais entidades armazenadas são:

- `User`
- `SecurityEvent`
- `Alert`

As configurações sensíveis são carregadas através de variáveis de ambiente:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
```

As variáveis utilizadas pelo projeto são:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

> Credenciais reais, senhas do banco e o segredo utilizado para assinar os tokens JWT não devem ser armazenados no repositório.

---

## Autenticação e Segurança

O CyberGuard utiliza **Spring Security** com autenticação baseada em **JWT**.

### Cadastro

Para realizar o cadastro, o usuário informa:

- username
- email
- password

O email é utilizado posteriormente para autenticação.

As senhas são processadas utilizando **BCrypt** antes de serem armazenadas no banco de dados. O hash da senha não é retornado nas respostas da API.

### Login

O login é realizado através de email e senha.

Após uma autenticação válida, a API gera um token JWT:

```json
{
  "token": "jwt_token"
}
```

O token deve ser enviado nas requisições protegidas através do cabeçalho:

```text
Authorization: Bearer <token>
```

O backend utiliza um filtro JWT para validar o token antes de permitir o acesso aos endpoints protegidos.

---

## Detecções de Segurança

O CyberGuard possui um mecanismo de análise que processa eventos de autenticação e procura padrões que possam representar comportamentos suspeitos.

### Brute Force

Um ataque de **Brute Force** é identificado quando ocorrem pelo menos **5 tentativas de login malsucedidas** para o mesmo usuário e endereço IP dentro de uma janela de **2 minutos**.

Quando o padrão é identificado, o sistema gera automaticamente um alerta do tipo:

```text
BRUTE_FORCE
```

### Password Spraying

O **Password Spraying** ocorre quando um mesmo endereço IP realiza tentativas de autenticação contra diferentes usuários.

O CyberGuard analisa as falhas de autenticação realizadas pelo mesmo IP e gera um alerta quando identifica tentativas contra pelo menos **5 usuários diferentes** dentro de uma janela de **3 minutos**.

O alerta gerado é do tipo:

```text
PASSWORD_SPRAYING
```

### Possible Account Compromise

O sistema também procura situações nas quais diversas tentativas de login malsucedidas são seguidas por uma autenticação bem-sucedida.

Quando ocorre um `LOGIN_SUCCESS` após pelo menos **5 falhas de autenticação** para o mesmo usuário e endereço IP dentro de uma janela de **5 minutos**, o sistema gera:

```text
POSSIBLE_ACCOUNT_COMPROMISE
```

Esse comportamento pode representar uma situação na qual as credenciais foram descobertas após sucessivas tentativas.

---

## Eventos de Segurança

Os eventos representam atividades de autenticação registradas e analisadas pelo CyberGuard.

Atualmente são utilizados:

| Evento | Descrição |
| --- | --- |
| `LOGIN_FAILED` | Tentativa de autenticação malsucedida |
| `LOGIN_SUCCESS` | Autenticação realizada com sucesso |

Cada evento pode armazenar:

- identificador
- tipo do evento
- username
- endereço IP de origem
- dispositivo
- timestamp

Exemplo:

```json
{
  "id": 88,
  "eventType": "LOGIN_FAILED",
  "username": "admin",
  "sourceIp": "192.168.1.100",
  "deviceName": "SIMULATOR-PC",
  "timestamp": "2026-09-03T23:12:50.019370Z"
}
```

Os timestamps são representados utilizando `Instant` e armazenados em **UTC**, garantindo consistência independentemente do ambiente onde o backend está sendo executado.

O frontend pode converter o horário para o fuso local do usuário.

---

## Alertas

Os alertas são gerados automaticamente quando alguma das regras de detecção identifica um comportamento suspeito.

Cada alerta pode armazenar:

- identificador
- tipo
- severidade
- status
- username
- endereço IP de origem
- data de criação

### Tipos de alerta

```text
BRUTE_FORCE
PASSWORD_SPRAYING
POSSIBLE_ACCOUNT_COMPROMISE
```

Os alertas são retornados pela API do mais recente para o mais antigo.

---

## Validação

A API utiliza **Jakarta Bean Validation** para validar os dados recebidos.

Entre as validações utilizadas estão:

- `@Valid`
- `@NotBlank`
- `@NotNull`
- `@Email`

Dados inválidos são rejeitados antes de chegar às regras de negócio.

Os erros de validação são tratados de forma centralizada pela aplicação e retornam respostas HTTP apropriadas, como:

```text
400 Bad Request
```

Isso evita o retorno de grandes stack traces ou informações internas da aplicação para o cliente.

---

## Acesso para Teste

Um usuário de teste está cadastrado no banco de dados:

| Campo | Valor |
| --- | --- |
| Email | `pedro@email.com` |
| Senha | `123456` |

### Testando pelo Swagger

Para testar as rotas protegidas:

1. Acesse a documentação Swagger.
2. Abra o endpoint `POST /api/auth/login`.
3. Clique em **Try it out**.
4. Faça login utilizando a conta de teste.
5. Execute a requisição.
6. Copie o token JWT retornado.
7. Clique em **Authorize** no topo da página.
8. Informe o token no campo `bearerAuth`.
9. Execute uma das rotas protegidas.

Exemplo:

```json
{
  "email": "pedro@email.com",
  "password": "123456"
}
```

Como o Swagger está configurado com autenticação HTTP Bearer, basta inserir o token no campo **Authorize**. O Swagger adiciona o esquema `Bearer` à requisição.

> Essa conta deve ser utilizada apenas para demonstração e testes da aplicação.

---

## Documentação da API

A API possui documentação interativa utilizando **Swagger / OpenAPI**.

### Produção

```text
https://cyberguard-backend-hqaj.onrender.com/swagger-ui/index.html
```

A documentação permite:

- visualizar todos os endpoints disponíveis
- visualizar os modelos utilizados pela API
- enviar requisições diretamente pelo navegador
- realizar login
- configurar autenticação JWT
- testar endpoints protegidos

A especificação OpenAPI também pode ser acessada através de:

```text
https://cyberguard-backend-hqaj.onrender.com/v3/api-docs
```

---

## Endpoints

### Autenticação

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `POST` | `/api/auth/register` | Cadastra um novo usuário |
| `POST` | `/api/auth/login` | Realiza login e retorna um token JWT |

As rotas de autenticação são públicas.

### Eventos

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/api/events` | Lista os eventos de segurança |
| `POST` | `/api/events` | Registra um novo evento de segurança |

### Alertas

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/api/alerts` | Lista os alertas de segurança |
| `PATCH` | `/api/alerts/{id}/status` | Atualiza o status de um alerta |

### Simulador

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `POST` | `/api/simulator/brute-force` | Simula um ataque de Brute Force |
| `POST` | `/api/simulator/password-spraying` | Simula um ataque de Password Spraying |
| `POST` | `/api/simulator/account-compromise` | Simula um possível comprometimento de conta |

Com exceção das rotas de autenticação, os endpoints protegidos exigem um token JWT válido.

---

## Exemplos de Requisição

### Cadastro

```json
{
  "username": "pedro",
  "email": "pedro@email.com",
  "password": "123456"
}
```

### Login

```json
{
  "email": "pedro@email.com",
  "password": "123456"
}
```

### Registrar Evento

```json
{
  "eventType": "LOGIN_FAILED",
  "username": "admin",
  "sourceIp": "192.168.1.100",
  "deviceName": "SIMULATOR-PC"
}
```

### Atualizar Status de um Alerta

```json
{
  "status": "RESOLVED"
}
```

---

## Como Rodar Localmente

### Pré-requisitos

- Java 25
- Maven ou Maven Wrapper
- PostgreSQL

### Clone o repositório

```bash
git clone https://github.com/pedrogda/cyberguard-backend.git
```

### Entre na pasta do projeto

```bash
cd cyberguard-backend
```

### Configure as variáveis de ambiente

Antes de iniciar a aplicação, configure:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

Por exemplo, o `application.properties` utiliza essas variáveis desta forma:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
```

### Execute com Maven Wrapper

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

A aplicação estará disponível em:

```text
http://localhost:3000
```

O Swagger estará disponível em:

```text
http://localhost:3000/swagger-ui/index.html
```

---

## Docker

O projeto inclui um `Dockerfile` utilizado para realizar o deploy da aplicação no Render.

O processo utiliza **multi-stage build**:

1. Uma imagem Java JDK é utilizada para compilar o projeto.
2. O Maven Wrapper gera o arquivo `.jar` da aplicação.
3. Uma segunda imagem Java é utilizada para execução.
4. O `.jar` gerado é copiado para o container final.
5. O Spring Boot é iniciado dentro do container.

Essa estratégia separa o ambiente de build do ambiente utilizado para executar a aplicação em produção.

---

## Deploy

A arquitetura de produção utiliza serviços separados para cada parte da aplicação:

- **Frontend:** Vercel
- **Backend:** Render
- **Banco de dados:** PostgreSQL — Supabase
- **Documentação:** Swagger / OpenAPI
- **Containerização:** Docker

### Backend

https://cyberguard-backend-hqaj.onrender.com

### Swagger

https://cyberguard-backend-hqaj.onrender.com/swagger-ui/index.html

### Arquitetura de Deploy

```text
Frontend
React + Vite
Vercel
    │
    │ HTTPS
    ▼
Backend
Spring Boot
Render
    │
    │ JDBC
    ▼
PostgreSQL
Supabase
```

> O backend utiliza o plano gratuito do Render. Após períodos de inatividade, o serviço pode entrar em repouso. Por isso, a primeira requisição pode levar alguns segundos enquanto a instância é inicializada.

---

## Objetivo do Projeto

O CyberGuard foi desenvolvido como projeto de portfólio com o objetivo de aplicar conceitos relacionados a:

- desenvolvimento backend com Java
- Spring Boot
- APIs REST
- Spring Security
- autenticação JWT
- BCrypt
- persistência com JPA / Hibernate
- PostgreSQL
- modelagem de dados
- validação de requisições
- tratamento de exceções
- regras de negócio
- análise de eventos
- conceitos de segurança da informação
- documentação de APIs
- arquitetura full-stack
- Docker
- deploy em ambiente cloud

O projeto utiliza **eventos simulados** para demonstrar as regras de detecção e geração de alertas.

O CyberGuard não realiza monitoramento real de dispositivos ou redes e não substitui soluções profissionais de **SIEM, SOC ou EDR**.

---

## Repositório

**Backend:**  
https://github.com/pedrogda/cyberguard-backend

---

## Autor

Desenvolvido por **Pedro Augusto Gomes de Araújo**.

GitHub: https://github.com/pedrogda

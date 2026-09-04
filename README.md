# CyberGuard — API

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue)
![Maven](https://img.shields.io/badge/Maven-red)
![JWT](https://img.shields.io/badge/Auth-JWT-purple)
![Docker](https://img.shields.io/badge/Docker-containerized-blue)

API REST do **CyberGuard**, uma aplicação de monitoramento de segurança desenvolvida para simular eventos de autenticação, aplicar regras de detecção e gerar alertas de possíveis ameaças.

O projeto permite simular cenários como **Brute Force**, **Password Spraying** e **Possible Account Compromise**, utilizando uma arquitetura backend com autenticação JWT, persistência em PostgreSQL e regras de detecção implementadas no Spring Boot.

🔗 **API em produção:** https://cyberguard-backend-hqaj.onrender.com

> Interface web (React): hospedada na Vercel.

---

## Funcionalidades

- Cadastro e autenticação de usuários por **email e senha**
- Autenticação stateless utilizando **JWT**
- Senhas armazenadas de forma segura utilizando **BCrypt**
- Proteção de endpoints com **Spring Security**
- Registro de eventos de segurança
- Simulação de eventos de autenticação
- Detecção automática de comportamentos suspeitos
- Geração de alertas de segurança
- Atualização do status dos alertas
- Ordenação de eventos e alertas por data de ocorrência
- Timestamps padronizados em **UTC utilizando `Instant`**
- Validação de dados de entrada com **Bean Validation**
- Tratamento centralizado de erros de validação
- Persistência dos dados em **PostgreSQL**
- Configuração de **CORS** para integração com o frontend
- Deploy containerizado utilizando **Docker**

---

## Regras de Detecção

O CyberGuard possui regras de detecção que analisam eventos de autenticação simulados.

### Brute Force

Um alerta de **Brute Force** é gerado quando são identificadas pelo menos **5 tentativas de login malsucedidas** para o mesmo usuário e endereço IP dentro de uma janela de **2 minutos**.

### Password Spraying

Um alerta de **Password Spraying** é gerado quando um mesmo endereço IP realiza tentativas de login malsucedidas para pelo menos **5 usuários diferentes** dentro de uma janela de **3 minutos**.

### Possible Account Compromise

Um alerta de **Possible Account Compromise** é gerado quando ocorre um login bem-sucedido após pelo menos **5 tentativas de login malsucedidas** para o mesmo usuário e endereço IP dentro de uma janela de **5 minutos**.

---

## Tecnologias

- **Java 25**
- **Spring Boot 4.1**
- **Spring Security**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **JWT**
- **BCrypt**
- **Bean Validation**
- **Maven**
- **Docker**
- **Supabase** para hospedagem do PostgreSQL
- **Render** para hospedagem da API

---

## Arquitetura

O projeto segue uma organização em camadas:

- `controller` — exposição dos endpoints REST
- `services` — regras de negócio e lógica de detecção
- `repository` — acesso aos dados utilizando Spring Data JPA
- `model` — entidades e enums da aplicação
- `dto` — objetos utilizados na entrada e saída de dados
- `security` — geração, validação e filtragem de tokens JWT
- `configuration` — configuração do Spring Security, autenticação e CORS
- `exception` — tratamento centralizado de exceções e erros de validação

A arquitetura da aplicação em produção é:

```text
React / Vercel
      │
      │ HTTPS / REST
      ▼
Spring Boot / Render
      │
      │ JPA / Hibernate
      ▼
PostgreSQL / Supabase
```

A autenticação é **stateless**. Após o login, o cliente recebe um token JWT e deve enviá-lo nas requisições protegidas.

```text
Authorization: Bearer <token>
```

---

## Autenticação

### Cadastro

O cadastro de usuários exige:

- `username`
- `email`
- `password`

O email e o username são únicos.

As senhas são processadas utilizando **BCrypt** antes de serem armazenadas no banco de dados.

O hash da senha não é retornado pela API após o cadastro.

### Login

O login é realizado utilizando:

```json
{
  "email": "usuario@email.com",
  "password": "senha"
}
```

Após uma autenticação válida, a API retorna um token JWT:

```json
{
  "token": "<jwt-token>"
}
```

Esse token deve ser utilizado para acessar as rotas protegidas.

---

## Eventos de Segurança

Os eventos representam atividades de autenticação analisadas pelo CyberGuard.

Cada evento pode armazenar informações como:

- tipo do evento
- username
- endereço IP de origem
- dispositivo
- timestamp

Entre os tipos de evento utilizados estão:

```text
LOGIN_FAILED
LOGIN_SUCCESS
```

Os timestamps são armazenados utilizando `Instant`, mantendo os dados em UTC e permitindo que o frontend faça a conversão para o horário local do usuário.

---

## Alertas

Quando uma regra de detecção é satisfeita, o sistema pode gerar automaticamente um alerta.

Os alertas possuem informações como:

- tipo
- severidade
- status
- username
- endereço IP
- data de criação

Entre os tipos de alerta estão:

```text
BRUTE_FORCE
PASSWORD_SPRAYING
POSSIBLE_ACCOUNT_COMPROMISE
```

Os alertas podem ter seus status atualizados durante a análise.

---

## Validação

Os dados recebidos pela API são validados utilizando **Jakarta Bean Validation**.

Entre as validações utilizadas estão:

- `@Valid`
- `@NotBlank`
- `@NotNull`
- `@Email`

Por exemplo, um cadastro com email inválido ou campos obrigatórios vazios é rejeitado antes de chegar à regra de negócio.

Os erros de validação são tratados centralmente e retornados com **HTTP 400 Bad Request** em um formato simplificado.

Exemplo:

```json
{
  "status": 400,
  "errors": {
    "email": "Invalid email",
    "password": "Password is required"
  }
}
```

---

## Como executar localmente

### Pré-requisitos

- Java 25
- Maven ou Maven Wrapper
- PostgreSQL

### Passos

1. Clone o repositório:

```bash
git clone https://github.com/pedrogda/cyberguard-backend.git
cd cyberguard-backend
```

2. Configure as seguintes variáveis de ambiente:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

Exemplo de configuração utilizada pelo `application.properties`:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
```

> Nunca armazene senhas do banco ou o `JWT_SECRET` diretamente no repositório.

3. Execute a aplicação:

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

4. Por padrão, a API estará disponível em:

```text
http://localhost:3000
```

---

## Principais Endpoints

### Autenticação

| Método | Rota | Descrição |
| --- | --- | --- |
| `POST` | `/api/auth/register` | Cadastro de usuário |
| `POST` | `/api/auth/login` | Login e geração do token JWT |

### Eventos

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/api/events` | Lista os eventos de segurança |
| `POST` | `/api/events` | Registra um evento de segurança |

### Alertas

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/api/alerts` | Lista os alertas |
| `PATCH` | `/api/alerts/{id}/status` | Atualiza o status de um alerta |

### Simulação

O backend também possui endpoints responsáveis por gerar cenários simulados utilizados para testar as regras de detecção, incluindo:

- Brute Force
- Password Spraying
- Possible Account Compromise

> Com exceção das rotas de autenticação, os endpoints protegidos exigem um token JWT válido no cabeçalho `Authorization`.

---

## Docker

O backend possui um `Dockerfile` utilizado para realizar o deploy da aplicação no Render.

O processo utiliza **multi-stage build**:

1. Uma imagem JDK compila o projeto com Maven.
2. O arquivo `.jar` gerado é copiado para uma segunda imagem.
3. A aplicação Spring Boot é executada no container final.

Isso permite que o ambiente utilizado em produção seja reproduzível e independente da máquina utilizada no desenvolvimento.

---

## Deploy

O projeto está hospedado utilizando serviços em nuvem:

- **Frontend:** Vercel
- **Backend:** [Render](https://cyberguard-backend-hqaj.onrender.com)
- **Banco de dados:** PostgreSQL — Supabase

### Fluxo em produção

```text
Usuário
   │
   ▼
Frontend — Vercel
   │
   │ REST API / JWT
   ▼
Backend — Render
   │
   ▼
PostgreSQL — Supabase
```

> O backend utiliza o plano gratuito do Render. Após períodos de inatividade, o serviço pode ser suspenso temporariamente e o primeiro acesso pode levar alguns segundos enquanto a instância é inicializada.

---

## Objetivo do Projeto

O CyberGuard foi desenvolvido como um projeto de portfólio com o objetivo de aplicar conceitos de:

- desenvolvimento backend com Spring Boot
- criação de APIs REST
- autenticação e autorização
- JWT
- Spring Security
- persistência com JPA/Hibernate
- PostgreSQL
- validação de dados
- tratamento de exceções
- regras de negócio
- detecção baseada em eventos
- arquitetura full-stack
- containerização com Docker
- deploy em ambiente cloud

O projeto utiliza **eventos simulados** para demonstrar a lógica de detecção e geração de alertas. Portanto, o CyberGuard não realiza monitoramento real de dispositivos ou redes e não substitui ferramentas profissionais de **SIEM, SOC ou EDR**.

---

## Autor

Desenvolvido por [Pedro Augusto Gomes de Araújo](https://github.com/pedrogda).

🔗 **GitHub:** https://github.com/pedrogda

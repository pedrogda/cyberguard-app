# CyberGuard — API

API REST para monitoramento e detecção de eventos suspeitos de segurança, desenvolvida com Java e Spring Boot.

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-brightgreen?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-green?logo=springsecurity&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Supabase-blue?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Render-blue?logo=docker&logoColor=white)

**Frontend:** [cyberguard-frontend](COLOCAR_LINK_DO_REPOSITORIO_FRONTEND)

**API:** https://cyberguard-backend-hqaj.onrender.com

**Swagger:** https://cyberguard-backend-hqaj.onrender.com/swagger-ui/index.html

---

O **CyberGuard** é uma plataforma de monitoramento de segurança capaz de registrar eventos de autenticação, analisar comportamentos suspeitos e gerar alertas automaticamente.

A aplicação identifica padrões relacionados a ataques de **Brute Force**, **Password Spraying** e possíveis **comprometimentos de conta**, permitindo acompanhar e gerenciar os alertas através de uma interface web.

---

## Funcionalidades

- Cadastro e autenticação de usuários
- Autenticação stateless utilizando JWT
- Criptografia de senhas com BCrypt
- Proteção de endpoints com Spring Security
- Registro e consulta de eventos de segurança
- Detecção automática de ataques de Brute Force
- Detecção automática de Password Spraying
- Detecção de possível comprometimento de conta
- Geração automática de alertas de segurança
- Classificação de alertas por severidade
- Gerenciamento do status dos alertas
- Simulação de cenários de ataque para demonstração
- Documentação interativa da API com Swagger / OpenAPI
- Persistência de dados com PostgreSQL
- Deploy containerizado com Docker no Render

---

## Detecções de Segurança

O CyberGuard analisa eventos de autenticação e procura padrões que possam representar comportamentos suspeitos.

### Brute Force

Detecta múltiplas tentativas de login malsucedidas realizadas contra o mesmo usuário a partir do mesmo endereço IP em um curto intervalo de tempo.

Ao atingir o limite definido pela aplicação, é criado um alerta:

`BRUTE_FORCE`

com severidade:

`HIGH`

### Password Spraying

Detecta tentativas de autenticação realizadas a partir do mesmo endereço IP contra diferentes usuários.

Quando o comportamento atinge o limite configurado, é criado um alerta:

`PASSWORD_SPRAYING`

com severidade:

`HIGH`

### Possible Account Compromise

Detecta situações em que diversas falhas de autenticação são seguidas por um login bem-sucedido para o mesmo usuário e endereço IP.

Esse comportamento pode indicar que as credenciais da conta foram descobertas após sucessivas tentativas.

É criado um alerta:

`POSSIBLE_ACCOUNT_COMPROMISE`

com severidade:

`CRITICAL`

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
| Swagger / OpenAPI | CORS |

---

## Arquitetura

O backend é organizado em camadas, separando as responsabilidades da aplicação:

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

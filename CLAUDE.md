# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Canvas Agricole** is a full-stack agricultural financing canvas management system built for BNA (Banque Nationale Agricole, Tunisia). It handles agricultural project credit evaluation, multi-type credit management, financial analysis, guarantee tracking, and bank engagement workflows.

- **Backend:** `backend-saeb/` — Spring Boot 3.2.2, Java 21, Oracle DB
- **Frontend:** `frontend-app1/` — Angular 17.1.1, TypeScript 5.3.2

---

## Development Commands

### Backend (Spring Boot)

```bash
# Run development server (port 8081)
cd backend-saeb
./mvnw spring-boot:run

# Build JAR
./mvnw clean package

# Build skipping tests
./mvnw clean package -DskipTests

# Run tests
./mvnw test

# Docker build
docker build -t backend-saeb .
```

API docs available at: `http://localhost:8081/swagger-ui.html`

### Frontend (Angular)

```bash
# Install dependencies
cd frontend-app1
npm install

# Run dev server (port 4200)
npm start        # or: ng serve

# Production build
npm run build

# Run unit tests (Karma/Jasmine)
npm test

# Build and watch
ng build --watch
```

---

## Architecture

### Backend Architecture

**Entry point:** `backend-saeb/src/main/java/net/kachout/saeb/Application.java`

**Layer structure:**
```
net.kachout.saeb/
├── web/             REST controllers (35+)
├── services/        Business logic interfaces and implementations
├── models/          JPA entities
├── repository/      Spring Data JPA repositories
├── dtos_/           Current DTO classes (use dtos_/ not dtos/)
├── config/          Security, datasource, cache, Swagger configs
└── resources/
    ├── sql/         Native SQL query files for complex reports
    └── db/migration/ Flyway migrations V1–V8
```

**Key configurations (`application.properties`):**
- Server port: `8081`
- Two Oracle datasources: primary (CANVA schema) and secondary (legacy banking data)
- JWT via Keycloak: issuer `http://localhost:8080/realms/canvaAgricole`
- Caffeine cache: 24h TTL, 500 max entries
- Max file upload: 10MB

**Authentication flow:** Spring Security OAuth2 resource server validates JWTs issued by Keycloak (or custom auth server). Custom `JwtAuthConverter` extracts roles from token claims.

**Multi-datasource:** The application integrates with legacy banking systems (REFCLI, CCI, SMILE) via a secondary datasource. Native SQL queries in `resources/sql/` target these legacy schemas. Flyway migrations V1–V3 create these legacy tables; V4–V6 insert their reference data.

**Main domain entities:**
- `CanevasAgricole` — central entity representing an agricultural project canvas
- `CreditAchatTerrain` / `CreditInvestissement` / `CreditCourtTerme` — three credit types
- `Site` — project geographic locations
- `Elevage` / `Materiel` / `Batiment` — agricultural assets
- `GarantiePropose` — guarantee proposals
- `EngagementBNA` / `EngagementBCT` — bank engagements
- `CanevasWorkflowLog` — workflow state history
- `CanevasStructureSnapshot` — versioned snapshots

### Frontend Architecture

**Entry point:** `frontend-app1/src/main.ts` → `AppModule` → `AppComponent`

**Key modules and locations:**
```
src/app/
├── _components/
│   ├── layout/          AppLayoutComponent, sidebar, topbar, footer, config
│   └── pages/
│       └── canvas.agricole/   All canvas feature components (40+)
├── _services/           35+ Angular services
├── _guards/             AuthGuard (Keycloak + custom)
├── _interceptors/       AuthInterceptor (JWT injection)
├── _models/             TypeScript interfaces/models
└── app-routing.module.ts
```

**Routing:** All canvas features are lazy-loaded under `/pages`. `AppLayoutComponent` wraps all protected routes. `/auth/callback` handles OAuth2 redirects.

**Authentication (dual-mode):** Controlled by `environment.authMode`:
- `'keycloak'` — uses `keycloak-angular` library with Keycloak adapter
- `'custom'` — uses `AuthService` calling `http://localhost:7070` with JWT stored in `SessionStorage` via `TokenStorageService`

`AuthInterceptor` injects the Bearer token on every outgoing HTTP request. Both modes use role-based route guards.

**State management:** `CanevasService` uses Angular Signals for reactive state. Most services use RxJS `Observable` streams. Some state is persisted via `DataBaseStorageService` (localStorage wrapper).

**Environment variables:**
- `apiURL`: `http://localhost:8081/` (backend base URL)
- `authServerUrl`: `http://localhost:7070` (custom auth)
- `appId`: `BNAHABIL`
- `keycloakRealm`: `canvaAgricole`, `keycloakClientId`: `angualr_client`

**UI stack:** PrimeNG 17 (primary components), Angular Material 17 (supplementary), Bootstrap 5 (layout), Leaflet (maps), jsPDF + html2canvas (PDF export).

---

## Database

- **DBMS:** Oracle (both datasources)
- **Schema:** `CANVA` (main), plus legacy schemas REFCLI, CCI, SMILE
- **Migrations:** Flyway, files in `backend-saeb/src/main/resources/db/migration/`
  - V1–V3: Legacy banking system tables
  - V4–V6: Legacy reference data
  - V7: Main CANVA schema creation
  - V8: Test/fake data

Required environment variables for backend:
```
ORACLE_HOST, ORACLE_PORT, ORACLE_USER, ORACLE_PASSWORD
JWT_SECRET, AUTH_SERVER_URL, CODE_APP
```

---

## External Dependencies

- **Keycloak** (optional, port 8080): `http://localhost:8080/realms/canvaAgricole` — provides JWT issuer and JWKS endpoint
- **Custom Auth Server** (optional, port 7070): Used when `authMode = 'custom'`
- **Oracle DB**: Required for backend startup; Flyway runs migrations automatically on first start
- **Spring Cloud Eureka** (optional): Backend registers as Eureka client for service discovery

# CLAUDE.md — Canva Agricole (BNA)

Documentation complète du projet pour Claude Code. Ce fichier sert de référence permanente pour toute assistance sur ce dépôt.

---

## 1. Vue d'ensemble

**Nom du projet :** Canva Agricole  
**Client :** Banque Nationale Agricole (BNA), Tunisie  
**Objectif :** Plateforme web de gestion du financement agricole — instruction de dossiers de crédit, analyse financière, suivi des engagements bancaires, gestion des garanties et circuit d'avis.  
**Version courante :** 05/05/2026  
**Git branch principale :** `dev`

---

## 2. Structure du projet (monorepo Maven + Angular)

```
canva_agricole/
├── authorization-server-be/    # Serveur SSO/OAuth2 (Spring Boot 3, port 7070)
├── backend-saeb/               # API métier principale (Spring Boot 3.2, port 8081)
├── bnaauthentication/          # Auth LDAP legacy BNA (Spring Boot 2, port 8082)
├── frontend-app1/              # SPA Angular 17 (port 4200 en dev)
├── uploads/                    # Stockage fichiers uploadés
├── sqlSchema                   # Schéma Oracle principal
├── V1_gpt__Initial_schema.sql  # Migration initiale générée
├── V1_kimik2__initial_schema.sql
├── SQLSITES.sql                # Table SITES
├── scriptDataBase/             # Scripts d'initialisation DB
└── pom.xml                     # POM parent Maven
```

---

## 3. Stack technique complète

### 3.1 Backend SAEB (`backend-saeb/`)

| Technologie | Version | Usage |
|-------------|---------|-------|
| Spring Boot | 3.2.2 | Framework principal |
| Java | 17 | Langage |
| Spring Data JPA / Hibernate | — | ORM, Oracle Dialect |
| Oracle JDBC (ojdbc11) | — | Pilote base de données |
| HikariCP | — | Pool de connexions (multi-datasource) |
| Spring Security OAuth2 Resource Server | — | Validation JWT |
| JJWT | 0.12.3 | Création/validation tokens JWT |
| Spring Cloud Eureka Client | — | Service discovery |
| Spring Cloud OpenFeign | — | Appels inter-services |
| SpringDoc OpenAPI | 2.5.0 | Documentation Swagger |
| Caffeine Cache | — | Cache applicatif |
| ModelMapper | 3.2.2 | Mapping Entity ↔ DTO |
| Lombok | — | Réduction boilerplate |
| Maven | 3 | Build |

### 3.2 Authorization Server (`authorization-server-be/`)

| Technologie | Version | Usage |
|-------------|---------|-------|
| Spring Boot | 3.2.0 | Framework |
| Java | 17 | Langage |
| Spring Security | — | Sécurité |
| JJWT | 0.12.3 | Tokens JWT |
| Oracle DB | — | Persistance sessions/codes |

### 3.3 BNA Auth Legacy (`bnaauthentication/`)

| Technologie | Version | Usage |
|-------------|---------|-------|
| Spring Boot | 2.0.5 | Framework (legacy) |
| Java | 1.8 | Langage (legacy) |
| Spring Security LDAP | — | Auth annuaire BNA |
| JJWT | 0.9.0 | JWT legacy |
| Oracle JDBC | — | Base de données |

### 3.4 Frontend Angular (`frontend-app1/`)

| Technologie | Version | Usage |
|-------------|---------|-------|
| Angular | 17.1.0 | Framework SPA |
| TypeScript | 5.3.2 | Langage |
| Angular CLI | 17.1.1 | Build tool |
| PrimeNG | 17.14.1 | Composants UI principaux |
| Angular Material | 17.3.10 | Composants Material Design |
| Bootstrap | 5.3.2 | CSS framework |
| PrimeFlex | 3.3.1 | Utilitaires CSS |
| Keycloak-Angular | 15.2.1 | Auth Keycloak |
| Keycloak-JS | 23.0.7 | SDK Keycloak |
| Leaflet | 1.9.4 | Cartes interactives |
| jsPDF | 3.0.1 | Export PDF |
| jspdf-autotable | 5.0.2 | Tables dans PDF |
| html2canvas | 1.4.1 | Capture écran → PDF |
| RxJS | 7.8.0 | Programmation réactive |

---

## 4. Ports et services

| Service | Port | Description |
|---------|------|-------------|
| Frontend Angular | 4200 | `ng serve` (dev) |
| Backend SAEB | 8081 | API REST principale |
| Authorization Server | 7070 | SSO / JWT / OAuth2 |
| Keycloak | 8080 | Realm `canvaAgricole` |
| BNA Auth LDAP | 8082 | Auth legacy LDAP |

---

## 5. Configuration principale

### 5.1 `backend-saeb/src/main/resources/application.properties`

- **Datasource primaire :** `jdbc:oracle:thin:@10.1.9.214:1561:SAEB.bna.tn` (user: `saeb`)
- **Datasource secondaire :** `jdbc:oracle:thin:@10.1.224.10:1541:bnaprod` (user: `saeb`)
- **JWT Issuer URI :** `http://localhost:8080/realms/canvaAgricole`
- **Upload :** répertoire `./uploads`, taille max `10MB`
- **Cache Caffeine :** `maximumSize=500, expireAfterWrite=24h`
- **Caches nommés :** `engagementBNA`, `engagementBCT`, `historiqueClasseBNA`, `historiqueClasseBCT`, `historiqueRetourClasseBct`, `montantTerme`

### 5.2 `authorization-server-be/src/main/resources/application.yml`

- **Port :** 7070
- **Access Token TTL :** 3 600 000 ms (1h)
- **Refresh Token TTL :** 604 800 000 ms (7 jours)
- **Session SSO TTL :** 28 800 000 ms (8h)
- **CORS autorisés :** `localhost:60959`, `4301`, `4302`, `4303`
- **Auth externe :** `http://localhost:8082/auth/signin/multiple`

### 5.3 `bnaauthentication/src/main/resources/application.properties`

- **Port :** 8082
- **LDAP BNA :** `ldap://10.1.6.1:389/`

---

## 6. Architecture des packages Java (backend-saeb)

```
net.kachout.saeb
├── Application.java                # Entry point (@SpringBootApplication)
├── config/
│   └── MapperConfig.java           # Configuration ModelMapper
├── datasource/
│   └── DataSourceConfig.java       # Config multi-datasource HikariCP
├── entities/                       # 43 entités JPA
├── dtos/                           # 80+ DTOs de transfert
├── dtos_/                          # DTOs secondaires (AffectationDto, etc.)
├── repository/                     # 30+ repositories Spring Data JPA
├── service/                        # 40+ services métier
│   ├── interfaces/                 # Interfaces (IGarantieService, etc.)
│   └── impl/                       # Implémentations
└── web/                            # 40+ contrôleurs REST
```

---

## 7. Entités JPA principales

| Entité | Table | Description |
|--------|-------|-------------|
| `Caneva` | `CANEVAS_AGRICOLE` | Canevas principal du projet agricole |
| `Site` | `SITES` | Localisation géographique |
| `CreditCourtTerme` | `CREDIT_COURT_TERME` | Crédits saisonniers/opérationnels |
| `CreditInvestissement` | `CREDIT_INVESTISSEMENT` | Crédits équipements/infrastructure |
| `CreditAchatTerrain` | `CREDIT_ACHAT_TERRAIN` | Financement foncier |
| `ItemInvestissement` | `ITEM_INVESTISSEMENT` | Lignes de crédit investissement |
| `ItemAchatTerrain` | `ITEM_ACHAT_TERRAIN` | Lignes d'achat terrain |
| `GarantiePropose` | — | Garanties proposées par client |
| `Avis` | — | Avis bancaire sur le dossier |
| `Affectation` | — | Affectation agent → canevas |
| `Associe` | — | Associés/partenaires du projet |
| `Elevage` | — | Données cheptel |
| `RendementVegital` | — | Rendements agricoles par spéculation |
| `RessourceHydrique` | — | Besoins et disponibilités en eau |
| `RessourceHydriqueInv` | — | Ressources hydriques pour investissement |
| `Materiel` | — | Équipements et machines |
| `Batiment` | — | Bâtiments et constructions |
| `CACourtTerme` | — | Compte d'approvisionnement court terme |
| `BilanFourragere` | — | Bilan fourrager (besoins vs dispo) |
| `SchemaCredit` | — | Schéma de financement crédit |
| `SchemaCreditInv` | — | Schéma investissement |
| `ConditionFinancement` | — | Conditions bancaires |
| `Vendeur` | `VENDEUR` | Informations vendeur foncier |
| `DocumentCanevaFile` | — | Pièces justificatives du canevas |
| `DocumentClientCanva` | — | Documents client |
| `GarantieProposeFichier` | — | Fichiers de garanties |
| `CanevasUser` | — | Association canevas ↔ utilisateur |
| `ClientCanvas` | — | Association client ↔ canevas |
| `ClientRequest` | — | Demandes clients |
| `SsoSession` | — | Sessions SSO (partagé avec auth-server) |
| `RefZone` | `REF_ZONE` | Référentiel zones |
| `RefRegion` | `REF_REGION` | Référentiel régions |
| `RefGouvernorat` | `REF_GOUVERNORAT` | Référentiel gouvernorats |
| `RefDelegation` | `REF_DELEGATION` | Référentiel délégations |
| `RefImada` | `REF_IMADA` | Référentiel imadas |

**Relations clés :**
- `Caneva` → `Sites` : Many-to-Many (table de jonction `CANEVA_SITE_PROJET`)
- `Caneva` → `CreditCourtTerme / CreditInvestissement / CreditAchatTerrain` : One-to-Many
- `Caneva` → `DocumentCanevaFile` : One-to-Many
- `Caneva` → `GarantiePropose` : One-to-Many
- Hiérarchie géographique : Zone → Région → Gouvernorat → Délégation → Imada

---

## 8. Contrôleurs REST (backend-saeb)

Tous sous `/api/` :

| Contrôleur | Mapping | Fonctions |
|------------|---------|-----------|
| `CanevaController` | `/api/canevas` | CRUD canevas, recherche par agent/personne |
| `ClientController` | `/api/clients` | Gestion clients |
| `CreditAchatTerrainController` | `/api/credits/terrain` | Crédits fonciers |
| `CreditInvestissementController` | `/api/credits/investissement` | Crédits investissement |
| `AvisController` | `/api/avis` | Avis bancaires |
| `GarantieController` | `/api/garanties` | Garanties |
| `EngagementsController` | `/api/engagements` | Engagements BNA/BCT |
| `AnalyseFinanciereController` | `/api/analyses` | Analyse financière |
| `SiteController` / `SitesController` | `/api/sites` | Gestion sites |
| `BaremeAgricoleController` | `/api/baremes` | Barèmes agricoles |
| `BaremeCourtTermeController` | `/api/baremes/court-terme` | Barèmes CT |
| `ElevageCheptelController` | `/api/elevage` | Cheptel/élevage |
| `MaterielController` | `/api/materiel` | Équipements |
| `ConditionBanqueController` | `/api/conditions` | Conditions bancaires |
| `DocumentClientCanvaController` | `/api/documents` | Upload/download fichiers |
| `AffectationController` | `/api/affectations` | Affectations agents |
| `RessourceHydriqueController` | `/api/ressources-hydriques` | Ressources eau |
| `SchemaCreditController` | `/api/schemas` | Schémas crédit |
| `RefController` | `/api/references` | Référentiels géographiques |
| `AgricoleRestController` | `/api/agricole` | Opérations agricoles |
| `ClientRequestController` | `/api/client-requests` | Demandes clients |
| `ClientCanvasController` | `/api/client-canvas` | Relations client-canevas |

**Documentation Swagger :** `http://localhost:8081/swagger-ui.html`

---

## 9. Services Angular (frontend-app1)

Dossier : `frontend-app1/src/app/_services/`

| Service | Fichier | Usage |
|---------|---------|-------|
| Canevas | `canevas.service.ts` | CRUD canevas agricoles |
| Client | `client.service.ts` | Gestion clients |
| Crédit sollicité | `credit-sollicite.service.ts` | Crédits demandés |
| Avis | `avis.service.ts` | Avis bancaires |
| Garantie | `garantie.service.ts` | Garanties |
| Engagement | `engagement.service.ts` | Engagements BNA/BCT |
| Affectation | `affectation.service.ts` | Affectation agents |
| Associé | `Associe.service.ts` | Partenaires/associés |
| API SAEB | `api-saeb.service.ts` | Service direct SAEB |
| Canevas AGR | `canevas.agr.service.ts` | Service agricole canvas |
| Analyse financière | `analyseFinanciere.service.ts` | Analyses |
| Bilan | `bilan.service.ts` | Bilans comptables |
| Condition banque | `conditionBanque.service.ts` | Conditions bancaires |
| Schéma crédit | `schema-credit.service.ts` | Schémas financement |
| Site | `site.service.ts` | Gestion sites |
| Spéculation | `speculation.service.ts` | Cultures/spéculations |
| Document | `document.client.canva.service.ts` | Gestion documents |
| Exploitation | `exploitation-projet.service.ts` | Données exploitation |
| Effort | `effort.service.ts` | Calcul efforts/coûts |
| Auth | `auth.service.ts` | Authentification (Keycloak + custom) |
| User | `user.service.ts` | Gestion utilisateurs |
| Token Expiration | `token-expiration.service.ts` | Suivi expiration token |
| Idle | `IdleService.service.ts` | Détection session idle |
| Loading | `loading.service.ts` | État de chargement global |
| PDF Export | `pdf-export.service.ts` | Export PDF |
| Storage | `storage.service.ts` | LocalStorage |
| DB Storage | `data-base-storage.service.ts` | IndexedDB |

---

## 10. Structure du module Angular Canevas Agricole

Dossier : `frontend-app1/src/app/_components/pages/canvas.agricole/`

**Pages principales (40+) :**
- `canevas/` — Liste des canevas
- `add-canevas/` — Création d'un canevas
- `fiche-signalitique/` et `fiche-signalitique-2/` — Fiches signaléti­ques client
- `exploitation/` — Données d'exploitation agricole
- `affectation/` — Affectation des agents
- `agent-canevas/` — Gestion agents
- `associates/` — Associés/partenaires
- `credit-sollicite/` — Crédits sollicités
- `credit-retenu[1-4]/` — Crédits retenus (4 variantes)
- `avis/` — Avis général
- `avis-achat-terrrain/` — Avis achat terrain
- `avis-credit-court-terme/` — Avis crédit CT
- `avis-credit-investissement/` — Avis investissement
- `avis-garantie/` — Avis garanties
- `garanties/` — Gestion garanties
- `garanties-proposees/` — Garanties proposées
- `engagement-bct-bna/` — Engagements BCT/BNA
- `engagements/` — Suivi engagements
- `materiel-projet/` — Équipements du projet
- `cheptel-projet/` — Cheptel/élevage
- `besoin-fourrager/` — Besoins fourragers
- `disponibilite-fourragere/` — Disponibilité fourragère
- `bilan-fourrager-inv/` — Bilan fourrager (investissement)
- `besoin-hydrique/` — Besoins hydriques
- `disponibilite-hydrique/` — Disponibilité en eau
- `bilan-hydrique/` — Bilan hydrique
- `condition-banque/` — Conditions bancaires
- `analyse-financier/bilan/` — Bilan comptable
- `analyse-financier/etat-tresorerie/` — État de trésorerie
- `analyse-financier/ratio-financier/` — Ratios financiers
- `analyse-financier/activite-rentabilite/` — Activité-rentabilité
- `document-upload/` — Upload documents
- `effort-table/` — Table des efforts/coûts
- `historical-bct-class/` — Historique classification BCT
- `map-picker/` — Sélecteur géographique (Leaflet)
- `client-and-canevas/` — Relations client-canevas
- `list/` — Vue liste

---

## 11. Authentification (dual mode)

### Mode Keycloak (`AUTH_MODE = 'keycloak'`)
- Serveur : `http://localhost:8080`
- Realm : `canvaAgricole`
- Client ID : `angualr_client`
- Library : `keycloak-angular 15.2.1`

### Mode Custom Auth
- Callback : `AuthCallbackComponent` (route `/auth/callback`)
- Token storage : `TokenStorageService`
- Intercepteur HTTP : `_helpers/auth.interceptor.ts` (injection auto JWT)
- Gestion expiration : `token-expiration.service.ts`
- Gestion idle : `IdleService.service.ts`

### Authorization Server (endpoints)
- `POST /api/auth/login` — Connexion
- `POST /api/auth/refresh` — Renouvellement token
- `POST /api/auth/exchange-code` — Échange code OAuth2
- `POST /api/auth/logout` — Déconnexion SSO
- `GET  /api/auth/me` — Profil courant
- `GET  /api/portal` — Applications accessibles
- `GET  /api/sessions` — Sessions actives

---

## 12. Base de données Oracle

### Connexions
- **Primaire :** `jdbc:oracle:thin:@10.1.9.214:1561:SAEB.bna.tn` — user: `saeb`
- **Secondaire :** `jdbc:oracle:thin:@10.1.224.10:1541:bnaprod` — user: `saeb`
- **Schémas utilisés :** `SAEB`, `SMILE`, `HABIL`

### Tables principales
```sql
CANEVAS_AGRICOLE         -- Table centrale des canevas
SITES                    -- Localisations géographiques
CANEVA_SITE_PROJET       -- Jonction canevas ↔ sites (M2M)
CREDIT_ACHAT_TERRAIN     -- Crédits fonciers
CREDIT_COURT_TERME       -- Crédits saisonniers
CREDIT_INVESTISSEMENT    -- Crédits équipements
ITEM_ACHAT_TERRAIN       -- Lignes crédits fonciers
ITEM_INVESTISSEMENT      -- Lignes crédits investissement
VENDEUR                  -- Vendeurs de terrain
REF_ZONE                 -- Référentiel zones
REF_REGION               -- Référentiel régions
REF_GOUVERNORAT          -- Référentiel gouvernorats
REF_DELEGATION           -- Référentiel délégations
REF_IMADA                -- Référentiel imadas
```

---

## 13. Commandes de développement

### Backend
```bash
# Lancer tous les backends séquentiellement
cd backend-saeb && mvn spring-boot:run
cd authorization-server-be && mvn spring-boot:run
cd bnaauthentication && mvn spring-boot:run

# Compiler sans lancer
mvn clean install -DskipTests

# Tests uniquement
mvn test
```

### Frontend
```bash
cd frontend-app1

# Installer les dépendances
npm install

# Démarrer en développement (port 4200)
ng serve

# Build production
ng build --configuration=production

# Lancer les tests
ng test

# Générer un composant
ng generate component _components/pages/canvas.agricole/mon-composant
```

---

## 14. Conventions de code

### Java (backend)
- **Entités :** annotées `@Entity`, `@Table`, Lombok (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`)
- **DTOs :** classes simples avec Lombok
- **Services :** interface dans `service/interfaces/`, implémentation dans `service/impl/` ou directement dans `service/`
- **Contrôleurs :** `@RestController`, `@RequestMapping("/api/...")`, `@CrossOrigin`
- **Sécurité :** toutes les routes protégées via OAuth2 Resource Server; JWT validé automatiquement

### Angular (frontend)
- **Formulaires :** Reactive Forms (`FormBuilder`, `FormGroup`)
- **HTTP :** services dédiés dans `_services/`, intercepteur auto-inject JWT
- **Routing :** lazy loading des modules (`loadChildren`)
- **Styles :** Bootstrap 5 + PrimeNG + styles custom dans les composants

---

## 15. Fichiers de configuration clés

| Fichier | Emplacement |
|---------|-------------|
| POM parent | `pom.xml` |
| POM backend SAEB | `backend-saeb/pom.xml` |
| Config SAEB | `backend-saeb/src/main/resources/application.properties` |
| POM auth server | `authorization-server-be/pom.xml` |
| Config auth server | `authorization-server-be/src/main/resources/application.yml` |
| POM BNA auth | `bnaauthentication/pom.xml` |
| Config BNA auth | `bnaauthentication/src/main/resources/application.properties` |
| package.json frontend | `frontend-app1/package.json` |
| Angular config | `frontend-app1/angular.json` |
| TypeScript config | `frontend-app1/tsconfig.json` |
| Routing principal | `frontend-app1/src/app/app-routing.module.ts` |
| Routing canevas | `frontend-app1/src/app/_components/pages/canvas.agricole/canvas.agricole-routing.module.ts` |
| Entry point Angular | `frontend-app1/src/main.ts` |
| App module | `frontend-app1/src/app/app.module.ts` |
| Schéma Oracle | `sqlSchema` |

---

## 16. Points d'attention importants

- **Multi-datasource :** Le backend SAEB utilise deux datasources Oracle simultanément (primaire et secondaire). La config est dans `DataSourceConfig.java`. Faire attention lors de modifications des requêtes natives.
- **Cache Caffeine :** Les données d'engagement BNA/BCT et l'historique de classification sont mis en cache 24h. En cas de problème de données obsolètes, vider le cache applicatif.
- **Authentication duale :** Le frontend supporte deux modes (Keycloak et custom). Le mode est défini dans les fichiers d'environnement Angular (`environment.ts`). Ne pas mélanger les deux flows.
- **LDAP BNA :** Le service `bnaauthentication` est une dépendance legacy. Il utilise Java 8 et Spring Boot 2. Ne pas upgrader sans tests approfondis.
- **Upload fichiers :** Le dossier `./uploads` doit être accessible en écriture par le processus Java. Max 10MB par fichier.
- **JWT :** Les tokens sont validés par le backend SAEB via l'issuer Keycloak (`http://localhost:8080/realms/canvaAgricole`). En mode custom auth, les tokens sont générés par l'authorization-server-be.
- **CORS :** L'authorization server autorise explicitement `localhost:60959`, `4301`, `4302`, `4303`. Si le frontend tourne sur un autre port, ajouter la config CORS.
- **Swagger :** Disponible à `http://localhost:8081/swagger-ui.html` en développement.

***

# Sistema Biblioteca - Documentazione Tecnica (Aggiornata)

Questo documento fornisce una panoramica tecnica completa dell’architettura backend e del nuovo sistema di design frontend per il progetto "Sistema Biblioteca".

***

## 1. Panoramica del Progetto
Il "Sistema Biblioteca" è una dashboard gestionale moderna sviluppata con **Spring Boot** e **Java 17+**. Il sistema offre un'interfaccia premium basata sui ruoli per la gestione di utenti, catalogo libri e noleggi.

### Evoluzione Recente:
Recentemente il progetto è stato sottoposto a un profondo processo di **refactoring e documentazione** globale:
- **CSS Architecture**: Passaggio da stili inline a un sistema di classi di utilità centralizzato in `style.css`.
- **Documentation Standard**: Ogni file (HTML, CSS, JS, Java) include ora una documentazione dettagliata per facilitare la manutenzione.
- **Micro-interazioni**: Implementazione di animazioni fluide e feedback visivi premium in tutta la dashboard.

***

## 2. Architettura Backend: Design a Livelli
Il progetto segue una **architettura a tre livelli** rigorosa:

1. **Controller Layer (`it.controller`)**: Gestisce il routing, la validazione dei parametri e il flusso delle viste.
2. **Service Layer (`it.service`)**: Contiene la logica di business, la validazione granulare e la trasformazione dei dati (DTO mapping).
3. **Repository Layer (`it.repository`)**: Gestisce la persistenza tramite **JdbcTemplate**, ottimizzando le performance con query SQL scritte a mano.
4. **Data Access Layers (`it.entity`, `it.dto`, `it.mapper`)**: Definiscono i modelli di dati e le logiche di conversione tramite RowMapper personalizzati.

***

## 3. Frontend - Design System & UI
L'interfaccia è costruita con **Thymeleaf**, **Vanilla CSS** e **JS**, seguendo una filosofia di design moderno e pulito.

### 3.1. Design System Centralizzato (`style.css`)
Tutte le componenti visive ereditano stili da un unico punto di verità ([style.css](file:///c:/Users/tubal_sqk4vbf/Desktop/Project/Library/sistema-biblioteca/src/main/resources/static/css/style.css)). 

**Utility Classes principali:**
- **Layout**: `.flex`, `.flex-column`, `.grid`, `.center`, `.justify-between`, `.gap-*`
- **Spacing**: `.m-*`, `.p-*` (basati su scala rem 0.25 - 3.0)
- **Tipografia**: `.text-xs` (0.75rem) fino a `.text-4xl` (2.25rem), `.fw-700` (Bold).
- **Stati Semantici**: `.icon-bg-success` (Verde), `.icon-box-accent` (Arancio), `.border-error` (Rosso).
- **Raggi**: `.radius-8`, `.radius-12`, `.radius-20`, `.radius-full`.

### 3.2. Frammenti e Componenti
L'UI è modularizzata in frammenti Thymeleaf riutilizzabili:
- **[Layout](file:///c:/Users/tubal_sqk4vbf/Desktop/Project/Library/sistema-biblioteca/src/main/resources/templates/fragments/layout.html)**: Master template che gestisce Sidebar, Header e Overlay.
- **[Navbar](file:///c:/Users/tubal_sqk4vbf/Desktop/Project/Library/sistema-biblioteca/src/main/resources/templates/fragments/navbar.html)**: Navigazione dinamica basata sul ruolo utente con supporto al **Theme Toggle** (Light/Dark).
- **[Catalogo](file:///c:/Users/tubal_sqk4vbf/Desktop/Project/Library/sistema-biblioteca/src/main/resources/templates/fragments/catalog.html)**: Visualizzazione a schede con animazioni di entrata staggered e badge di stato dinamici.
- **[Popup](file:///c:/Users/tubal_sqk4vbf/Desktop/Project/Library/sistema-biblioteca/src/main/resources/templates/fragments/popup.html)**: Sistema modale polivalente per Modifica e Successo.
- **[Searchbar](file:///c:/Users/tubal_sqk4vbf/Desktop/Project/Library/sistema-biblioteca/src/main/resources/templates/fragments/searchbar.html)**: Overlay di ricerca contestuale configurabile per sezioni (Libri, Utenti, Prestiti).

***

## 4. Documentazione del Codice
Ogni componente del sistema è stato documentato secondo standard specifici:

- **Java**: Utilizzo di **Javadoc** su tutte le classi e metodi per spiegare contratti API e logiche SQL.
- **JavaScript**: Utilizzo di **JSDoc** per descrivere parametri di funzioni e manipolazioni DOM.
- **HTML/CSS**: Commenti strutturali che separano blocchi logici e descrivono la gerarchia visiva.

***

## 5. Workflow Critici

### Gestione dei Prestiti
Il processo è centralizzato in `RentService`. Quando un libro viene noleggiato, lo stato del libro passa a `"noleggiato"`. La restituzione (`/api/delivered`) reintegra la disponibilità e registra la chiusura del record di noleggio tramite query atomiche.

### Ricerca Globale
La ricerca utilizza un sistema di redirect intelligente:
1. L'utente preme la barra di ricerca o `Ctrl+K`.
2. Il JS cattura la query e ricarica la dashboard con parametri URL aggiuntivi (`&search=...`).
3. Il `BookService` riceve la query e filtra i risultati tramite pattern matching (LIKE) nel database.

***

## 6. Sviluppi Futuri
- Integrazione completa delle API REST nel popup di modifica (attualmente simulate in frontend).
- Implementazione di Spring Security per una gestione delle sessioni più robusta.
- Aggiunta di grafici statistici nella Dashboard Home tramite Chart.js.
gli admin; "Prenota" visibile agli utenti standard.
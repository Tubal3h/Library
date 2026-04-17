***

# Sistema Biblioteca - Documentazione Tecnica 

Questo documento fornisce una panoramica tecnica completa dell’architettura backend e del nuovo sistema di design frontend per il progetto "Sistema Biblioteca".

***

## 1. Panoramica del Progetto
Il "Sistema Biblioteca" è una dashboard gestionale moderna sviluppata con **Spring Boot** e **Java 21+**. Il sistema offre un'interfaccia premium basata sui ruoli per la gestione di utenti, catalogo libri e noleggi.

### Evoluzione Recente:
Recentemente il progetto è stato sottoposto a un profondo processo di **documentazione** globale:
- **CSS Architecture**: Passaggio da stili inline a un sistema di classi di utilità centralizzato in `style.css`.
- **Administrative Expansion**: Introduzione di workflow avanzati per l'aggiunta di edizioni e copie fisiche con validazione ISBN.
- **Mobile Optimization**: Implementazione di una navigazione "Bottom Bar" nativa per dispositivi touch e popup adattivi.
- **Documentation Standard**: Ogni file (HTML, CSS, JS, Java) include ora una documentazione dettagliata (Javadoc/JSDoc) per facilitare la manutenzione.

***

## 2. Architettura Backend: Design a Livelli
Il progetto segue una **architettura a tre livelli** rigorosa:

1. **Controller Layer (`it.controller`)**: Gestisce il routing, la validazione dei parametri e il flusso delle viste. Utilizza `RedirectAttributes` per la gestione degli stati di successo.
2. **Service Layer (`it.service`)**: Contiene la logica di business, la validazione granulare e il coordinamento tra repository (es. `insertBook` che tocca tre tabelle diverse).
3. **Repository Layer (`it.repository`)**: Gestisce la persistenza tramite **JdbcTemplate**, ottimizzando le performance con query SQL scritte a mano e RowMapper personalizzati.

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
- **[Navbar](file:///c:/Users/tubal_sqk4vbf/Desktop/Project/Library/sistema-biblioteca/src/main/resources/templates/fragments/navbar.html)**: Navigazione dinamica. Include una **Bottom Bar** ottimizzata per mobile che appare solo su schermi < 768px.
- **[Catalogo](file:///c:/Users/tubal_sqk4vbf/Desktop/Project/Library/sistema-biblioteca/src/main/resources/templates/fragments/catalog.html)**: Visualizzazione a schede con animazioni di entrata staggered e badge di stato dinamici.
- **[Popup](file:///c:/Users/tubal_sqk4vbf/Desktop/Project/Library/sistema-biblioteca/src/main/resources/templates/fragments/popup.html)**: Sistema modale polivalente gestito via JS e attributi Thymeleaf per feedback immediati.

***

## 4. Gestione Avanzata Inventario (Novità)

### Aggiunta Edizione
Workflow amministrativo che permette di registrare una nuova opera nel database. L'operazione è atomica e coinvolge:
1. Creazione del titolo in `books_names`.
2. Creazione dell'edizione in `edition` (legata ad autore, editore e categoria).
3. Inserimento automatico della prima copia fisica (`books`) con stato "disponibile".

### Gestione Copie via ISBN
Permette di espandere l'inventario di un'edizione esistente semplicemente scansionando/inserendo il codice ISBN. Il sistema identifica l'edizione e aggiunge un nuovo record fisico preservando l'integrità dei metadati.

### Eliminazione Logica
I libri non vengono rimossi fisicamente per preservare lo storico dei noleggi. Viene invece aggiornato il loro stato a `"eliminato"`, nascondendoli dal catalogo pubblico ma mantenendoli nel database per audit.

***

## 5. Workflow Critici

### Gestione dei Prestiti
Il processo è centralizzato in `RentService`. Quando un libro viene noleggiato, lo stato del libro passa a `"noleggiato"`. La restituzione (`/api/delivered`) reintegra la disponibilità e registra la chiusura del record di noleggio tramite query atomiche.

### Feedback Visivo (Success Popups)
Il sistema utilizza un'architettura **Stateless Redirect**:
1. Il Controller esegue l'operazione e aggiunge attributi a `RedirectAttributes` (es. `popupType=addEdition`, `popupBookTitle=...`).
2. Spring Boot effettua il redirect alla dashboard.
3. Il frontend (`popup.html`) intercetta questi attributi flash e renderizza automaticamente il modale di successo con i dati dinamici.

### Ricerca Globale
La ricerca utilizza un sistema di redirect intelligente:
1. L'utente preme la barra di ricerca.
2. Il JS cattura la query e ricarica la dashboard con parametri URL aggiuntivi (`&search=...`).
3. Il `BookService` riceve la query e filtra i risultati.

***

## 6. Sviluppi Futuri
- Integrazione completa delle API REST nel popup di modifica (attualmente simulate in frontend).
- Implementazione di Spring Security per una gestione delle sessioni più robusta.
- Aggiunta di grafici statistici nella Dashboard Home tramite Chart.js.

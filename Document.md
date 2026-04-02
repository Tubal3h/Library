***

# Sistema Biblioteca - Documentazione Tecnica

Questo documento fornisce una panoramica tecnica completa dell’architettura backend e delle sue funzionalità principali per il progetto "Sistema Biblioteca".

***

## 1. Panoramica del Progetto
Il "Sistema Biblioteca" è una dashboard per la gestione di una biblioteca sviluppata con **Spring Boot** e **Java 17+**. Il sistema ha l’obiettivo di fornire un’interfaccia moderna basata sui ruoli per la gestione degli utenti, del catalogo libri e dei registri di prestito.

### Tecnologie Principali:
- **Spring Boot**: Framework principale.
- **JdbcTemplate**: Livello di accesso ai dati per alte prestazioni e controllo diretto delle query SQL.
- **Thymeleaf**: Motore di template lato server per il rendering delle viste.
- **Lombok**: Semplifica lo sviluppo tramite la generazione automatica di codice boilerplate per modelli e DTO.

***

## 2. Architettura: Design a Livelli
Il progetto segue una classica **architettura a tre livelli**, che garantisce una chiara separazione delle responsabilità:

1. **Controller Layer**: Gestisce le richieste HTTP in ingresso e controlla il flusso dell’applicazione.
2. **Service Layer**: Contiene la logica di business principale e gestisce la conversione tra entità e DTO.
3. **Repository Layer**: Gestisce l’accesso diretto al database usando JdbcTemplate per prestazioni ottimizzate.

***

## 3. Documentazione dei Componenti

### 3.1. Controllers (`it.controller`)

- **`AuthController`**  
  **Funzionalità**: Login e accesso  
  **Descrizione**: Gestisce il punto di ingresso iniziale (`/`) e le API di login/logout. Utilizza un meccanismo di redirect personalizzato che passa il contesto utente tramite parametri URL per evitare una gestione complessa delle sessioni.

- **`DashboardController`**  
  **Funzionalità**: Gestione viste  
  **Descrizione**: Hub centrale dell’applicazione. Renderizza dinamicamente le diverse sezioni della dashboard (`home`, `catalog`, `users`) in base al ruolo dell’utente autenticato e alla sezione richiesta.

***

### 3.2. Services (`it.service`)

- **`AuthService`**  
  **Funzionalità**: Verifica credenziali  
  **Descrizione**: Valida i tentativi di login controllando email e password rispetto ai dati persistenti nel `UserRepository`.

- **`UserService`**  
  **Funzionalità**: Logica utenti  
  **Descrizione**: Gestisce la conversione delle entità `User` complete in oggetti `UserDto` pronti per la visualizzazione, garantendo che dati sensibili come le password non vengano mai esposti.

- **`CategoryService`**  
  **Funzionalità**: Logica di supporto  
  **Descrizione**: (Placeholder) Fornisce logica base per la gestione delle categorie della biblioteca.

***

### 3.3. Repositories (`it.repository`)

- **`UserRepository`**  
  **Funzionalità**: Persistenza utenti  
  **Descrizione**: Fornisce metodi per recuperare utenti tramite email e ottenere la lista completa degli account registrati.

- **`BookRepository`**  
  **Funzionalità**: Mapping avanzato catalogo  
  **Descrizione**: Contiene query SQL complesse con join tra più tabelle (Libri, Autori, ISBN, Editori, Categorie) per creare oggetti `BookCatalogDto` unificati per la visualizzazione del catalogo.

- **`RentRepository`**  
  **Funzionalità**: Gestione prestiti  
  **Descrizione**: Gestisce la persistenza dei record di prestito e dello storico.

- **`CategoryRepository`**  
  **Funzionalità**: Gestione categorie  
  **Descrizione**: Gestisce operazioni base di lookup e gestione delle categorie dei libri.

***

## 4. Modelli e Data Transfer Object

### 4.1. Modelli principali (`it.model`)
I modelli riflettono lo schema del database, includendo:

- **`User`**: Dettagli account (ID, Email, Password, Ruolo).
- **`Book`**: Entità principale libro con relazioni verso Titolo, Autore, Categoria e ISBN.
- **`Author`**, **`Category`**, **`Publisher`**, **`Isbn`**: Entità di supporto che definiscono i metadati del libro.
- **`RentalRecord`**: Storico delle transazioni tra utenti e libri.

***

### 4.2. DTO (`it.dto`)
Questi oggetti garantiscono un flusso dati pulito:

- **`LoginDto`**: Modello semplificato per il form di login.
- **`UserDto`**: Versione sicura per la UI del record utente.
- **`BookCatalogDto`**: Versione “flattened” dell’entità libro contenente dati leggibili (es. "Mario Rossi" invece di un ID).

***

## 5. Workflow Critici

### Flusso di Autenticazione
1. L’utente inserisce email e password in `index.html`.
2. `AuthController.login` riceve i dati.
3. `AuthService.login` verifica le credenziali tramite `UserRepository`.
4. Se il login ha successo, l’utente viene reindirizzato a `/dashboard?email=user@example.com`, che funge da chiave di sessione per le navigazioni successive.

***

### Estrazione del Catalogo
Il `BookRepository` esegue una query SQL con più join per unire dati frammentati provenienti da sei tabelle diverse in una singola lista ottimizzata di oggetti `BookCatalogDto`. Questo evita il problema delle **N+1 query**, recuperando tutti i dati in un’unica chiamata efficiente al database.
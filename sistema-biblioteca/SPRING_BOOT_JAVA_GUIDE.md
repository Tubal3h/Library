# Guida a Java e Spring Boot

Questa guida fornisce una panoramica dei concetti fondamentali di Java e Spring Boot, con esempi di codice pratici basati sulla struttura del progetto "Sistema Biblioteca".

---

## 1. Fondamenti di Java

### Classi e Oggetti
In Java, tutto ruota attorno alle classi. Una classe è un "progetto" per creare oggetti.

```java
public class Libro {
    private String titolo;
    private String autore;

    // Costruttore
    public Libro(String titolo, String autore) {
        this.titolo = titolo;
        this.autore = autore;
    }

    // Metodo
    public void descrivi() {
        System.out.println("Il libro '" + titolo + "' è scritto da " + autore);
    }
}
```

### Interfacce
Un'interfaccia definisce un contratto che le classi devono seguire. È fondamentale per il disaccoppiamento.

```java
public interface ServizioLibro {
    void aggiungiLibro(Libro libro);
}
```

### Stream API (Java 8+)
Gli Stream permettono di elaborare collezioni di dati in modo dichiarativo.

```java
List<String> nomi = Arrays.asList("Mario", "Luigi", "Peach");
List<String> nomiFiltrati = nomi.stream()
    .filter(n -> n.startsWith("L")) // Filtra nomi che iniziano con 'L'
    .map(String::toUpperCase)       // Trasforma in maiuscolo
    .collect(Collectors.toList());  // Raccoglie in una lista
```

### Optional
Evita i `NullPointerException` incapsulando valori che potrebbero essere assenti.

```java
Optional<String> nome = Optional.ofNullable(getLibroTitolo());
nome.ifPresent(System.out::println);
String risultato = nome.orElse("Titolo non disponibile");
```

---

## 2. Spring Boot Core

Spring Boot è un framework che semplifica la creazione di applicazioni Java "stand-alone".

### Inversion of Control (IoC) e Dependency Injection (DI)
Spring gestisce il ciclo di vita degli oggetti (Bean) e li "inietta" dove servono.

```java
@Service
public class BookService {
    private final BookRepository repository;

    @Autowired // Iniezione tramite costruttore (scelta raccomandata)
    public BookService(BookRepository repository) {
        this.repository = repository;
    }
}
```

### Annotazioni Comuni
- `@SpringBootApplication`: Punto di ingresso dell'app.
- `@RestController`: Definisce una classe che gestisce richieste HTTP (REST API).
- `@Service`: Indica che la classe contiene logica di business.
- `@Repository`: Indica che la classe interagisce con il database.
- `@Entity`: Definisce una classe come tabella del database (JPA/Hibernate).

---

## 3. Architettura del Progetto

Il progetto segue un'architettura a livelli:

1.  **Entity**: Rappresentano le tabelle del database.
2.  **Repository**: Interfaccia tra l'applicazione e il database.
3.  **Service**: Contiene la logica di business (es. calcoli, validazioni).
4.  **DTO (Data Transfer Object)**: Oggetti usati per trasportare dati tra i vari livelli o verso l'esterno (API), evitando di esporre direttamente le Entity.
5.  **Mapper**: Trasformano Entity in DTO e viceversa.

### Esempio di Flusso:
1. Il Controller riceve una richiesta.
2. Il Service chiama il Repository per ottenere un'Entity.
3. Il Service usa un Mapper per convertire l'Entity in un DTO.
4. Il DTO viene restituito al chiamante.

---

## 4. Esempio Pratico: Gestione Eccezioni

Spring permette di gestire le eccezioni in modo centralizzato usando `@ControllerAdvice`.

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFound(BookNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
```

---

## 5. Maven

Maven gestisce le dipendenze del progetto tramite il file `pom.xml`.

- `mvn clean`: Pulisce la cartella `target`.
- `mvn compile`: Compila il codice sorgente.
- `mvn package`: Crea il file JAR/WAR per il deployment.

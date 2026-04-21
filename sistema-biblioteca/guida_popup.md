# 📘 Guida Completa al Sistema Popup (Sistema Biblioteca)

Questa guida spiega come utilizzare, personalizzare ed estendere il sistema di finestre modali (popup) utilizzato nell'applicazione. Il sistema è progettato per essere **modulare**, **premium** (glassmorphism) e **responsive**.

---

## 🏗️ 1. Struttura HTML (`popup.html`)

Il popup risiede in un unico frammento Thymeleaf (`fragments/popup.html`) che viene incluso nelle varie pagine. Utilizza un contenitore principale con ID `#genericPopup`.

### Elementi Chiave:
- **`overlay`**: Lo sfondo scuro con sfocatura (`blur`).
- **`header`**: Contiene il titolo (`#popupTitle`), l'icona (`#popupIcon`) e il tasto di chiusura.
- **`body`**: Un contenitore flessibile (`#popupBody`) che contiene diversi blocchi di contenuto pre-definiti (div con ID specifici).
- **`footer`**: Contiene i bottoni di azione (`Annulla` e `#popupConfirmBtn`).

### Blocchi di Contenuto (ID da conoscere):
- `editBookContent`: Form per la modifica dei dati di base del libro.
- `addCopyContent`: Schermata di successo per l'aggiunta di una copia.
- `addEditionContent`: Form completo per inserire una nuova edizione (ISBN, Autore, etc.).
- `confirmContent`: Dialogo generico "Sei sicuro?" per azioni pericolose.
- `viewBooksEditionContent`: Tabella dinamica per visualizzare l'elenco delle copie.

---

## 🎨 2. Stile CSS (`popup.css`)

Il design segue i principi di **Modern Web Design**:
- **Glassmorphism**: Uso di `backdrop-filter: blur(14px)` e trasparenze.
- **Animazioni**: Il popup entra con una scala fluida (`animate-scale-in`) e i contenuti interni appaiono con un leggero movimento verso l'alto (`animate-fade-in-up`).
- **Responsive**: 
  - Su **Desktop**: Centrato e largo 600px.
  - Su **Mobile**: Diventa un **Bottom Sheet** (scivola dal basso, copre la larghezza totale) per una migliore UX touch.

---

## 🧠 3. Logica JavaScript (`popup.js`)

Il file JS gestisce la visibilità dei contenuti e le interazioni.

### Funzioni di Apertura:
| Funzione | Scopo |
| :--- | :--- |
| `openPopup(action, id, ...)` | Apre la modifica libro o la conferma di aggiunta copia. |
| `openConfirmPopup(action, title, msg, url)` | Apre la richiesta di conferma generica ("Sei sicuro?"). |
| `openAddEditionPopup()` | Mostra il form per una nuova edizione. |
| `openViewBooksPopup(element)` | Carica via API le copie di un'edizione e le mostra. |

### Funzioni di Controllo:
- `closePopup()`: Chiude il popup e ripristina lo scroll della pagina.
- `closePopupOnBackdrop(event)`: Chiude se si clicca fuori dall'area del contenuto.

---

## 🚀 4. Come Usarlo (Esempi Pratici)

### Invocare una Conferma di Eliminazione
Se hai un bottone in una tabella, puoi usare gli attributi `data-*`:
```html
<button 
    data-title="Titolo Libro" 
    data-url="/api/delete?id=123" 
    onclick="triggerConfirmDelete(this)">
    Elimina
</button>
```

### Aprire il popup "Visualizza Copie"
```html
<button 
    data-id="45" 
    data-title="Il Signore degli Anelli" 
    onclick="openViewBooksPopup(this)">
    Visualizza Libri
</button>
```

---

## 🛠️ 5. Come Estenderlo (Aggiungere un nuovo Popup)

Se vuoi aggiungere una nuova funzionalità (es. "Visualizza Profilo Utente"):

1.  **HTML**: Aggiungi un `div` in `popup.html` con un ID unico:
    ```html
    <div id="userProfileContent" class="none">
        <!-- I tuoi campi qui -->
    </div>
    ```
2.  **JS (Reset)**: Aggiungi l'ID `'userProfileContent'` agli array di reset nelle funzioni `openPopup` e `openConfirmPopup` (per assicurarti che il contenuto vecchio sparisca quando apri un altro popup).
3.  **JS (Funzione)**: Crea la funzione di apertura:
    ```javascript
    function openUserProfile(userData) {
        // 1. Reset e mostra overlay
        // 2. Popola i dati in userProfileContent
        // 3. Rimuovi classe .none da userProfileContent
        // 4. Mostra il popup globale
    }
    ```

---

## 💡 Suggerimenti Premium
- **Blocco Scroll**: Quando il popup è aperto, il JS imposta `document.body.style.overflow = 'hidden'` per evitare che l'utente scorra la pagina sotto.
- **Validazione**: Per i form (come l'Edizione), la logica di validazione (es. ISBN 13 cifre) è integrata sia nel JS che nell'HTML (`pattern`, `maxlength`).

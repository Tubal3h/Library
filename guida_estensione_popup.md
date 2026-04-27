# Guida: Come aggiungere o personalizzare i Popup

Grazie al nuovo sistema modulare, aggiungere un nuovo popup o crearne una variante è estremamente semplice e richiede modifiche minime.

---

## 1. Aggiungere un nuovo Pannello (HTML)
Ogni popup è tecnicamente un "pannello" all'interno del corpo del modale principale.

1. Apri `popup.html`.
2. All'interno di `<div id="popupBody" class="popup-body">`, aggiungi il tuo nuovo contenuto:

```html
<!-- Nuovo Pannello Esempio -->
<div id="myNewPanel" class="none animate-fade-in-up">
    <h4 class="text-lg fw-700 mb-2">Titolo del Contenuto</h4>
    <p class="opacity-70 text-sm">Inserisci qui il tuo messaggio o i tuoi campi form.</p>
    <div class="input-group mt-4">
        <label class="text-xs uppercase fw-700 opacity-60 mb-2 block">Campo Esempio</label>
        <input type="text" id="myInputId" class="w-100 p-4 radius-12 border-none font-size-small" placeholder="Scrivi qualcosa...">
    </div>
</div>
```

---

## 2. Registrare il Pannello (JS)
Per permettere al sistema di nascondere automaticamente il tuo pannello quando ne viene aperto un altro, devi registrarlo.

1. Apri `popup.js`.
2. Aggiungi l'ID del tuo pannello all'array `PANELS`:

```javascript
const PANELS = [
    // ... altri id ...,
    'myNewPanel' // Aggiungi il tuo ID qui
];
```

---

## 3. Aprire il Popup (JS)
Ora puoi creare una funzione per aprire il tuo nuovo popup usando il metodo `Popup.open()`.

### Esempio Base:
```javascript
function openMyCustomPopup() {
    Popup.open({
        title: 'Il Mio Nuovo Popup',
        icon: 'fa-star',               // Classe FontAwesome
        panelId: 'myNewPanel',         // L'ID creato nell'HTML
        confirmText: 'Salva Dati',
        onConfirm: () => {
            const val = document.getElementById('myInputId').value;
            console.log("Valore inserito:", val);
            Popup.close();
        }
    });
}
```

---

## 4. Personalizzazioni Avanzate
L'oggetto `config` passato a `Popup.open()` accetta diversi parametri per cambiare il comportamento:

| Parametro | Descrizione | Default |
| :--- | :--- | :--- |
| `title` | Testo dell'intestazione | 'Messaggio' |
| `icon` | Icona FontAwesome (es. `fa-check`) | 'fa-info-circle' |
| `iconClass` | Classe CSS extra per il box icona (es. `icon-bg-success`, `icon-bg-error`) | '' |
| `panelId` | ID del div contenuto nell'HTML | **Richiesto** |
| `confirmText` | Testo del pulsante principale | 'Conferma' |
| `onConfirm` | Funzione da eseguire al click su Conferma | `Popup.close()` |
| `showCancel` | Mostra/Nascondi il tasto Annulla | `true` |
| `footerVisible` | Mostra/Nascondi l'intero footer | `true` |

### Esempio Versione "Solo Messaggio" (Senza Footer):
```javascript
Popup.open({
    title: 'Avviso Importante',
    icon: 'fa-exclamation-triangle',
    iconClass: 'icon-bg-error',
    panelId: 'myNewPanel',
    footerVisible: false // Nasconde i bottoni
});
```

---

## 5. Creare varianti di popup esistenti
Se vuoi creare una versione diversa di un popup che già esiste (es. un edit che punta a un'altra API):

1. Non serve toccare l'HTML se i campi sono gli stessi.
2. Crea una nuova funzione in `popup.js` che usa lo stesso `panelId` ma cambia `onConfirm` o `title`.

```javascript
function openAlternativeEditPopup(id, val) {
    Popup.open({
        title: 'Modifica Alternativa',
        panelId: 'editTitleOnlyContent', // Riutilizza pannello esistente
        onConfirm: () => {
            // Logica specifica per questa versione
            const newVal = document.getElementById('editTitleOnlyInput').value;
            window.location.href = `/api/alt-update?id=${id}&val=${newVal}`;
        }
    });
    document.getElementById('editTitleOnlyCurrent').innerText = val;
}
```

> [!TIP]
> Ricorda di usare sempre `Popup.close()` all'interno di `onConfirm` se l'azione non prevede un ricaricamento della pagina.

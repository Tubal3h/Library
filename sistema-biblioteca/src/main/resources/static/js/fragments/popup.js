/**
 * popup.js
 * Gestisce la logica dei modali generici nell'applicazione (popup.html).
 * Permette di aprire il popup in diverse modalità (modifica, successo)
 * e gestisce il riempimento dinamico dei campi del form.
 */

/**
 * Apre il popup modale per la modifica di un libro o per confermare un'operazione.
 * 
 * @param {string} action - Il tipo di azione da eseguire ('edit' o 'addCopy').
 * @param {string} bookId - L'identificativo univoco del libro.
 * @param {string} titleTxt - Il titolo del libro.
 * @param {string} authorTxt - L'autore principale del libro.
 * @param {string} categoryTxt - La categoria del libro.
 * @param {string} publisherTxt - L'editore del libro.
 */
function openPopup(action, bookId, titleTxt, authorTxt, categoryTxt, publisherTxt) {
    const popup = document.getElementById('genericPopup');
    const title = document.getElementById('popupTitle');
    const icon = document.getElementById('popupIcon');
    const confirmBtn = document.getElementById('popupConfirmBtn');
    const editContent = document.getElementById('editBookContent');
    const addCopyContent = document.getElementById('addCopyContent');

    // Verifica che tutti gli elementi necessari siano presenti nel DOM
    if (!popup || !title || !icon || !confirmBtn || !editContent || !addCopyContent) return;

    // Reset iniziale dello stato dei contenuti
    editContent.classList.add('none');
    addCopyContent.classList.add('none');

    // Configurazione del popup basata sull'azione richiesta
    if (action === 'edit') {
        /**
         * MODALITÀ: MODIFICA LIBRO
         */
        title.innerText = 'Modifica Libro';
        icon.className = 'fa-solid fa-pen-to-square text-white';

        // Gestione classi per lo sfondo dell'icona (Design System)
        icon.parentElement.classList.remove('icon-bg-success');
        icon.parentElement.classList.add('icon-box-accent');

        editContent.classList.remove('none');
        document.querySelector('.btn-link-action').classList.remove('none');

        // Popolamento dinamico delle informazioni del libro
        const bookNameElem = document.getElementById('editBookName');
        if (bookNameElem) bookNameElem.innerText = titleTxt;

        const titleInput = document.getElementById('editTitleInput');
        if (titleInput) titleInput.value = titleTxt;

        // Selezione dell'autore corretto nel dropdown
        const authorSelect = document.getElementById('editAuthorInput');
        if (authorSelect && authorTxt) {
            for (let i = 0; i < authorSelect.options.length; i++) {
                if (authorSelect.options[i].text === authorTxt) {
                    authorSelect.selectedIndex = i;
                    break;
                }
            }
        }

        // Selezione della categoria nel dropdown
        const categorySelect = document.getElementById('editCategoryInput');
        if (categorySelect && categoryTxt) {
            for (let i = 0; i < categorySelect.options.length; i++) {
                if (categorySelect.options[i].text === categoryTxt) {
                    categorySelect.selectedIndex = i;
                    break;
                }
            }
        }

        // Selezione dell'editore nel dropdown
        const publisherSelect = document.getElementById('editPublisherInput');
        if (publisherSelect && publisherTxt) {
            for (let i = 0; i < publisherSelect.options.length; i++) {
                if (publisherSelect.options[i].text === publisherTxt) {
                    publisherSelect.selectedIndex = i;
                    break;
                }
            }
        }

        confirmBtn.innerText = 'Salva Cambiamenti';
    }
    else if (action === 'addCopy') {
        /**
         * MODALITÀ: SUCCESSO (AGGIUNTA COPIA)
         */
        title.innerText = 'Operazione Riuscita';
        icon.className = 'fa-solid fa-circle-check text-white';

        // Cambio colore icona in stile "Successo"
        icon.parentElement.classList.remove('icon-box-accent');
        icon.parentElement.classList.add('icon-bg-success');

        addCopyContent.classList.remove('none');

        const addCopyNameElem = document.getElementById('addCopyBookName');
        if (addCopyNameElem) addCopyNameElem.innerText = titleTxt;

        confirmBtn.innerText = 'Chiudi';
        document.querySelector('.btn-link-action').classList.add('none');
    }

    // Visualizza il popup e blocca lo scroll del body
    popup.classList.remove('none');
    document.body.style.overflow = 'hidden';

    /**
     * Listener per il pulsante di conferma.
     * Attualmente simula l'interazione con il backend.
     */
    confirmBtn.onclick = () => {
        console.log(`[Popup] Action '${action}' triggered for book ID: ${bookId}`);
        alert(`Simulazione: Operazione '${action}' eseguita correttamente lato client.`);
        closePopup();
    };
}

/**
 * Chiude il popup modale e ripristina lo scroll della pagina.
 */
function closePopup() {
    const popup = document.getElementById('genericPopup');
    if (popup) {
        popup.classList.add('none');
        document.body.style.overflow = '';
    }
}

/**
 * Chiude il popup se l'utente clicca fuori dall'area del contenuto (sull'overlay).
 * @param {Event} event - L'evento click.
 */
function closePopupOnBackdrop(event) {
    if (event.target === event.currentTarget) {
        closePopup();
    }
}

/**
 * Apre il popup in modalità "Aggiungi Edizione".
 * Mostra il form con i campi Titolo, ISBN (13 cifre numeriche), Data, Autore, Categoria, Editore.
 */
function openAddEditionPopup() {
    const popup = document.getElementById('genericPopup');
    const title = document.getElementById('popupTitle');
    const icon = document.getElementById('popupIcon');
    const confirmBtn = document.getElementById('popupConfirmBtn');
    const addEditionContent = document.getElementById('addEditionContent');

    if (!popup || !title || !icon || !confirmBtn || !addEditionContent) return;

    // Nasconde tutti gli altri pannelli
    ['editBookContent', 'addCopyContent', 'deleteBookContent', 'errorContent'].forEach(id => {
        const el = document.getElementById(id);
        if (el) el.classList.add('none');
    });

    // Intestazione popup
    title.innerText = 'Aggiungi Edizione';
    icon.className = 'fa-solid fa-plus text-white font-size-medium';
    icon.parentElement.classList.remove('icon-bg-success');
    icon.parentElement.classList.add('icon-box-accent');

    // Reset form
    const titleInput = document.getElementById('addEditionTitleInput');
    const isbnInput = document.getElementById('addEditionIsbnInput');
    const dateInput = document.getElementById('addEditionDateInput');
    const authorSel = document.getElementById('addEditionAuthorInput');
    const catSel = document.getElementById('addEditionCategoryInput');
    const pubSel = document.getElementById('addEditionPublisherInput');
    const isbnErr = document.getElementById('addEditionIsbnError');

    if (titleInput) titleInput.value = '';
    if (isbnInput) isbnInput.value = '';
    if (dateInput) dateInput.value = '';
    if (authorSel) authorSel.selectedIndex = 0;
    if (catSel) catSel.selectedIndex = 0;
    if (pubSel) pubSel.selectedIndex = 0;
    if (isbnErr) isbnErr.style.display = 'none';

    addEditionContent.classList.remove('none');

    // Mostra il pulsante Annulla
    const cancelBtn = document.querySelector('.btn-link-action');
    if (cancelBtn) cancelBtn.classList.remove('none');

    confirmBtn.innerText = 'Aggiungi Edizione';

    // Conferma con validazione ISBN
    confirmBtn.onclick = () => {
        if (isbnErr) isbnErr.style.display = 'none';

        // Validazione HTML5 e invio tramite Thymeleaf Form
        const form = document.getElementById('addEditionForm');
        if (form) {
            console.log('[Popup] Sottomissione form Aggiungi Edizione...');
            form.submit();
        }
    };

    // Mostra il popup
    popup.classList.remove('none');
    document.body.style.overflow = 'hidden';
}

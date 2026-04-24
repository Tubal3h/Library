/**
 * popup.js
 * Gestisce la logica dei modali generici nell'applicazione (popup.html).
 * Permette di aprire il popup in diverse modalità (modifica, successo)
 * e gestisce il riempimento dinamico dei campi del form.
 */

const POPUP_PANELS = [
    'editBookContent', 
    'addCopyContent', 
    'addEditionSuccessContent', 
    'deleteBookContent', 
    'confirmContent', 
    'errorContent', 
    'addEditionContent', 
    'deliveredRentContent', 
    'viewBooksEditionContent',
    'editTitleOnlyContent',
    'editAuthorOnlyContent',
    'editPublisherOnlyContent',
    'editCategoryOnlyContent',
    'addUserContent',
    'addUserSuccessContent',
    'updateTitleContent',
    'updateAuthorContent',
    'updatePublisherContent',
    'updateCategoryContent',
    'deleteUserContent'
];

/**
 * Nasconde tutti i pannelli del popup.
 */
function hideAllPanels() {
    POPUP_PANELS.forEach(id => {
        const el = document.getElementById(id);
        if (el) el.classList.add('none');
    });
}

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

    // Assicura che il footer sia visibile (potrebbe essere stato nascosto da un messaggio di successo/errore)
    const footer = document.querySelector('.popup-footer');
    if (footer) footer.classList.remove('none');

    // Nasconde tutti gli altri pannelli
    hideAllPanels();

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
        confirmBtn.style.background = ''; 
        confirmBtn.style.borderColor = '';
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

        const addCopyTitleElem = document.getElementById('addCopyBookTitle');
        if (addCopyTitleElem) addCopyTitleElem.innerText = titleTxt;

        const addCopyIdElem = document.getElementById('addCopyBookId');
        if (addCopyIdElem) addCopyIdElem.innerText = bookId;

        const addCopyIsbnElem = document.getElementById('addCopyBookName');
        if (addCopyIsbnElem) addCopyIsbnElem.innerText = ''; // Non abbiamo l'ISBN in questo metodo, ma evitiamo testi errati

        confirmBtn.innerText = 'Chiudi';
        confirmBtn.style.background = ''; 
        confirmBtn.style.borderColor = '';
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
 * Chiude il popup modale e ripristina la dashboard pulendo l'URL dai parametri.
 */
function closePopup() {
    const popup = document.getElementById('genericPopup');
    if (popup) {
        popup.classList.add('none');
        document.body.style.overflow = '';
        
        // Se l'URL contiene parametri del popup, ricarichiamo la dashboard pulita
        if (window.location.search.includes('action=viewCopies')) {
            window.location.href = '/dashboard';
            return;
        }
    }
}

/**
 * Funzione di inizializzazione chiamata lato server (via bridge script in popup.html)
 * per aprire il popup delle copie senza fetch.
 */
function initServerSidePopup() {
    const popup = document.getElementById('genericPopup');
    const viewContent = document.getElementById('viewBooksEditionContent');
    const title = document.getElementById('popupTitle');
    const icon = document.getElementById('popupIcon');
    const confirmBtn = document.getElementById('popupConfirmBtn');

    if (!popup || !viewContent || !title || !icon || !confirmBtn) return;

    // Assicura che il footer sia visibile
    const footer = document.querySelector('.popup-footer');
    if (footer) footer.classList.remove('none');

    // Header specifico per "Visualizza Copie"
    title.innerText = 'Gestione copie';
    icon.className = 'fa-solid fa-eye text-white';
    icon.parentElement.classList.remove('icon-bg-success');
    icon.parentElement.classList.add('icon-box-accent');

    // Mostra il pannello delle copie
    viewContent.classList.remove('none');

    // Configurazione Bottone (Chiudi)
    confirmBtn.innerText = 'Chiudi';
    confirmBtn.onclick = () => closePopup();

    // Mostra l'overlay
    popup.classList.remove('none');
    document.body.style.overflow = 'hidden';
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
 * Helper per avviare la conferma eliminazione leggendo i dati dall'elemento HTML.
 * @param {HTMLElement} element - Il bottone cliccato.
 */
function triggerConfirmDelete(element) {
    const title = element.getAttribute('data-title');
    const url = element.getAttribute('data-url');
    openConfirmPopup('delete', title, 'Verrà eliminata questa copia dalla libreria', url);
}

/**
 * Helper per avviare la conferma eliminazione utente leggendo i dati dall'elemento HTML.
 * @param {HTMLElement} element - Il bottone cliccato.
 */
function triggerConfirmDeleteUser(element) {
    const title = element.getAttribute('data-title');
    const url = element.getAttribute('data-url');
    openConfirmPopup('delete', title, 'Verrà eliminato definitivamente questo dipendente dal sistema', url);
}

/**
 * Helper per avviare la conferma aggiunta copia leggendo i dati dall'elemento HTML.
 * @param {HTMLElement} element - Il bottone cliccato.
 */
function triggerConfirmAdd(element) {
    const title = element.getAttribute('data-title');
    const url = element.getAttribute('data-url');
    openConfirmPopup('add', title, 'Si aggiungerà una nuova copia per questa edizione', url);
}

/**
 * Helper per avviare la conferma consegna leggendo i dati dall'elemento HTML.
 * @param {HTMLElement} element - Il bottone cliccato.
 */
function triggerConfirmDelivered(element) {
    const title = element.getAttribute('data-title');
    const url = element.getAttribute('data-url');
    openConfirmPopup('delivered', title, 'Il manuale verrà reso disponibile', url);
}

/**
 * Apre il popup in modalità di conferma ("Sei sicuro?").
 * 
 * @param {string} action - 'delete', 'add', 'delivered' o altro tipo di azione.
 * @param {string} titleTxt - Titolo dell'operazione (es. il nome del libro).
 * @param {string} message - Messaggio di conferma.
 * @param {string} confirmUrl - URL a cui reindirizzare dopo la conferma.
 */
function openConfirmPopup(action, titleTxt, message, confirmUrl) {
    const popup = document.getElementById('genericPopup');
    const title = document.getElementById('popupTitle');
    const icon = document.getElementById('popupIcon');
    const confirmBtn = document.getElementById('popupConfirmBtn');
    const confirmContent = document.getElementById('confirmContent');

    if (!popup || !title || !icon || !confirmBtn || !confirmContent) return;

    // Assicura che il footer sia visibile
    const footer = document.querySelector('.popup-footer');
    if (footer) footer.classList.remove('none');

    // Nasconde tutti gli altri pannelli
    hideAllPanels();

    // Configurazione Header
    title.innerText = 'Richiesta Conferma';
    icon.className = 'fa-solid fa-circle-question text-white';
    icon.parentElement.classList.remove('icon-bg-success');
    icon.parentElement.classList.add('icon-box-accent');

    // Configurazione Contenuto Conferma
    const confirmTitleElem = document.getElementById('confirmTitle');
    const confirmMsgElem = document.getElementById('confirmMessage');
    const confirmDetailsElem = document.getElementById('confirmDetails');
    const confirmIconBox = document.getElementById('confirmIconBox');
    const confirmIconInner = document.getElementById('confirmIconInner');

    if (confirmTitleElem) confirmTitleElem.innerText = 'Confermi l\'operazione?';
    if (confirmMsgElem) confirmMsgElem.innerText = message;
    
    if (confirmDetailsElem) {
        confirmDetailsElem.innerText = titleTxt;
        confirmDetailsElem.style.display = titleTxt ? 'block' : 'none';
    }

    // Design specifico per azione
    if (action === 'delete') {
        confirmBtn.innerText = 'Elimina.';
        confirmBtn.style.setProperty('background', 'var(--color-error)', 'important');
        confirmBtn.style.setProperty('border-color', 'var(--color-error)', 'important');
        if (confirmIconBox) {
            confirmIconBox.style.background = 'rgba(198, 40, 40, 0.1)';
            confirmIconBox.style.color = 'var(--color-error)';
        }
        if (confirmIconInner) confirmIconInner.className = 'fa-solid fa-trash-can';
    } else {
        confirmBtn.innerText = 'Procedi.';
        confirmBtn.style.background = ''; 
        confirmBtn.style.borderColor = '';
        if (confirmIconBox) {
            confirmIconBox.style.background = 'rgba(245, 166, 35, 0.1)';
            confirmIconBox.style.color = 'var(--color-accent)';
        }
        if (confirmIconInner) confirmIconInner.className = 'fa-solid fa-circle-question';
    }

    confirmContent.classList.remove('none');

    // Mostra il pulsante Annulla
    const cancelBtn = document.querySelector('.btn-link-action');
    if (cancelBtn) cancelBtn.classList.remove('none');

    // Handler conferma
    confirmBtn.onclick = () => {
        window.location.href = confirmUrl;
    };

    // Mostra il popup
    popup.classList.remove('none');
    document.body.style.overflow = 'hidden';
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

    // Assicura che il footer sia visibile
    const footer = document.querySelector('.popup-footer');
    if (footer) footer.classList.remove('none');

    // Nasconde tutti gli altri pannelli
    hideAllPanels();

    // Intestazione popup
    title.innerText = 'Aggiungi Edizione';
    icon.className = 'fa-solid fa-plus text-white font-size-medium';
    icon.parentElement.classList.remove('icon-bg-success');
    icon.parentElement.classList.add('icon-box-accent');

    // Reset form
    const titleInput = document.getElementById('addEditionTitleInput');
    const isbnInput = document.getElementById('addEditionIsbnInput');
    const dateInput = document.getElementById('addEditionDateInput');
    const authorNameInput = document.getElementById('addEditionAuthorNameInput');
    const authorLastNameInput = document.getElementById('addEditionAuthorLastNameInput');
    const catInput = document.getElementById('addEditionCategoryInput');
    const pubInput = document.getElementById('addEditionPublisherInput');
    const isbnErr = document.getElementById('addEditionIsbnError');

    if (titleInput) titleInput.value = '';
    if (isbnInput) isbnInput.value = '';
    if (dateInput) dateInput.value = '';
    if (authorNameInput) authorNameInput.value = '';
    if (authorLastNameInput) authorLastNameInput.value = '';
    if (catInput) catInput.value = '';
    if (pubInput) pubInput.value = '';
    if (isbnErr) isbnErr.style.display = 'none';

    addEditionContent.classList.remove('none');

    // Mostra il pulsante Annulla
    const cancelBtn = document.querySelector('.btn-link-action');
    if (cancelBtn) cancelBtn.classList.remove('none');

    confirmBtn.innerText = 'Aggiungi Edizione';
    confirmBtn.style.background = ''; 
    confirmBtn.style.borderColor = '';

    // Conferma con validazione ISBN
    confirmBtn.onclick = () => {
        if (isbnErr) isbnErr.style.display = 'none';

        // Validazione HTML5 e invio tramite Thymeleaf Form
        const form = document.getElementById('addEditionForm');
        if (form) {
            form.submit();
        }
    };

    // Mostra il popup
    popup.classList.remove('none');
    document.body.style.overflow = 'hidden';
}

/**
 * Apre il popup in modalità "Aggiungi Dipendente/Utente".
 * Mostra il form con i campi Nome, Cognome, Email, Password, Ruolo.
 */
function openAddUserPopup() {
    const popup = document.getElementById('genericPopup');
    const title = document.getElementById('popupTitle');
    const icon = document.getElementById('popupIcon');
    const confirmBtn = document.getElementById('popupConfirmBtn');
    const addUserContent = document.getElementById('addUserContent');

    if (!popup || !title || !icon || !confirmBtn || !addUserContent) return;

    // Assicura che il footer sia visibile
    const footer = document.querySelector('.popup-footer');
    if (footer) footer.classList.remove('none');

    // Nasconde tutti gli altri pannelli
    hideAllPanels();

    // Intestazione popup
    title.innerText = 'Aggiungi Dipendente';
    icon.className = 'fa-solid fa-user-plus text-white font-size-medium';
    icon.parentElement.classList.remove('icon-bg-success');
    icon.parentElement.classList.add('icon-box-accent');

    // Reset form e stato visivo
    const form = document.getElementById('addUserForm');
    if (form) form.reset();

    // Ripristina anteprima email al default
    updateAddUserEmailPreview();

    // Ripristina visivamente la radio-card del ruolo
    updateRoleSelection();

    addUserContent.classList.remove('none');

    // Mostra il pulsante Annulla
    const cancelBtn = document.querySelector('.btn-link-action');
    if (cancelBtn) cancelBtn.classList.remove('none');

    confirmBtn.innerText = 'Aggiungi Dipendente';
    confirmBtn.style.background = '';
    confirmBtn.style.borderColor = '';

    confirmBtn.onclick = () => {
        if (form && form.checkValidity()) {
            form.submit();
        } else if (form) {
            form.reportValidity();
        }
    };

    // Mostra il popup
    popup.classList.remove('none');
    document.body.style.overflow = 'hidden';
}

// ─── FUNZIONI MODIFICA SINGOLO CAMPO ───────────────────────────────────────────

/**
 * Apre il popup per modificare il solo titolo di un libro/edizione.
 * @param {string} id - ID del libro/edizione.
 * @param {string} currentTitle - Titolo corrente.
 */
function openEditTitlePopup(id, currentTitle) {
    const popup = document.getElementById('genericPopup');
    const title = document.getElementById('popupTitle');
    const icon = document.getElementById('popupIcon');
    const confirmBtn = document.getElementById('popupConfirmBtn');
    const panel = document.getElementById('editTitleOnlyContent');

    if (!popup || !title || !icon || !confirmBtn || !panel) return;

    const footer = document.querySelector('.popup-footer');
    if (footer) footer.classList.remove('none');

    hideAllPanels();

    title.innerText = 'Modifica Titolo';
    icon.className = 'fa-solid fa-pen-to-square text-white font-size-medium';
    icon.parentElement.classList.remove('icon-bg-success');
    icon.parentElement.classList.add('icon-box-accent');

    const currentElem = document.getElementById('editTitleOnlyCurrent');
    const inputElem = document.getElementById('editTitleOnlyInput');
    if (currentElem) currentElem.innerText = currentTitle;
    if (inputElem) inputElem.value = currentTitle;

    panel.classList.remove('none');

    const cancelBtn = document.querySelector('.btn-link-action');
    if (cancelBtn) cancelBtn.classList.remove('none');

    confirmBtn.innerText = 'Salva Titolo';
    confirmBtn.style.background = '';
    confirmBtn.style.borderColor = '';
    confirmBtn.onclick = () => {
        const newVal = inputElem ? inputElem.value.trim() : '';
        if (!newVal) return;
        console.log(`[Popup] Modifica titolo id=${id} → "${newVal}"`);
        window.location.href = `/api/updateBookTitle?editionId=${id}&title=${encodeURIComponent(newVal)}`;
    };

    popup.classList.remove('none');
    document.body.style.overflow = 'hidden';
}

/**
 * Apre il popup per modificare il solo autore di un libro/edizione.
 * @param {string} id - ID del libro/edizione.
 * @param {string} currentAuthor - Autore corrente.
 */
function openEditAuthorPopup(id, currentAuthor) {
    const popup = document.getElementById('genericPopup');
    const title = document.getElementById('popupTitle');
    const icon = document.getElementById('popupIcon');
    const confirmBtn = document.getElementById('popupConfirmBtn');
    const panel = document.getElementById('editAuthorOnlyContent');

    if (!popup || !title || !icon || !confirmBtn || !panel) return;

    const footer = document.querySelector('.popup-footer');
    if (footer) footer.classList.remove('none');

    hideAllPanels();

    title.innerText = 'Modifica Autore';
    icon.className = 'fa-solid fa-user-pen text-white font-size-medium';
    icon.parentElement.classList.remove('icon-bg-success');
    icon.parentElement.classList.add('icon-box-accent');

    const currentElem = document.getElementById('editAuthorOnlyCurrent');
    if (currentElem) currentElem.innerText = currentAuthor;

    // Pre-compila nome e cognome separando al primo spazio
    const parts = currentAuthor ? currentAuthor.split(' ') : [];
    const firstInput = document.getElementById('editAuthorFirstNameInput');
    const lastInput = document.getElementById('editAuthorLastNameInput');
    if (firstInput) firstInput.value = parts[0] || '';
    if (lastInput) lastInput.value = parts.slice(1).join(' ') || '';

    panel.classList.remove('none');

    const cancelBtn = document.querySelector('.btn-link-action');
    if (cancelBtn) cancelBtn.classList.remove('none');

    confirmBtn.innerText = 'Salva Autore';
    confirmBtn.style.background = '';
    confirmBtn.style.borderColor = '';
    confirmBtn.onclick = () => {
        const firstName = firstInput ? firstInput.value.trim() : '';
        const lastName = lastInput ? lastInput.value.trim() : '';
        if (!firstName && !lastName) return;
        console.log(`[Popup] Modifica autore id=${id} → "${firstName} ${lastName}"`);
        window.location.href = `/api/updateAuthor?editionId=${id}&authorName=${encodeURIComponent(firstName)}&authorLastName=${encodeURIComponent(lastName)}`;
    };

    popup.classList.remove('none');
    document.body.style.overflow = 'hidden';
}

/**
 * Apre il popup per modificare il solo editore di un libro/edizione.
 * @param {string} id - ID del libro/edizione.
 * @param {string} currentPublisher - Editore corrente.
 */
function openEditPublisherPopup(id, currentPublisher) {
    const popup = document.getElementById('genericPopup');
    const title = document.getElementById('popupTitle');
    const icon = document.getElementById('popupIcon');
    const confirmBtn = document.getElementById('popupConfirmBtn');
    const panel = document.getElementById('editPublisherOnlyContent');

    if (!popup || !title || !icon || !confirmBtn || !panel) return;

    const footer = document.querySelector('.popup-footer');
    if (footer) footer.classList.remove('none');

    hideAllPanels();

    title.innerText = 'Modifica Editore';
    icon.className = 'fa-solid fa-building-columns text-white font-size-medium';
    icon.parentElement.classList.remove('icon-bg-success');
    icon.parentElement.classList.add('icon-box-accent');

    const currentElem = document.getElementById('editPublisherOnlyCurrent');
    const inputElem = document.getElementById('editPublisherOnlyInput');
    if (currentElem) currentElem.innerText = currentPublisher;
    if (inputElem) inputElem.value = currentPublisher;

    panel.classList.remove('none');

    // Reset dropdown suggerimenti
    const dropdown = document.getElementById('publisherSuggestionsDropdown');
    if (dropdown) dropdown.style.display = 'none';

    const cancelBtn = document.querySelector('.btn-link-action');
    if (cancelBtn) cancelBtn.classList.remove('none');

    confirmBtn.innerText = 'Salva Editore';
    confirmBtn.style.background = '';
    confirmBtn.style.borderColor = '';
    confirmBtn.onclick = () => {
        const newVal = inputElem ? inputElem.value.trim() : '';
        if (!newVal) return;
        console.log(`[Popup] Modifica editore id=${id} → "${newVal}"`);
        window.location.href = `/api/updatePublisher?editionId=${id}&publisherName=${encodeURIComponent(newVal)}`;
    };

    popup.classList.remove('none');
    document.body.style.overflow = 'hidden';
}

/**
 * Apre il popup per modificare la sola categoria di un libro/edizione.
 * @param {string} id - ID del libro/edizione.
 * @param {string} currentCategory - Categoria corrente.
 */
function openEditCategoryPopup(id, currentCategory) {
    const popup = document.getElementById('genericPopup');
    const title = document.getElementById('popupTitle');
    const icon = document.getElementById('popupIcon');
    const confirmBtn = document.getElementById('popupConfirmBtn');
    const panel = document.getElementById('editCategoryOnlyContent');

    if (!popup || !title || !icon || !confirmBtn || !panel) return;

    const footer = document.querySelector('.popup-footer');
    if (footer) footer.classList.remove('none');

    hideAllPanels();

    title.innerText = 'Modifica Categoria';
    icon.className = 'fa-solid fa-tag text-white font-size-medium';
    icon.parentElement.classList.remove('icon-bg-success');
    icon.parentElement.classList.add('icon-box-accent');

    const currentElem = document.getElementById('editCategoryOnlyCurrent');
    const inputElem = document.getElementById('editCategoryOnlyInput');
    if (currentElem) currentElem.innerText = currentCategory;
    if (inputElem) inputElem.value = currentCategory;

    panel.classList.remove('none');

    // Reset dropdown suggerimenti
    const dropdown = document.getElementById('categorySuggestionsDropdown');
    if (dropdown) dropdown.style.display = 'none';

    const cancelBtn = document.querySelector('.btn-link-action');
    if (cancelBtn) cancelBtn.classList.remove('none');

    confirmBtn.innerText = 'Salva Categoria';
    confirmBtn.style.background = '';
    confirmBtn.style.borderColor = '';
    confirmBtn.onclick = () => {
        const newVal = inputElem ? inputElem.value.trim() : '';
        if (!newVal) return;
        console.log(`[Popup] Modifica categoria id=${id} → "${newVal}"`);
        window.location.href = `/api/updateCategory?editionId=${id}&categoryName=${encodeURIComponent(newVal)}`;
    };

    popup.classList.remove('none');
    document.body.style.overflow = 'hidden';
}

// ─── AUTOCOMPLETE CATEGORIA ────────────────────────────────────────────────────

/**
 * Legge le categorie dal DOM (iniettate da Thymeleaf in #categoryDataSource)
 * e restituisce un array di stringhe.
 * @returns {string[]}
 */
function getCategoryList() {
    const items = document.querySelectorAll('#categoryDataSource li');
    return Array.from(items).map(li => li.textContent.trim()).filter(Boolean);
}

/**
 * Filtra le categorie esistenti in base al testo digitato e mostra
 * il dropdown dei suggerimenti. Chiamata sull'evento oninput dell'input.
 * @param {string} query - Testo corrente nell'input.
 */
function filterCategorySuggestions(query) {
    const dropdown = document.getElementById('categorySuggestionsDropdown');
    const input    = document.getElementById('editCategoryOnlyInput');
    if (!dropdown || !input) return;

    const q = query.trim().toLowerCase();

    if (!q) {
        dropdown.style.display = 'none';
        dropdown.innerHTML = '';
        return;
    }

    const all     = getCategoryList();
    const matches = all.filter(c => c.toLowerCase().includes(q));

    if (matches.length === 0) {
        dropdown.style.display = 'none';
        dropdown.innerHTML = '';
        return;
    }

    // Costruisce le voci del dropdown
    dropdown.innerHTML = matches.map(cat => {
        // Evidenzia la parte che corrisponde alla query
        const re      = new RegExp(`(${q.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')})`, 'gi');
        const highlighted = cat.replace(re, '<mark style="background:rgba(245,166,35,0.3);color:inherit;border-radius:3px;padding:0 2px;">$1</mark>');
        return `
            <div class="category-suggestion-item"
                 style="padding:10px 16px; cursor:pointer; font-size:0.85rem; font-weight:600;
                        transition:background 0.2s ease; border-bottom:1px solid var(--color-border);"
                 onmousedown="selectCategorySuggestion('${cat.replace(/'/g, "\\'")}')"
                 onmouseover="this.style.background='var(--color-surface-2)'"
                 onmouseout="this.style.background=''">${highlighted}</div>`;
    }).join('');

    dropdown.style.display = 'block';
}

/**
 * Seleziona un suggerimento: riempie l'input e chiude il dropdown.
 * Usa onmousedown invece di onclick per evitare che il blur dell'input
 * nasconda il dropdown prima del click.
 * @param {string} value - Il nome della categoria selezionata.
 */
function selectCategorySuggestion(value) {
    const input    = document.getElementById('editCategoryOnlyInput');
    const dropdown = document.getElementById('categorySuggestionsDropdown');
    if (input)    input.value = value;
    if (dropdown) { dropdown.style.display = 'none'; dropdown.innerHTML = ''; }
}

// Chiude il dropdown se si clicca fuori dall'input/dropdown
document.addEventListener('click', (e) => {
    // Categoria
    const catDrop  = document.getElementById('categorySuggestionsDropdown');
    const catInput = document.getElementById('editCategoryOnlyInput');
    if (catDrop && catInput && !catInput.contains(e.target) && !catDrop.contains(e.target)) {
        catDrop.style.display = 'none'; catDrop.innerHTML = '';
    }
    // Titolo
    const titleDrop  = document.getElementById('titleSuggestionsDropdown');
    const titleInput = document.getElementById('editTitleOnlyInput');
    if (titleDrop && titleInput && !titleInput.contains(e.target) && !titleDrop.contains(e.target)) {
        titleDrop.style.display = 'none'; titleDrop.innerHTML = '';
    }
    // Autore
    const authDrop  = document.getElementById('authorSuggestionsDropdown');
    const authInput = document.getElementById('editAuthorSearchInput');
    if (authDrop && authInput && !authInput.contains(e.target) && !authDrop.contains(e.target)) {
        authDrop.style.display = 'none'; authDrop.innerHTML = '';
    }
    // Editore
    const pubDrop  = document.getElementById('publisherSuggestionsDropdown');
    const pubInput = document.getElementById('editPublisherOnlyInput');
    if (pubDrop && pubInput && !pubInput.contains(e.target) && !pubDrop.contains(e.target)) {
        pubDrop.style.display = 'none'; pubDrop.innerHTML = '';
    }
});

// ─── AUTOCOMPLETE TITOLO ───────────────────────────────────────────────────────

/**
 * Legge i titoli dal DOM (books + editions iniettati da Thymeleaf).
 * @returns {string[]}
 */
function getTitleList() {
    const items = document.querySelectorAll('#titleDataSource li');
    return [...new Set(Array.from(items).map(li => li.textContent.trim()).filter(Boolean))];
}

/**
 * Filtra i titoli e mostra il dropdown dei suggerimenti.
 * @param {string} query
 */
function filterTitleSuggestions(query) {
    const dropdown = document.getElementById('titleSuggestionsDropdown');
    const input    = document.getElementById('editTitleOnlyInput');
    if (!dropdown || !input) return;

    const q = query.trim().toLowerCase();
    if (!q) { dropdown.style.display = 'none'; dropdown.innerHTML = ''; return; }

    const matches = getTitleList().filter(t => t.toLowerCase().includes(q));
    if (!matches.length) { dropdown.style.display = 'none'; dropdown.innerHTML = ''; return; }

    const re = new RegExp(`(${q.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')})`, 'gi');
    dropdown.innerHTML = matches.map(t => {
        const hl = t.replace(re, '<mark style="background:rgba(245,166,35,0.3);color:inherit;border-radius:3px;padding:0 2px;">$1</mark>');
        return `<div style="padding:10px 16px;cursor:pointer;font-size:0.85rem;font-weight:600;
                    transition:background 0.2s;border-bottom:1px solid var(--color-border);"
             onmousedown="selectTitleSuggestion('${t.replace(/'/g, "\\'")}')"
             onmouseover="this.style.background='var(--color-surface-2)'"
             onmouseout="this.style.background=''">${hl}</div>`;
    }).join('');
    dropdown.style.display = 'block';
}

/**
 * Riempie l'input titolo con il valore selezionato e chiude il dropdown.
 * @param {string} value
 */
function selectTitleSuggestion(value) {
    const input    = document.getElementById('editTitleOnlyInput');
    const dropdown = document.getElementById('titleSuggestionsDropdown');
    if (input)    input.value = value;
    if (dropdown) { dropdown.style.display = 'none'; dropdown.innerHTML = ''; }
}

// ─── AUTOCOMPLETE AUTORE ───────────────────────────────────────────────────────

/**
 * Legge gli autori dal DOM (${authors} iniettati da Thymeleaf).
 * @returns {{first:string, last:string, full:string}[]}
 */
function getAuthorList() {
    const items = document.querySelectorAll('#authorDataSource li');
    return Array.from(items).map(li => ({
        first: li.dataset.first || '',
        last:  li.dataset.last  || '',
        full:  li.textContent.trim()
    })).filter(a => a.full);
}

/**
 * Filtra gli autori e mostra il dropdown dei suggerimenti.
 * @param {string} query
 */
function filterAuthorSuggestions(query) {
    const dropdown = document.getElementById('authorSuggestionsDropdown');
    const input    = document.getElementById('editAuthorSearchInput');
    if (!dropdown || !input) return;

    const q = query.trim().toLowerCase();
    if (!q) { dropdown.style.display = 'none'; dropdown.innerHTML = ''; return; }

    const matches = getAuthorList().filter(a => a.full.toLowerCase().includes(q));
    if (!matches.length) { dropdown.style.display = 'none'; dropdown.innerHTML = ''; return; }

    const re = new RegExp(`(${q.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')})`, 'gi');
    dropdown.innerHTML = matches.map(a => {
        const hl = a.full.replace(re, '<mark style="background:rgba(245,166,35,0.3);color:inherit;border-radius:3px;padding:0 2px;">$1</mark>');
        return `<div style="padding:10px 16px;cursor:pointer;font-size:0.85rem;font-weight:600;
                    transition:background 0.2s;border-bottom:1px solid var(--color-border);"
             onmousedown="selectAuthorSuggestion('${a.first.replace(/'/g, "\\'").replace(/"/g, '&quot;')}', '${a.last.replace(/'/g, "\\'").replace(/"/g, '&quot;')}')"
             onmouseover="this.style.background='var(--color-surface-2)'"
             onmouseout="this.style.background=''">${hl}</div>`;
    }).join('');
    dropdown.style.display = 'block';
}

/**
 * Riempie i campi nome e cognome con l'autore selezionato e chiude il dropdown.
 * @param {string} first - Nome
 * @param {string} last  - Cognome
 */
function selectAuthorSuggestion(first, last) {
    const firstInput = document.getElementById('editAuthorFirstNameInput');
    const lastInput  = document.getElementById('editAuthorLastNameInput');
    const searchInput = document.getElementById('editAuthorSearchInput');
    const dropdown   = document.getElementById('authorSuggestionsDropdown');
    if (firstInput)  firstInput.value  = first;
    if (lastInput)   lastInput.value   = last;
    if (searchInput) searchInput.value = `${first} ${last}`;
    if (dropdown)    { dropdown.style.display = 'none'; dropdown.innerHTML = ''; }
}

// ─── AUTOCOMPLETE EDITORE ──────────────────────────────────────────────────────

/**
 * Legge gli editori dal DOM (${publishers} iniettati da Thymeleaf).
 * @returns {string[]}
 */
function getPublisherList() {
    const items = document.querySelectorAll('#publisherDataSource li');
    return Array.from(items).map(li => li.textContent.trim()).filter(Boolean);
}

/**
 * Filtra gli editori esistenti in base al testo digitato e mostra il dropdown.
 * @param {string} query
 */
function filterPublisherSuggestions(query) {
    const dropdown = document.getElementById('publisherSuggestionsDropdown');
    const input    = document.getElementById('editPublisherOnlyInput');
    if (!dropdown || !input) return;

    const q = query.trim().toLowerCase();
    if (!q) { dropdown.style.display = 'none'; dropdown.innerHTML = ''; return; }

    const matches = getPublisherList().filter(p => p.toLowerCase().includes(q));
    if (!matches.length) { dropdown.style.display = 'none'; dropdown.innerHTML = ''; return; }

    const re = new RegExp(`(${q.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')})`, 'gi');
    dropdown.innerHTML = matches.map(p => {
        const hl = p.replace(re, '<mark style="background:rgba(245,166,35,0.3);color:inherit;border-radius:3px;padding:0 2px;">$1</mark>');
        return `<div style="padding:10px 16px;cursor:pointer;font-size:0.85rem;font-weight:600;
                    transition:background 0.2s;border-bottom:1px solid var(--color-border);"
             onmousedown="selectPublisherSuggestion('${p.replace(/'/g, "\\'")}')"
             onmouseover="this.style.background='var(--color-surface-2)'"
             onmouseout="this.style.background=''">${hl}</div>`;
    }).join('');
    dropdown.style.display = 'block';
}

/**
 * Riempie l'input editore con il valore selezionato e chiude il dropdown.
 * @param {string} value
 */
function selectPublisherSuggestion(value) {
    const input    = document.getElementById('editPublisherOnlyInput');
    const dropdown = document.getElementById('publisherSuggestionsDropdown');
    if (input)    input.value = value;
    if (dropdown) { dropdown.style.display = 'none'; dropdown.innerHTML = ''; }
}

// ─── ADD USER POPUP — HELPERS ──────────────────────────────────────────────────

/**
 * Alterna la visibilità del campo password nel popup "Aggiungi Dipendente".
 */
function toggleAddUserPassword() {
    const input = document.getElementById('addUserPasswordInput');
    const icon  = document.getElementById('addUserPasswordToggleIcon');
    if (!input) return;
    if (input.type === 'password') {
        input.type = 'text';
        if (icon) { icon.classList.remove('fa-eye'); icon.classList.add('fa-eye-slash'); }
    } else {
        input.type = 'password';
        if (icon) { icon.classList.remove('fa-eye-slash'); icon.classList.add('fa-eye'); }
    }
}

/**
 * Aggiorna in tempo reale l'anteprima dell'email aziendale
 * in base a nome e cognome inseriti nel form aggiungi dipendente.
 */
function updateAddUserEmailPreview() {
    const nameInput = document.getElementById('addUserNameInput');
    const lastInput = document.getElementById('addUserLastNameInput');
    const preview   = document.getElementById('addUserEmailPreview');
    if (!preview) return;

    const name = nameInput ? nameInput.value.trim().toLowerCase().replace(/\s+/g, '') : '';
    const last = lastInput ? lastInput.value.trim().toLowerCase().replace(/\s+/g, '') : '';

    if (name || last) {
        preview.textContent = `${name || 'nome'}.${last || 'cognome'}@biblioteca.it`;
    } else {
        preview.textContent = 'nome.cognome@biblioteca.it';
    }
}

function updateRoleSelection() {
    const userOption  = document.getElementById('roleOptionUser');
    const adminOption = document.getElementById('roleOptionAdmin');
    const userRadio   = userOption  ? userOption.querySelector('input[type="radio"]')  : null;
    const adminRadio  = adminOption ? adminOption.querySelector('input[type="radio"]') : null;

    if (userOption && userRadio)
        userOption.classList.toggle('role-option--selected', userRadio.checked);
    if (adminOption && adminRadio)
        adminOption.classList.toggle('role-option--selected', adminRadio.checked);
}


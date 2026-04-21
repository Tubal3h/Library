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

    // Nasconde tutti gli altri pannelli
    ['editBookContent', 'addCopyContent', 'addEditionSuccessContent', 'deleteBookContent', 'confirmContent', 'errorContent'].forEach(id => {
        const el = document.getElementById(id);
        if (el) el.classList.add('none');
    });

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

        const addCopyNameElem = document.getElementById('addCopyBookName');
        if (addCopyNameElem) addCopyNameElem.innerText = titleTxt;

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
 * Chiude il popup modale e ripristina lo scroll della pagina.
 */
function closePopup() {
    const popup = document.getElementById('genericPopup');
    if (popup) {
        popup.classList.add('none');
        document.body.style.overflow = '';
        
        // Reset filtro eliminati se presente
        const toggle = document.getElementById('showDeletedToggle');
        if (toggle) toggle.checked = false;
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
 * Helper per avviare la conferma eliminazione leggendo i dati dall'elemento HTML.
 * @param {HTMLElement} element - Il bottone cliccato.
 */
function triggerConfirmDelete(element) {
    const title = element.getAttribute('data-title');
    const url = element.getAttribute('data-url');
    openConfirmPopup('delete', title, 'Sei sicuro di voler eliminare questa copia fisica dal catalogo?', url);
}

/**
 * Helper per avviare la conferma aggiunta copia leggendo i dati dall'elemento HTML.
 * @param {HTMLElement} element - Il bottone cliccato.
 */
function triggerConfirmAdd(element) {
    const title = element.getAttribute('data-title');
    const url = element.getAttribute('data-url');
    openConfirmPopup('add', title, 'Vuoi aggiungere una nuova copia fisica per questa edizione?', url);
}

/**
 * Helper per avviare la conferma consegna leggendo i dati dall'elemento HTML.
 * @param {HTMLElement} element - Il bottone cliccato.
 */
function triggerConfirmDelivered(element) {
    const title = element.getAttribute('data-title');
    const url = element.getAttribute('data-url');
    openConfirmPopup('delivered', title, 'Sei sicuro di voler consegnare questo libro?', url);
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

    // Nasconde tutti gli altri pannelli
    ['editBookContent', 'addCopyContent', 'addEditionSuccessContent', 'deleteBookContent', 'errorContent', 'addEditionContent', 'deliveredRentContent', 'viewBooksEditionContent'].forEach(id => {
        const el = document.getElementById(id);
        if (el) el.classList.add('none');
    });

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

    if (confirmTitleElem) confirmTitleElem.innerText = 'Sei sicuro?';
    if (confirmMsgElem) confirmMsgElem.innerText = message;
    
    if (confirmDetailsElem) {
        confirmDetailsElem.innerText = titleTxt;
        confirmDetailsElem.style.display = titleTxt ? 'block' : 'none';
    }

    // Design specifico per azione
    if (action === 'delete') {
        confirmBtn.innerText = 'Sì, elimina';
        confirmBtn.style.setProperty('background', 'var(--color-error)', 'important');
        confirmBtn.style.setProperty('border-color', 'var(--color-error)', 'important');
        if (confirmIconBox) {
            confirmIconBox.style.background = 'rgba(198, 40, 40, 0.1)';
            confirmIconBox.style.color = 'var(--color-error)';
        }
        if (confirmIconInner) confirmIconInner.className = 'fa-solid fa-trash-can';
    } else {
        confirmBtn.innerText = 'Sì, procedi';
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

    // Nasconde tutti gli altri pannelli
    ['editBookContent', 'addCopyContent', 'addEditionSuccessContent', 'deleteBookContent', 'confirmContent', 'errorContent'].forEach(id => {
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

// Variabili globali per gestire il refresh del popup copie
let currentViewEditionId = null;
let currentViewEditionTitle = null;

/**
 * Gestisce il cambio di stato del filtro eliminati e ricarica i dati.
 */
function toggleDeletedFilter() {
    const toggle = document.getElementById('showDeletedToggle');
    if (toggle) {
        // Se il click è avvenuto sul contenitore (non sul checkbox direttamente via click event del checkbox)
        // ma toggleDeletedFilter è chiamato dall'onclick del div, allora invertiamo manualmente se serve.
        // In realtà l'onclick sul div triggera anche se clicchi l'input.
        // Per semplicità, ricarichiamo semplicemente chiamando openViewBooksPopup con i dati correnti.
        openViewBooksPopup(null, currentViewEditionId, currentViewEditionTitle);
    }
}

/**
 * Apre il popup per visualizzare l'elenco dei libri (copie) di un'edizione.
 * Carica i dati tramite una chiamata REST e popola la tabella.
 * 
 * @param {HTMLElement|null} element - L'elemento (bottone) cliccato (null se chiamato per refresh).
 * @param {number|null} id - ID manuale per refresh.
 * @param {string|null} titleText - Titolo manuale per refresh.
 */
function openViewBooksPopup(element, id = null, titleText = null) {
    const editionId = element ? element.getAttribute('data-id') : id;
    const editionTitle = element ? element.getAttribute('data-title') : titleText;
    
    // Salviamo i dati per eventuali refresh (cambio filtro)
    currentViewEditionId = editionId;
    currentViewEditionTitle = editionTitle;

    const popup = document.getElementById('genericPopup');
    const title = document.getElementById('popupTitle');
    const icon = document.getElementById('popupIcon');
    const confirmBtn = document.getElementById('popupConfirmBtn');
    const viewContent = document.getElementById('viewBooksEditionContent');
    const listContainer = document.getElementById('viewBooksListContainer');
    const editionTitleElem = document.getElementById('viewBooksEditionTitle');
    const showDeletedToggle = document.getElementById('showDeletedToggle');

    if (!popup || !title || !icon || !confirmBtn || !viewContent || !listContainer || !editionTitleElem) return;

    const includeDeleted = showDeletedToggle ? showDeletedToggle.checked : false;

    // Nasconde tutti gli altri pannelli
    ['editBookContent', 'addCopyContent', 'addEditionSuccessContent', 'deleteBookContent', 'confirmContent', 'errorContent', 'addEditionContent'].forEach(id => {
        const el = document.getElementById(id);
        if (el) el.classList.add('none');
    });

    // Configurazione Header
    title.innerText = 'Visualizza Copie';
    icon.className = 'fa-solid fa-eye text-white';
    icon.parentElement.classList.remove('icon-bg-success');
    icon.parentElement.classList.add('icon-box-accent');

    // Imposta Titolo Edizione
    editionTitleElem.innerText = editionTitle;

    // Svuota lista e mostra caricamento
    listContainer.innerHTML = '<tr><td colspan="3" class="py-4 text-center opacity-50 italic">Caricamento in corso...</td></tr>';

    viewContent.classList.remove('none');

    // Configurazione Bottone (Semplice chiusura)
    confirmBtn.innerText = 'Chiudi';
    confirmBtn.style.background = '';
    confirmBtn.style.borderColor = '';
    confirmBtn.onclick = () => closePopup();

    // Nasconde pulsante Annulla (non serve qui)
    const cancelBtn = document.querySelector('.btn-link-action');
    if (cancelBtn) cancelBtn.classList.add('none');

    // Recupero dati tramite API
    fetch(`/api/books/edition/${editionId}?includeDeleted=${includeDeleted}`)
        .then(response => {
            if (!response.ok) throw new Error('Errore nel recupero dei dati');
            return response.json();
        })
        .then(books => {
            listContainer.innerHTML = '';
            
            // Variabile per contare quanti elementi passano il filtro
            let count = 0;

            books.forEach(book => {
                // Filtraggio lato client per "mostrare solo quelli eliminati" se la spunta è attiva
                if (includeDeleted) {
                    if (book.status !== 'eliminato') return;
                } else {
                    if (book.status === 'eliminato') return;
                }

                count++;
                const tr = document.createElement('tr');
                tr.className = 'border-b hover-bg-light transition';
                if (book.status === 'eliminato') tr.style.opacity = '0.6';
                
                // Badge Stato
                let statusClass = 'status-vibrant-pending';
                let statusLabel = book.status;

                if (book.status === 'disponibilita') {
                    statusClass = 'status-vibrant-active';
                    statusLabel = 'Disponibile';
                } else if (book.status === 'eliminato') {
                    statusClass = 'status-vibrant-error';
                    statusLabel = 'Eliminato';
                } else if (book.status === 'in prestito') {
                    statusLabel = 'In Prestito';
                }

                // Genera URL per eliminazione
                const deleteUrl = `/api/deleteBook?bookId=${book.bookId}&bookName=${encodeURIComponent(editionTitle)}`;
                
                // Condizione: Si può eliminare SOLO se è disponibile. 
                // Se è in prestito o già eliminato, il tasto non deve apparire.
                const canDelete = book.status === 'disponibilita';
                
                const deleteBtnHtml = canDelete ? `
                    <button type="button" 
                            class="btn-modern-action pointer transition shadow-soft radius-12"
                            style="width: 38px; height: 38px;"
                            data-title="${editionTitle} (Copia #${book.bookId})" 
                            data-url="${deleteUrl}"
                            onclick="triggerConfirmDelete(this)"
                            title="Elimina Copia">
                        <i class="fa-solid fa-trash-can"></i>
                    </button>
                ` : `<span class="text-xs opacity-50 italic">Azione non permessa</span>`;

                tr.innerHTML = `
                    <td class="py-3 font-mono fw-600 text-brand">#${book.bookId}</td>
                    <td class="py-3">
                        <div class="${statusClass}" style="display: inline-block;">
                            <div class="glass-indicator radius-full px-3 py-1 font-size-xs fw-700 uppercase tracking-widest">
                                ${statusLabel}
                            </div>
                        </div>
                    </td>
                    <td class="py-3 text-right">
                        ${deleteBtnHtml}
                    </td>
                `;
                listContainer.appendChild(tr);
            });

            // Se dopo il filtraggio non ci sono elementi
            if (count === 0) {
                const emptyMessage = includeDeleted 
                    ? "Non ci sono libri eliminati." 
                    : "Nessuna copia attiva trovata per questa edizione.";
                listContainer.innerHTML = `<tr><td colspan="3" class="py-8 text-center opacity-50 italic">${emptyMessage}</td></tr>`;
            }
        })
        .catch(error => {
            console.error('Error fetching books:', error);
            listContainer.innerHTML = `<tr><td colspan="3" class="py-4 text-center text-error fw-600">Errore: ${error.message}</td></tr>`;
        });

    // Mostra il popup
    popup.classList.remove('none');
    document.body.style.overflow = 'hidden';
}

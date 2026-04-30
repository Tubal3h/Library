/**
 * successHandlers.js
 * Gestisce i feedback di successo, errori e conferme.
 */

function initSuccessPopup(type, data) {
    const config = {
        title: type === 'error' ? 'Errore' : 'Operazione Riuscita',
        icon: type === 'error' ? 'fa-circle-exclamation' : 'fa-circle-check',
        iconClass: type === 'error' ? 'icon-bg-error' : 'icon-bg-success',
        confirmText: 'Ottimo',
        showCancel: false,
        onConfirm: () => Popup.close()
    };

    const map = {
        addEdition: {
            id: 'addEditionSuccessContent', fn: () => {
                document.getElementById('addEditionSuccessBookTitle').innerText = data.title || "";
                document.getElementById('addEditionSuccessBookIsbn').innerText = data.isbn || "";
            }
        },
        addCopy: {
            id: 'addCopyContent', fn: () => {
                document.getElementById('addCopyBookTitle').innerText = data.title || "";
                document.getElementById('addCopyBookId').innerText = data.id || "";
            }
        },
        addAuthor: {
            id: 'addAuthorSuccessContent', fn: () => {
                document.getElementById('addAuthorSuccessName').innerText = data.authorName || "";
                document.getElementById('addAuthorSuccessLastName').innerText = data.authorLastName || "";
            }
        },
        addPublisher: { id: 'addPublisherSuccessContent', idField: 'addPublisherSuccessName', dataField: 'publisherName' },
        addBookName: { id: 'addTitleSuccessContent', idField: 'addTitleSuccessName', dataField: 'title' },
        addCategory: { id: 'addCategorySuccessContent', idField: 'addCategorySuccessName', dataField: 'categoryName' },
        updateTitle: { id: 'updateTitleSuccessContent', idField: 'updateTitleBookName', dataField: 'title' },
        updateAuthor: {
            id: 'updateAuthorSuccessContent', fn: () => {
                document.getElementById('updateAuthorName').innerText = data.authorName || "";
                document.getElementById('updateAuthorLastName').innerText = data.authorLastName || "";
            }
        },
        updatePublisher: { id: 'updatePublisherSuccessContent', idField: 'updatePublisherName', dataField: 'publisherName' },
        updateCategory: { id: 'updateCategorySuccessContent', idField: 'updateCategoryName', dataField: 'categoryName' },
        deleteBook: {
            id: 'deleteBookContent', fn: () => {
                document.getElementById('deleteBookTitle').innerText = data.title || "";
                document.getElementById('deleteBookId').innerText = data.id || "";
            }
        },
        deliveredRent: { id: 'deliveredRentContent', idField: 'deliveredBookTitle', dataField: 'title' },
        booked: { id: 'bookedRentContent', idField: 'bookedBookTitle', dataField: 'popupBookTitle' },
        error: { id: 'errorContent', fn: () => document.getElementById('errorMessage').innerText = data.errorMessage || "Errore sconosciuto." }
    };

    const item = map[type];
    if (item) {
        config.panelId = item.id;
        Popup.open(config);
        if (item.fn) item.fn();
        else if (item.idField) document.getElementById(item.idField).innerText = data[item.dataField] || "";
    }
}

function openConfirmPopup(action, titleTxt, message, confirmUrl, method = 'GET') {
    const isDelete = action === 'delete';

    Popup.open({
        title: 'Richiesta Conferma',
        icon: isDelete ? 'fa-trash-can' : 'fa-circle-question',
        panelId: 'confirmContent',
        confirmText: isDelete ? 'Elimina' : 'Procedi',
        onConfirm: () => {
            if (typeof confirmUrl === 'function') return confirmUrl();
            if (method.toUpperCase() === 'POST') {
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = confirmUrl;
                document.body.appendChild(form);
                form.submit();
            } else {
                window.location.href = confirmUrl;
            }
        }
    });

    if (isDelete) {
        const btn = document.getElementById('popupConfirmBtn');
        if (btn) {
            btn.style.setProperty('background', 'var(--color-error)', 'important');
            btn.style.setProperty('border-color', 'var(--color-error)', 'important');
        }
    }

    document.getElementById('confirmTitle').innerText = 'Vuoi proseguire?';
    document.getElementById('confirmMessage').innerText = message;
    const details = document.getElementById('confirmDetails');
    if (details) {
        details.innerText = titleTxt;
        details.style.display = titleTxt ? 'block' : 'none';
    }
}

// Trigger veloci
function triggerConfirmDelete(el) { openConfirmPopup('delete', el.dataset.title, 'Questa copia verrà eliminata dalla libreria', el.dataset.url); }
function triggerConfirmDeleteUser(el) { openConfirmPopup('delete', el.dataset.title, 'Il seguente dipendente verrà eliminato dal sistema', el.dataset.url); }
function triggerConfirmAdd(el) { openConfirmPopup('add', el.dataset.title, 'Si aggiungerà una nuova copia per questa edizione', el.dataset.url); }
function triggerConfirmDelivered(el) { openConfirmPopup('delivered', el.dataset.title, 'Il manuale verrà reso disponibile', el.dataset.url); }
function triggerConfirmReturned(el) { openConfirmPopup('returned', el.dataset.title, 'Il manuale verrà segnato come restituito', el.dataset.url); }
function triggerConfirmRent(el) { openConfirmPopup('rent', el.dataset.title, 'Vuoi noleggiare questo manuale?', el.dataset.url); }

// Esponi globalmente
window.initSuccessPopup = initSuccessPopup;
window.openConfirmPopup = openConfirmPopup;
window.triggerConfirmDelete = triggerConfirmDelete;
window.triggerConfirmDeleteUser = triggerConfirmDeleteUser;
window.triggerConfirmAdd = triggerConfirmAdd;
window.triggerConfirmDelivered = triggerConfirmDelivered;
window.triggerConfirmReturned = triggerConfirmReturned;
window.triggerConfirmRent = triggerConfirmRent;

console.log("successHandlers.js loaded");
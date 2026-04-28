/**
 * addHandlers.js
 * Gestisce l'aggiunta di nuove entità (Libri, Autori, Editori, Categorie, Edizioni).
 */

function openAddEntityPopup(type, config) {
    Popup.open({
        title: `Nuovo ${type}`,
        icon: config.icon,
        panelId: config.panelId,
        confirmText: 'Aggiungi',
        onConfirm: () => {
            const form = document.getElementById(config.formId);
            if (form && form.checkValidity()) {
                const val = form.querySelector('input').value;
                openConfirmPopup('add', val, `Vuoi aggiungere questo nuovo ${type.toLowerCase()}?`, () => form.submit());
            } else if (form) form.reportValidity();
        }
    });
}

function openAddTitlePopup() { openAddEntityPopup('Titolo', { icon: 'fa-plus', panelId: 'addTitleContent', formId: 'addTitleForm' }); }
function openAddPublisherPopup() { openAddEntityPopup('Editore', { icon: 'fa-building-columns', panelId: 'addPublisherContent', formId: 'addPublisherForm' }); }
function openAddCategoryPopup() { openAddEntityPopup('Categoria', { icon: 'fa-tag', panelId: 'addCategoryContent', formId: 'addCategoryForm' }); }

function openAddAuthorPopup() { 
    openAddEntityPopup('Autore', { icon: 'fa-user-plus', panelId: 'addAuthorContent', formId: 'addAuthorForm' }); 
    const btn = document.getElementById('popupConfirmBtn');
    if (btn) btn.onclick = () => {
        const form = document.getElementById('addAuthorForm');
        if (form && form.checkValidity()) {
            const n = form.querySelector('input[name="authorName"]').value;
            const l = form.querySelector('input[name="authorLastName"]').value;
            openConfirmPopup('add', `${n} ${l}`, 'Vuoi aggiungere questo autore?', () => form.submit());
        } else if (form) form.reportValidity();
    };
}

function openAddEditionPopup() {
    Popup.open({
        title: 'Aggiungi Edizione', icon: 'fa-plus', panelId: 'addEditionContent',
        confirmText: 'Aggiungi Edizione',
        size: 'large',
        onConfirm: () => {
            const form = document.getElementById('addEditionForm');
            if (form) form.submit();
        }
    });
}

// Inizializzazione Autocomplete per Edizioni
document.addEventListener('DOMContentLoaded', () => {
    if (typeof Autocomplete !== 'undefined') {
        Autocomplete.init('addEditionTitleInput', 'addEditionTitleSuggestions', 'titleDataSource', 'selectAddEditionTitle');
        Autocomplete.init('addEditionAuthorNameInput', 'addEditionAuthorSuggestions', 'authorDataSource', 'selectAddEditionAuthor');
        Autocomplete.init('addEditionPublisherInput', 'addEditionPublisherSuggestions', 'publisherDataSource', 'selectAddEditionPublisher');
    }
});

function selectAddEditionTitle(v) { document.getElementById('addEditionTitleInput').value = v; Autocomplete.hide(document.getElementById('addEditionTitleSuggestions')); }
function selectAddEditionPublisher(v) { document.getElementById('addEditionPublisherInput').value = v; Autocomplete.hide(document.getElementById('addEditionPublisherSuggestions')); }
function selectAddEditionAuthor(f, l) {
    document.getElementById('addEditionAuthorNameInput').value = f;
    document.getElementById('addEditionAuthorLastNameInput').value = l;
    Autocomplete.hide(document.getElementById('addEditionAuthorSuggestions'));
}

// Esponi globalmente
window.openAddTitlePopup = openAddTitlePopup;
window.openAddPublisherPopup = openAddPublisherPopup;
window.openAddCategoryPopup = openAddCategoryPopup;
window.openAddAuthorPopup = openAddAuthorPopup;
window.openAddEditionPopup = openAddEditionPopup;
window.selectAddEditionTitle = selectAddEditionTitle;
window.selectAddEditionPublisher = selectAddEditionPublisher;
window.selectAddEditionAuthor = selectAddEditionAuthor;

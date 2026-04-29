/**
 * updateHandlers.js
 * Gestisce la modifica diretta delle entità base (Settings).
 */

function openEditEntityFullPopup(type, config) {
    Popup.open({
        title: config.title,
        icon: 'fa-pen-to-square',
        panelId: config.panelId,
        confirmText: 'Salva',
        onConfirm: () => {
            const form = document.getElementById(config.formId);
            if (form && form.checkValidity()) {
                openConfirmPopup('edit', config.entityName, `Vuoi salvare le modifiche a questo ${type}?`, () => form.submit());
            } else if (form) form.reportValidity();
        }
    });
}

/** Gestione conferma modifica entità (Settings) */
function openEditBookNamePopup(id, val) {
    openEditEntityFullPopup('Titolo', { title: 'Modifica...', panelId: 'updateTitleContent', formId: 'updateTitleForm', entityName: val });
    document.getElementById('updateTitleId').value = id;
    document.getElementById('updateTitleInput').value = val;
}

function openEditAuthorFullPopup(id, n, l) {
    openEditEntityFullPopup('Autore', { title: 'Modifica...', panelId: 'updateAuthorContent', formId: 'updateAuthorForm', entityName: `${n} ${l}` });
    document.getElementById('updateAuthorId').value = id;
    document.getElementById('updateAuthorNameInput').value = n;
    document.getElementById('updateAuthorLastNameInput').value = l;
}

function openEditPublisherFullPopup(id, val) {
    openEditEntityFullPopup('Editore', { title: 'Modifica...', panelId: 'updatePublisherContent', formId: 'updatePublisherForm', entityName: val });
    document.getElementById('updatePublisherId').value = id;
    document.getElementById('updatePublisherInput').value = val;
}

function openEditCategoryFullPopup(id, val) {
    openEditEntityFullPopup('Categoria', { title: 'Modifica...', panelId: 'updateCategoryContent', formId: 'updateCategoryForm', entityName: val });
    document.getElementById('updateCategoryId').value = id;
    document.getElementById('updateCategoryInput').value = val;
}

/** Gestione conferma eliminazione entità (Settings) [DA IMPLEMENTARE!!]*/
function triggerConfirmDeleteBookName(el) { 
    openConfirmPopup('delete', el.dataset.title, 'Verrano eliminate tutte le copie del manuale', `/api/deleteBookName?bookNameId=${el.dataset.id}`);

}

function triggerConfirmDeleteAuthor(el) {
    openConfirmPopup('delete', el.dataset.title, 'L\'autore verrà eliminato e tutti i manuali correlati!', `/api/deleteAuthor?authorId=${el.dataset.id}`);

}

function triggerConfirmDeletePublisher(el) {
    openConfirmPopup('delete', el.dataset.title, 'L\'editore verrà eliminato e tutti i manuali correlati!', `/api/deletePublisher?publisherId=${el.dataset.id}`);

}

function triggerConfirmDeleteCategory(el) {
    openConfirmPopup('delete', el.dataset.title, 'La categoria verrà eliminata e tutti i manuali correlati!', `/api/deleteCategory?categoryId=${el.dataset.id}`);

}

// Esponi globalmente
window.openEditBookNamePopup = openEditBookNamePopup;
window.openEditAuthorFullPopup = openEditAuthorFullPopup;
window.openEditPublisherFullPopup = openEditPublisherFullPopup;
window.openEditCategoryFullPopup = openEditCategoryFullPopup;
window.triggerConfirmDeleteBookName = triggerConfirmDeleteBookName;
window.triggerConfirmDeleteAuthor = triggerConfirmDeleteAuthor;
window.triggerConfirmDeletePublisher = triggerConfirmDeletePublisher;
window.triggerConfirmDeleteCategory = triggerConfirmDeleteCategory;

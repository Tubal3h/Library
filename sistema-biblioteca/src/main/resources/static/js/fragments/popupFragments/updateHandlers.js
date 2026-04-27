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

function openEditBookNamePopup(id, val) {
    openEditEntityFullPopup('Titolo', { title: 'Modifica Titolo Opera', panelId: 'updateTitleContent', formId: 'updateTitleForm', entityName: val });
    document.getElementById('updateTitleId').value = id;
    document.getElementById('updateTitleInput').value = val;
}

function openEditAuthorFullPopup(id, n, l) {
    openEditEntityFullPopup('Autore', { title: 'Modifica Autore', panelId: 'updateAuthorContent', formId: 'updateAuthorForm', entityName: `${n} ${l}` });
    document.getElementById('updateAuthorId').value = id;
    document.getElementById('updateAuthorNameInput').value = n;
    document.getElementById('updateAuthorLastNameInput').value = l;
}

function openEditPublisherFullPopup(id, val) {
    openEditEntityFullPopup('Editore', { title: 'Modifica Editore', panelId: 'updatePublisherContent', formId: 'updatePublisherForm', entityName: val });
    document.getElementById('updatePublisherId').value = id;
    document.getElementById('updatePublisherInput').value = val;
}

function openEditCategoryFullPopup(id, val) {
    openEditEntityFullPopup('Categoria', { title: 'Modifica Categoria', panelId: 'updateCategoryContent', formId: 'updateCategoryForm', entityName: val });
    document.getElementById('updateCategoryId').value = id;
    document.getElementById('updateCategoryInput').value = val;
}

function triggerConfirmDeleteBookName(el) { openConfirmPopup('delete', el.dataset.title, 'Verrà eliminato il titolo e tutte le sue edizioni correlate!', `/api/deleteBookName?bookNameId=${el.dataset.id}`); }
function triggerConfirmDeleteAuthor(el) { openConfirmPopup('delete', el.dataset.title, 'Verrà eliminato l\'autore e tutti i suoi libri correlati!', `/api/deleteAuthor?authorId=${el.dataset.id}`); }
function triggerConfirmDeletePublisher(el) { openConfirmPopup('delete', el.dataset.title, 'Verrà eliminato l\'editore e tutti i suoi libri correlati!', `/api/deletePublisher?publisherId=${el.dataset.id}`); }
function triggerConfirmDeleteCategory(el) { openConfirmPopup('delete', el.dataset.title, 'Verrà eliminata la categoria e tutti i suoi libri correlati!', `/api/deleteCategory?categoryId=${el.dataset.id}`); }

// Esponi globalmente
window.openEditBookNamePopup = openEditBookNamePopup;
window.openEditAuthorFullPopup = openEditAuthorFullPopup;
window.openEditPublisherFullPopup = openEditPublisherFullPopup;
window.openEditCategoryFullPopup = openEditCategoryFullPopup;
window.triggerConfirmDeleteBookName = triggerConfirmDeleteBookName;
window.triggerConfirmDeleteAuthor = triggerConfirmDeleteAuthor;
window.triggerConfirmDeletePublisher = triggerConfirmDeletePublisher;
window.triggerConfirmDeleteCategory = triggerConfirmDeleteCategory;

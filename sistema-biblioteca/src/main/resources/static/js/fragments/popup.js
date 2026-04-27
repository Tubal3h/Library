/**
 * popup.js - Core Manager
 * Gestisce l'apertura/chiusura e la configurazione base dei popup.
 */

const DOM = {
    overlay: () => document.getElementById('genericPopup'),
    title: () => document.getElementById('popupTitle'),
    icon: () => document.getElementById('popupIcon'),
    iconBox: () => document.getElementById('popupIcon')?.parentElement,
    confirmBtn: () => document.getElementById('popupConfirmBtn'),
    cancelBtn: () => document.querySelector('.btn-link-action'),
    footer: () => document.querySelector('.popup-footer'),
    body: () => document.getElementById('popupBody')
};

const PANELS = [
    'editBookContent', 'editFieldOnlyContent',
    'addTitleContent', 'addAuthorContent', 'addPublisherContent', 'addCategoryContent', 'addEditionContent',
    'updateTitleContent', 'updateAuthorContent', 'updatePublisherContent', 'updateCategoryContent',
    'addCopyContent', 'addEditionSuccessContent', 'addTitleSuccessContent', 'addAuthorSuccessContent',
    'addPublisherSuccessContent', 'addCategorySuccessContent', 'updateTitleSuccessContent',
    'updateAuthorSuccessContent', 'updatePublisherSuccessContent', 'updateCategorySuccessContent',
    'addUserSuccessContent', 'returnedRentContent', 'deliveredRentContent', 'bookedRentContent',
    'deleteBookContent', 'deleteUserContent', 'confirmContent', 'errorContent',
    'addUserContent', 'viewBooksEditionContent'
];

const Popup = {
    open(config) {
        const { title, icon, iconClass, panelId, confirmText, onConfirm, showCancel = true, footerVisible = true } = config;

        this.hideAllPanels();
        
        if (DOM.title()) DOM.title().innerText = title || 'Messaggio';
        if (DOM.icon()) {
            DOM.icon().className = `fa-solid ${icon || 'fa-info-circle'} text-white`;
            if (DOM.iconBox()) DOM.iconBox().className = `icon-box-accent ${iconClass || ''}`;
        }

        const panel = document.getElementById(panelId);
        if (panel) panel.classList.remove('none');

        if (DOM.footer()) DOM.footer().classList.toggle('none', !footerVisible);
        if (DOM.cancelBtn()) DOM.cancelBtn().classList.toggle('none', !showCancel);
        
        if (DOM.confirmBtn()) {
            DOM.confirmBtn().innerText = confirmText || 'Conferma';
            DOM.confirmBtn().style.background = '';
            DOM.confirmBtn().style.borderColor = '';
            DOM.confirmBtn().onclick = onConfirm || (() => this.close());
        }

        if (DOM.overlay()) DOM.overlay().classList.remove('none');
        document.body.style.overflow = 'hidden';
    },

    close() {
        if (DOM.overlay()) {
            DOM.overlay().classList.add('none');
            document.body.style.overflow = '';
            if (window.location.search.includes('action=viewCopies')) {
                window.location.href = '/dashboard';
            }
        }
    },

    hideAllPanels() {
        PANELS.forEach(id => {
            const el = document.getElementById(id);
            if (el) el.classList.add('none');
        });
    }
};

// Funzione di compatibilità Legacy
function openPopup(action, bookId, titleTxt, authorTxt, categoryTxt, publisherTxt) {
    if (action === 'edit') {
        Popup.open({
            title: 'Modifica Libro',
            icon: 'fa-pen-to-square',
            panelId: 'editBookContent',
            confirmText: 'Salva Cambiamenti',
            onConfirm: () => {
                console.log(`[Popup] Edit triggered for ${bookId}`);
                Popup.close();
            }
        });

        const nameElem = document.getElementById('editBookName');
        if (nameElem) nameElem.innerText = titleTxt;
        
        const titleInput = document.getElementById('editTitleInput');
        if (titleInput) titleInput.value = titleTxt;

        const selectByText = (id, text) => {
            const sel = document.getElementById(id);
            if (!sel || !text) return;
            for (let i = 0; i < sel.options.length; i++) {
                if (sel.options[i].text === text) { sel.selectedIndex = i; break; }
            }
        };

        selectByText('editAuthorInput', authorTxt);
        selectByText('editCategoryInput', categoryTxt);
        selectByText('editPublisherInput', publisherTxt);

    } else if (action === 'addCopy') {
        Popup.open({
            title: 'Operazione Riuscita',
            icon: 'fa-circle-check',
            iconClass: 'icon-bg-success',
            panelId: 'addCopyContent',
            confirmText: 'Chiudi',
            showCancel: false
        });

        document.getElementById('addCopyBookTitle').innerText = titleTxt;
        document.getElementById('addCopyBookId').innerText = bookId;
    }
}

// Supporto Autocomplete Generico
const Autocomplete = {
    init(inputId, dropdownId, dataSourceId, onSelect) {
        const input = document.getElementById(inputId);
        const dropdown = document.getElementById(dropdownId);
        if (!input || !dropdown) return;

        input.oninput = (e) => {
            const q = e.target.value.trim().toLowerCase();
            if (!q) return this.hide(dropdown);

            const items = document.querySelectorAll(`#${dataSourceId} li`);
            const matches = Array.from(items).filter(li => li.textContent.toLowerCase().includes(q));

            if (!matches.length) return this.hide(dropdown);

            dropdown.innerHTML = matches.map(li => {
                const text = li.textContent.trim();
                const hl = text.replace(new RegExp(`(${q})`, 'gi'), '<mark class="hl">$1</mark>');
                const dataArgs = dataSourceId === 'authorDataSource' ? `'${li.dataset.first}', '${li.dataset.last}'` : `'${text.replace(/'/g, "\\'")}'`;
                return `<div class="suggestion-item" onmousedown="${onSelect}(${dataArgs})">${hl}</div>`;
            }).join('');
            dropdown.classList.remove('none');
            dropdown.style.display = 'block';
        };

        document.addEventListener('click', (e) => {
            if (!input.contains(e.target) && !dropdown.contains(e.target)) this.hide(dropdown);
        });
    },
    hide(el) { 
        el.classList.add('none');
        el.style.display = 'none'; 
        el.innerHTML = ''; 
    }
};

// Esponi globalmente
window.Popup = Popup;
window.Autocomplete = Autocomplete;
window.openPopup = openPopup;
window.closePopup = () => Popup.close();
window.closePopupOnBackdrop = (e) => { if (e.target === e.currentTarget) Popup.close(); };
window.findIdByText = (srcId, txt) => {
    if (!txt) return null;
    const item = Array.from(document.querySelectorAll(`#${srcId} li`))
        .find(li => li.textContent.trim().toLowerCase() === txt.toLowerCase());
    return item ? (item.dataset.id || item.getAttribute('data-id')) : null;
};

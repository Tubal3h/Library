/**
 * utilityHandlers.js
 * Gestisce l'aggiunta di dipendenti e la visualizzazione delle copie.
 */

function openAddUserPopup() {
    Popup.open({
        title: 'Aggiungi un nuovo dipendente', icon: 'fa-user-plus', panelId: 'addUserContent',
        confirmText: 'Aggiungi Dipendente',
        onConfirm: () => {
            const form = document.getElementById('addUserForm');
            if (form && form.checkValidity()) form.submit();
            else if (form) form.reportValidity();
        }
    });
    updateAddUserEmailPreview();
    updateRoleSelection();
}

function updateAddUserEmailPreview() {
    const sanitize = (val) => val ? val.toLowerCase().replace(/[^a-z0-9àèéìòù]/g, '') : '';
    
    const n = sanitize(document.getElementById('addUserNameInput')?.value) || 'nome';
    const l = sanitize(document.getElementById('addUserLastNameInput')?.value) || 'cognome';
    
    const p = document.getElementById('addUserEmailPreview');
    if (p) p.textContent = `${n}.${l}@biblioteca.it`;
}

function updateRoleSelection() {
    const toggle = (id, check) => document.getElementById(id)?.classList.toggle('role-option--selected', check);
    toggle('roleOptionUser', document.querySelector('input[value="role_user"]')?.checked);
    toggle('roleOptionAdmin', document.querySelector('input[value="role_admin"]')?.checked);
}

function initServerSidePopup() {
    Popup.open({
        title: 'Gestione delle copie', icon: 'fa-eye', panelId: 'viewBooksEditionContent',
        confirmText: 'Chiudi', showCancel: false, onConfirm: () => Popup.close()
    });
}

/**
 * Filtra le righe della tabella delle copie in base allo stato.
 * @param {HTMLElement} btn Il bottone cliccato
 */
function filterBookCopies(btn) {
    const filter = btn.dataset.filter;
    const container = document.getElementById('viewBooksListContainer');
    if (!container) return;

    const rows = container.querySelectorAll('tr');
    
    // Aggiorna stato attivo dei chip
    const bar = btn.closest('.popup-filter-bar');
    if (bar) {
        bar.querySelectorAll('.filter-chip').forEach(c => c.classList.remove('active'));
    }
    btn.classList.add('active');
    
    rows.forEach(row => {
        const statusPill = row.querySelector('.status-pill');
        if (!statusPill) return;

        if (filter === 'all') {
            row.style.display = '';
        } else {
            // Verifica se la pillola ha la classe corrispondente al filtro
            if (statusPill.classList.contains(filter)) {
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        }
    });
}

// Esponi globalmente
window.openAddUserPopup = openAddUserPopup;
window.updateAddUserEmailPreview = updateAddUserEmailPreview;
window.updateRoleSelection = updateRoleSelection;
window.initServerSidePopup = initServerSidePopup;
window.filterBookCopies = filterBookCopies;

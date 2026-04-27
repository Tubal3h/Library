/**
 * utilityHandlers.js
 * Gestisce l'aggiunta di dipendenti e la visualizzazione delle copie.
 */

function openAddUserPopup() {
    Popup.open({
        title: 'Aggiungi Dipendente', icon: 'fa-user-plus', panelId: 'addUserContent',
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
    const n = document.getElementById('addUserNameInput')?.value.trim().toLowerCase().replace(/\s+/g, '') || 'nome';
    const l = document.getElementById('addUserLastNameInput')?.value.trim().toLowerCase().replace(/\s+/g, '') || 'cognome';
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
        title: 'Gestione copie', icon: 'fa-eye', panelId: 'viewBooksEditionContent',
        confirmText: 'Chiudi', showCancel: false, onConfirm: () => Popup.close()
    });
}

// Esponi globalmente
window.openAddUserPopup = openAddUserPopup;
window.updateAddUserEmailPreview = updateAddUserEmailPreview;
window.updateRoleSelection = updateRoleSelection;
window.initServerSidePopup = initServerSidePopup;

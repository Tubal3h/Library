/**
 * Open the search popup modal
 * @param {string} section - 'catalog' or 'users'
 */
function openSearchPopup(section) {
    const overlay = document.getElementById('searchPopupOverlay');
    const title = document.getElementById('searchPopupTitle');
    const subtitle = document.getElementById('searchPopupSubtitle');
    const inputLabel = document.getElementById('searchInputLabel');
    const inputField = document.getElementById('searchInputField');

    if (!overlay || !title || !subtitle || !inputLabel || !inputField) return;

    if (section === 'catalog') {
        title.innerText = 'Ricerca Catalogo';
        subtitle.innerText = 'Cerca tra i libri e le edizioni';
        inputLabel.innerText = 'Cerca per titolo, autore, o ISBN';
        inputField.placeholder = 'Es: C++ Primer o John Doe...';
    } else if (section === 'users') {
        title.innerText = 'Ricerca Utenti';
        subtitle.innerText = 'Cerca tra gli utenti del sistema';
        inputLabel.innerText = 'Cerca per nome, cognome o email';
        inputField.placeholder = 'Es: mario.rossi@email.com...';
    } else if (section === 'edition') {
        title.innerText = 'Ricerca Edizioni';
        subtitle.innerText = 'Cerca tra le edizioni disponibili';
        inputLabel.innerText = 'Cerca per titolo, editore o anno';
        inputField.placeholder = 'Es: Mondadori, 2023...';
    } else if (section === 'rents') {
        title.innerText = 'Ricerca Prestiti';
        subtitle.innerText = 'Cerca tra i prestiti in corso o scaduti';
        inputLabel.innerText = 'Cerca per ISBN, ID utente o data';
        inputField.placeholder = 'Es: 978-..., user-id...';
    } else {
        title.innerText = 'Ricerca Generale';
        subtitle.innerText = 'Cerca nel sistema';
        inputLabel.innerText = 'Query di ricerca';
        inputField.placeholder = 'Scrivi qui...';
    }

    inputField.value = ''; // Reset input
    
    // Configura la sezione attuale per la ricerca
    overlay.dataset.section = section;

    // Mostra il popup
    overlay.classList.remove('none');
    document.body.style.overflow = 'hidden'; 
    inputField.focus();
}

/**
 * Close the search pop up
 */
function closeSearchPopup() {
    const overlay = document.getElementById('searchPopupOverlay');
    if (overlay) {
        overlay.classList.add('none');
        document.body.style.overflow = ''; 
    }
}

/**
 * Execute the search (Frontend only)
 */
function executeSearch() {
    const query = document.getElementById('searchInputField').value;
    const overlay = document.getElementById('searchPopupOverlay');
    const section = overlay ? overlay.dataset.section : 'general';

    if (query.trim() === '') {
        // Mostra un avviso visuale usando animazione/effetto (o semplice alert in frontend only)
        document.getElementById('searchInputField').style.borderColor = 'var(--color-error)';
        setTimeout(() => {
            document.getElementById('searchInputField').style.borderColor = 'var(--color-border)';
        }, 2000);
        return;
    }

    console.log(`Esecuzione ricerca in sezione '${section}' per query: '${query}'`);
    alert(`Simulazione ricerca in '${section}' per: '${query}'.\nManca il backend.`);
    closeSearchPopup();
}

// Supporto tasto Invio nel campo di ricerca
document.addEventListener('DOMContentLoaded', () => {
    const inputField = document.getElementById('searchInputField');
    if (inputField) {
        inputField.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                e.preventDefault();
                executeSearch();
            }
        });
    }

    // Chiudi cliccando fuori dal popup
    const overlay = document.getElementById('searchPopupOverlay');
    if (overlay) {
        overlay.addEventListener('click', (event) => {
            if (event.target === overlay) {
                closeSearchPopup();
            }
        });
    }
});

/**
 * searchbar.js
 * Logica per il popup di ricerca (searchbar.html).
 * Permette di configurare dinamicamente i testi del popup in base alla sezione
 * corrente (catalogo, utenti, prestiti) e di eseguire la ricerca tramite URL params.
 */

/**
 * Apre il popup di ricerca configurandolo per una specifica sezione.
 * 
 * @param {string} section - La sezione in cui effettuare la ricerca ('catalog', 'users', 'edition', 'rents').
 */
function openSearchPopup(section) {
    const overlay = document.getElementById('searchPopupOverlay');
    const title = document.getElementById('searchPopupTitle');
    const subtitle = document.getElementById('searchPopupSubtitle');
    const inputLabel = document.getElementById('searchInputLabel');
    const inputField = document.getElementById('searchInputField');

    if (!overlay || !title || !subtitle || !inputLabel || !inputField) return;

    // Configurazione dinamica dei testi in base alla sezione
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
        inputLabel.innerText = 'Cerca per titolo, editore, categoria';
        inputField.placeholder = 'Es: Mondadori, 2023...';
    } else if (section === 'rents') {
        title.innerText = 'Ricerca Prestiti';
        subtitle.innerText = 'Cerca tra i prestiti in corso o scaduti';
        inputLabel.innerText = 'Cerca per ISBN, ID utente o Data di Scadenza';
        inputField.placeholder = 'Es: 978-..., user-id...';
    } else {
        title.innerText = 'Ricerca Generale';
        subtitle.innerText = 'Cerca nel sistema';
        inputLabel.innerText = 'Query di ricerca';
        inputField.placeholder = 'Scrivi qui...';
    }

    inputField.value = ''; // Reset del campo input
    
    // Memorizza la sezione attuale nell'attributo data-section dell'overlay
    overlay.dataset.section = section;

    // Visualizza il popup e imposta il focus sull'input
    overlay.classList.remove('none');
    document.body.style.overflow = 'hidden'; 
    inputField.focus();
}

/**
 * Chiude il popup di ricerca e ripristina lo scroll.
 */
function closeSearchPopup() {
    const overlay = document.getElementById('searchPopupOverlay');
    if (overlay) {
        overlay.classList.add('none');
        document.body.style.overflow = ''; 
    }
}

/**
 * Esegue la ricerca aggiornando i parametri URL della pagina.
 * Questo approccio permette al backend di filtrare i risultati durante il ricaricamento.
 */
function executeSearch() {
    const inputField = document.getElementById('searchInputField');
    const query = inputField.value.trim();

    if (query === '') {
        inputField.classList.add('border-error');
        setTimeout(() => {
            inputField.classList.remove('border-error');
        }, 2000);
        return;
    }

    closeSearchPopup();

    window.location.href = `/api/search/${encodeURIComponent(query)}`;
}


/**
 * Inizializzazione dei listener per il campo di ricerca e l'overlay.
 */
document.addEventListener('DOMContentLoaded', () => {
    const inputField = document.getElementById('searchInputField');
    if (inputField) {
        // Avvia la ricerca premendo il tasto 'Invio'
        inputField.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                e.preventDefault();
                executeSearch();
            }
        });
    }

    // Chiude il popup cliccando sullo sfondo (overlay)
    const overlay = document.getElementById('searchPopupOverlay');
    if (overlay) {
        overlay.addEventListener('click', (event) => {
            if (event.target === overlay) {
                closeSearchPopup();
            }
        });
    }
});

/**
 * -------------------------------------------------------------------------
 * RENTS.JS
 * -------------------------------------------------------------------------
 * Gestisce la logica interattiva per il frammento dei noleggi.
 * Include il filtraggio lato client per i libri scaduti.
 */

/**
 * Filtra la lista dei noleggi in base allo stato (Prenotato, In Prestito, Scaduto).
 * @param {HTMLElement} btn - Il chip cliccato
 */
function filterRents(btn) {
    const filter = btn.dataset.filter;
    const cards = document.querySelectorAll('.book-card-horizontal[data-status]');
    
    // Aggiorna stato attivo dei chip
    const bar = btn.closest('.popup-filter-bar');
    if (bar) {
        bar.querySelectorAll('.filter-chip').forEach(c => c.classList.remove('active'));
    }
    btn.classList.add('active');
    
    cards.forEach(card => {
        const status = card.dataset.status;
        const isExpired = card.dataset.expired === 'true';
        
        let visible = false;
        if (filter === 'all') {
            visible = true;
        } else if (filter === 'expired') {
            visible = isExpired;
        } else {
            visible = (status === filter);
        }
        
        if (visible) {
            card.classList.remove('card-hidden', 'fade-out');
            void card.offsetWidth; // Trigger reflow
            card.classList.add('fade-in');
            setTimeout(() => card.classList.remove('fade-in'), 300);
        } else {
            card.classList.add('fade-out');
            setTimeout(() => {
                if (card.classList.contains('fade-out')) {
                    card.classList.add('card-hidden');
                }
            }, 300);
        }
    });
}

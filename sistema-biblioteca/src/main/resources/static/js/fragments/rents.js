/**
 * -------------------------------------------------------------------------
 * RENTS.JS
 * -------------------------------------------------------------------------
 * Gestisce la logica interattiva per il frammento dei noleggi.
 * Include il filtraggio lato client per i libri scaduti.
 */

/**
 * Filtra la lista dei noleggi in base allo stato di scadenza.
 * Se attivo, mostra solo i noleggi che hanno data_expired="true".
 * 
 * @param {HTMLInputElement} checkbox - L'elemento input che triggera il filtro
 */
function toggleExpiredFilter(checkbox) {
    const isChecked = checkbox.checked;
    const cards = document.querySelectorAll('.book-card-horizontal[data-expired]');

    cards.forEach(card => {
        const isExpired = card.getAttribute('data-expired') === 'true';

        if (isChecked && !isExpired) {
            // Nasconde le card non scadute
            card.classList.add('fade-out');
            
            // Attende la fine dell'animazione CSS prima di applicare display:none
            setTimeout(() => {
                if (checkbox.checked) {
                    card.classList.add('card-hidden');
                }
            }, 300);
        } else {
            // Mostra le card (o le lascia visibili)
            card.classList.remove('card-hidden');
            
            // Trigger reflow per riavviare l'animazione se necessario
            void card.offsetWidth;
            
            card.classList.remove('fade-out');
            card.classList.add('fade-in');
            
            // Pulisce la classe temporanea di fade-in
            setTimeout(() => {
                card.classList.remove('fade-in');
            }, 300);
        }
    });
}

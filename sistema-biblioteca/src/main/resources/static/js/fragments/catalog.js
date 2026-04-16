/**
 * catalog.js
 * Gestisce le interazioni e le animazioni specifiche della sezione catalogo (catalog.html).
 * Nota: La maggior parte delle animazioni a cascata (stagger) è gestita tramite
 * variabili CSS (--delay) calcolate direttamente in Thymeleaf.
 */

document.addEventListener('DOMContentLoaded', () => {
    /**
     * Inizializzazione della logica del catalogo.
     * È possibile estendere questo script per gestire filtri dinamici client-side
     * o caricamenti asincroni (AJAX) delle schede libro.
     */
    
    // Selettore per tutte le schede libro presenti nel catalogo
    const catalogCards = document.querySelectorAll('.animate-card');
    
    if (catalogCards.length > 0) {
        console.log(`[Catalog] Inizializzate ${catalogCards.length} schede libro.`);
    }
});

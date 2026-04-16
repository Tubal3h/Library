/**
 * navbar.js
 * Logica per la barra di navigazione laterale (navbar.html).
 * Gestisce l'animazione di ingresso e le interazioni specifiche della Sidebar.
 */

document.addEventListener('DOMContentLoaded', () => {
    const sidebar = document.getElementById('sidebar');
    
    /**
     * Trigger per l'animazione di ingresso iniziale.
     * Anche se l'animazione base è gestita via CSS, questo timeout
     * assicura che l'elemento diventi visibile correttamente.
     */
    setTimeout(() => {
        if (sidebar) {
            sidebar.style.opacity = '1';
        }
    }, 100);

    // Nota: Eventuali logiche per tooltip o collassamento sidebar possono essere aggiunte qui.
});

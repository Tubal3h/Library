/**
 * layout.js
 * Logica globale per il gestore del layout (layout.html).
 * Gestisce il tema Dark/Light (persistenza e switch) e l'animazione di reveal
 * iniziale dei componenti principali (Sidebar, Main Content).
 */

document.addEventListener('DOMContentLoaded', () => {
    const body = document.querySelector('body');
    const themeToggle = document.getElementById('theme-toggle');

    /**
     * ---- GESTIONE TEMA (DARK/LIGHT) ----
     * Sincronizza il tema salvato nel localStorage con lo stato della pagina.
     */
    const savedTheme = localStorage.getItem('theme') || 'light';
    body.setAttribute('data-theme', savedTheme);
    
    if (themeToggle) {
        // Imposta lo stato iniziale del toggle basato sul tema salvato
        themeToggle.checked = savedTheme === 'dark';
        
        /**
         * Listener per il cambio manuale del tema.
         */
        themeToggle.addEventListener('change', () => {
            const newTheme = themeToggle.checked ? 'dark' : 'light';
            body.setAttribute('data-theme', newTheme);
            localStorage.setItem('theme', newTheme);
            
            console.log(`[Theme] Switch to: ${newTheme}`);
        });
    }

    /**
     * ---- ANIMAZIONE DI REVEAL INIZIALE ----
     * Mostra gradualmente la Sidebar e l'area di contenuto principale
     * con un leggero ritardo sequenziale per un effetto premium.
     */
    const elementsToReveal = [
        document.getElementById('sidebar'),
        document.getElementById('app-main')
    ];

    elementsToReveal.forEach((el, index) => {
        if (el) {
            setTimeout(() => {
                // Imposta opacità e visibilità per mostrare l'elemento
                el.style.opacity = '1';
                el.style.visibility = 'visible';
            }, index * 100);
        }
    });
});

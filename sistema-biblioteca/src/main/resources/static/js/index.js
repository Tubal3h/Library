/**
 * index.js
 * Logica per la pagina di login (index.html).
 * Gestisce le animazioni di ingresso, il carosello delle citazioni,
 * la visibilità della password e il feedback del form.
 */

document.addEventListener('DOMContentLoaded', () => {

    /**
     * ---- ANIMAZIONE DI APERTURA ----
     * Utilizza le classi CSS 'page-loading' e 'page-ready' per innescare
     * le animazioni definite in index.css.
     */
    document.body.classList.add('page-loading');
    requestAnimationFrame(() => {
        requestAnimationFrame(() => {
            document.body.classList.remove('page-loading');
            document.body.classList.add('page-ready');
        });
    });

    /**
     * ---- TOGGLE VISIBILITÀ PASSWORD ----
     * Cambia il tipo dell'input da 'password' a 'text' e viceversa.
     */
    const toggleBtn = document.getElementById('toggle-password');
    const passwordInput = document.getElementById('password');
    const eyeIcon = document.getElementById('eye-icon');

    if (toggleBtn && passwordInput) {
        toggleBtn.addEventListener('click', () => {
            const isHidden = passwordInput.type === 'password';
            passwordInput.type = isHidden ? 'text' : 'password';
            // Aggiorna l'icona FontAwesome
            eyeIcon.classList.toggle('fa-eye', !isHidden);
            eyeIcon.classList.toggle('fa-eye-slash', isHidden);
        });
    }

    /**
     * ---- CAROSELLO DELLE CITAZIONI ----
     * Gestisce la rotazione di frasi celebri sulla lettura nel pannello sinistro.
     */
    const quotes = [
        { text: '"Un libro è un sogno che tieni in mano."', author: '— Neil Gaiman' },
        { text: '"Non esiste un amico più fedele di un libro."', author: '— Ernest Hemingway' },
        { text: '"La lettura è a mente quello che l\'esercizio è al corpo."', author: '— Joseph Addison' },
        { text: '"I libri sono uno specchio: vedi solo ciò che porti dentro."', author: '— Carlos Ruiz Zafón' },
    ];

    let currentQuote = 0;
    const quoteText = document.getElementById('left-quote-text');
    const quoteAuthor = document.getElementById('left-quote-author');

    /**
     * Aggiorna la citazione visualizzata con un effetto di dissolvenza.
     * @param {number} index - L'indice della citazione nell'array 'quotes'.
     */
    function updateQuote(index) {
        if (!quoteText || !quoteAuthor) return;
        
        // Inizio dissolvenza (opacità 0)
        quoteText.style.opacity = '0';
        quoteAuthor.style.opacity = '0';
        
        setTimeout(() => {
            quoteText.textContent = quotes[index].text;
            quoteAuthor.textContent = quotes[index].author;
            // Fine dissolvenza (opacità 1)
            quoteText.style.opacity = '1';
            quoteAuthor.style.opacity = '1';
        }, 220); // Tempo coordinato con la transizione CSS
    }

    // Configurazione iniziale della transizione (mantenuta inline per semplicità JS)
    if (quoteText) quoteText.style.transition = 'opacity 0.22s ease';
    if (quoteAuthor) quoteAuthor.style.transition = 'opacity 0.22s ease';

    const prevBtn = document.getElementById('arrow-prev');
    const nextBtn = document.getElementById('arrow-next');

    // Listener per i pulsanti di navigazione del carosello
    if (prevBtn) {
        prevBtn.addEventListener('click', () => {
            currentQuote = (currentQuote - 1 + quotes.length) % quotes.length;
            updateQuote(currentQuote);
        });
    }

    if (nextBtn) {
        nextBtn.addEventListener('click', () => {
            currentQuote = (currentQuote + 1) % quotes.length;
            updateQuote(currentQuote);
        });
    }

    /**
     * ---- GESTIONE ERRORI THYMELEAF ----
     * Controlla la presenza del parametro 'error' nell'URL (esposto dopo reindirizzamento dal controller).
     */
    const params = new URLSearchParams(window.location.search);
    const errorMsg = document.getElementById('error-msg');
    const errorText = document.getElementById('error-text');

    if (params.has('error') && errorMsg) {
        errorMsg.classList.remove('none');
        if (errorText) errorText.textContent = 'Username o password non corretti.';
    }

    /**
     * ---- FEEDBACK INVIO FORM ----
     * Disabilita il pulsante di invio per prevenire click multipli e fornisce feedback visivo.
     */
    const loginForm = document.getElementById('login-form');
    const loginBtn = document.getElementById('login-btn');
    const btnText = document.getElementById('btn-text');

    if (loginForm && loginBtn) {
        loginForm.addEventListener('submit', () => {
            loginBtn.disabled = true;
            if (btnText) btnText.textContent = 'Accesso in corso…';
        });
    }

});

/* ============================================================
   index.js — logica pagina login
   ============================================================ */

document.addEventListener('DOMContentLoaded', () => {

    /* ---- Animazione di apertura ---- */
    document.body.classList.add('page-loading');
    requestAnimationFrame(() => {
        requestAnimationFrame(() => {
            document.body.classList.remove('page-loading');
            document.body.classList.add('page-ready');
        });
    });

    /* ---- Toggle visibilità password ---- */
    const toggleBtn = document.getElementById('toggle-password');
    const passwordInput = document.getElementById('password');
    const eyeIcon = document.getElementById('eye-icon');

    if (toggleBtn && passwordInput) {
        toggleBtn.addEventListener('click', () => {
            const isHidden = passwordInput.type === 'password';
            passwordInput.type = isHidden ? 'text' : 'password';
            eyeIcon.classList.toggle('fa-eye', !isHidden);
            eyeIcon.classList.toggle('fa-eye-slash', isHidden);
        });
    }

    /* ---- Citazioni per i pulsanti freccia ---- */
    const quotes = [
        { text: '"Un libro è un sogno che tieni in mano."', author: '— Neil Gaiman' },
        { text: '"Non esiste un amico più fedele di un libro."', author: '— Ernest Hemingway' },
        { text: '"La lettura è a mente quello che l\'esercizio è al corpo."', author: '— Joseph Addison' },
        { text: '"I libri sono uno specchio: vedi solo ciò che porti dentro."', author: '— Carlos Ruiz Zafón' },
    ];

    let currentQuote = 0;
    const quoteText = document.getElementById('left-quote-text');
    const quoteAuthor = document.getElementById('left-quote-author');

    function updateQuote(index) {
        if (!quoteText || !quoteAuthor) return;
        quoteText.style.opacity = '0';
        quoteAuthor.style.opacity = '0';
        setTimeout(() => {
            quoteText.textContent = quotes[index].text;
            quoteAuthor.textContent = quotes[index].author;
            quoteText.style.opacity = '1';
            quoteAuthor.style.opacity = '1';
        }, 220);
    }

    // Transizione CSS: aggiunta inline
    if (quoteText) quoteText.style.transition = 'opacity 0.22s ease';
    if (quoteAuthor) quoteAuthor.style.transition = 'opacity 0.22s ease';

    const prevBtn = document.getElementById('arrow-prev');
    const nextBtn = document.getElementById('arrow-next');

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

    /* ---- Gestione errori Thymeleaf (parametro ?error) ---- */
    const params = new URLSearchParams(window.location.search);
    const errorMsg = document.getElementById('error-msg');
    const errorText = document.getElementById('error-text');

    if (params.has('error') && errorMsg) {
        errorMsg.classList.remove('none');
        if (errorText) errorText.textContent = 'Username o password non corretti.';
    }

    /* ---- Rimosso intercettamento Javascript perché usiamo Controller classici! ---- */
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

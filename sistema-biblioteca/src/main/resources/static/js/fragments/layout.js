/* ============================================================
   LAYOUT.JS — Global Layout Logic
   ============================================================ */
document.addEventListener('DOMContentLoaded', () => {
    const body = document.querySelector('body');
    const themeToggle = document.getElementById('theme-toggle');

    // 1. Initial Theme Sync
    const savedTheme = localStorage.getItem('theme') || 'light';
    body.setAttribute('data-theme', savedTheme);
    
    if (themeToggle) {
        themeToggle.checked = savedTheme === 'dark';
        
        // 2. Theme Toggle Listener
        themeToggle.addEventListener('change', () => {
            const newTheme = themeToggle.checked ? 'dark' : 'light';
            body.setAttribute('data-theme', newTheme);
            localStorage.setItem('theme', newTheme);
            
            // Optional: Log for debugging
            console.log(`Theme changed to: ${newTheme}`);
        });
    }

    // Reveal main app elements
    const elementsToReveal = [
        document.getElementById('sidebar'),
        document.getElementById('app-main')
    ];

    elementsToReveal.forEach((el, index) => {
        if (el) {
            setTimeout(() => {
                el.style.opacity = '1';
                el.style.visibility = 'visible';
            }, index * 100);
        }
    });
});

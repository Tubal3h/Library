/* ============================================================
   NAVBAR.JS — Logiche Navbar
   ============================================================ */
   document.addEventListener('DOMContentLoaded', () => {
    // Nascondi la navbar allo scroll verso il basso per risparmiare spazio,
    // mostrala allo scroll verso l'alto (Smart Sticky Navbar)
    let lastScroll = 0;
    const navContainer = document.getElementById('nav-container');

    if (navContainer) {
        window.addEventListener('scroll', () => {
            const currentScroll = window.pageYOffset || document.documentElement.scrollTop;
            
            // Scroll down
            if (currentScroll > 60 && currentScroll > lastScroll) {
                navContainer.style.transform = 'translateY(-150%) scale(0.95)';
                navContainer.style.opacity = '0';
            } 
            // Scroll up
            else {
                navContainer.style.transform = 'translateY(0) scale(1)';
                navContainer.style.opacity = '1';
            }
            lastScroll = currentScroll <= 0 ? 0 : currentScroll;
        }, { passive: true });
    }
});

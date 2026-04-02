/* ============================================================
   SIDEBAR.JS — Sidebar Logics
   ============================================================ */
document.addEventListener('DOMContentLoaded', () => {
    const sidebar = document.getElementById('sidebar');
    
    // Initial entrance animation trigger (handled by CSS, but good to have a backup)
    setTimeout(() => {
        if (sidebar) sidebar.style.opacity = '1';
    }, 100);

    // Sidebar Tooltip or Collapse logic can go here
});

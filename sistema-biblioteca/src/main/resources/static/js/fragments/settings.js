/**
 * settings.js
 * Logic specifically for the settings page.
 */

document.addEventListener('DOMContentLoaded', () => {
    console.log('Settings section loaded');
});

/**
 * Switches the active tab panel in the settings view.
 * @param {HTMLElement} clickedTab - The tab button that was clicked
 * @param {string} targetId       - The ID of the panel to show
 */
function switchSettingsTab(clickedTab, targetId) {
    // Deactivate all tabs
    document.querySelectorAll('.settings-tab').forEach(tab => tab.classList.remove('active'));
    // Hide all panels
    document.querySelectorAll('.settings-panel').forEach(panel => {
        panel.classList.remove('active');
        panel.classList.remove('panel-enter');
    });

    // Activate the clicked tab
    clickedTab.classList.add('active');

    // Show & animate the target panel
    const target = document.getElementById(targetId);
    if (target) {
        target.classList.add('active');
        // Trigger reflow so the animation re-runs
        void target.offsetWidth;
        target.classList.add('panel-enter');
    }
}

/**
 * Filters a settings list based on search input.
 * @param {HTMLInputElement} input - The search input element
 * @param {string} listId          - The ID of the list container to filter
 */
function filterSettingsList(input, listId) {
    const filter = input.value.toLowerCase();
    const list = document.getElementById(listId);
    const items = list.getElementsByClassName('settings-item');

    for (let i = 0; i < items.length; i++) {
        const searchText = items[i].getAttribute('data-search') || '';
        if (searchText.includes(filter)) {
            items[i].style.display = '';
        } else {
            items[i].style.display = 'none';
        }
    }

    // Handle empty state visibility
    const emptyState = list.querySelector('.settings-empty');
    if (emptyState) {
        let visibleCount = 0;
        for (let i = 0; i < items.length; i++) {
            if (items[i].style.display !== 'none') visibleCount++;
        }
        emptyState.style.display = visibleCount === 0 ? 'flex' : 'none';
    }
}

/* ── Scroll-to-top ── */
// Injected directly into <body> so position:fixed is always relative to the viewport,
// regardless of overflow/transform on parent containers.
(function initScrollTopBtn() {
    const scroller = document.getElementById('app-main');
    if (!scroller) return;

    // Create & append button to body
    const btn = document.createElement('button');
    btn.id        = 'settings-scroll-top';
    btn.className = 'settings-scroll-top';
    btn.title     = 'Torna su';
    btn.innerHTML = '<i class="fa-solid fa-arrow-up"></i>';
    btn.addEventListener('click', () => {
        scroller.scrollTo({ top: 0, behavior: 'smooth' });
    });
    document.body.appendChild(btn);

    // Show/hide on scroll
    scroller.addEventListener('scroll', () => {
        btn.classList.toggle('visible', scroller.scrollTop > 200);
    }, { passive: true });
})();

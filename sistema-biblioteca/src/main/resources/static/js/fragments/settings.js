/**
 * settings.js
 * Logic specifically for the settings page.
 */

document.addEventListener('DOMContentLoaded', () => {
    console.log('Settings section loaded');
});

/**
 * Filters a settings list based on search input
 * @param {HTMLInputElement} input - The search input element
 * @param {string} listId - The ID of the list container to filter
 */
function filterSettingsList(input, listId) {
    const filter = input.value.toLowerCase();
    const list = document.getElementById(listId);
    const items = list.getElementsByClassName('settings-item');

    for (let i = 0; i < items.length; i++) {
        const searchText = items[i].getAttribute('data-search') || '';
        if (searchText.includes(filter)) {
            items[i].style.display = "";
            items[i].classList.add('animate-fade-in'); // Optional: re-trigger animation
        } else {
            items[i].style.display = "none";
        }
    }

    // Handle empty state visibility if needed
    const emptyState = list.querySelector('.settings-empty');
    if (emptyState) {
        let visibleCount = 0;
        for (let i = 0; i < items.length; i++) {
            if (items[i].style.display !== "none") visibleCount++;
        }
        emptyState.style.display = visibleCount === 0 ? "flex" : "none";
    }
}

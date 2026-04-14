/**
 * Open the popup modal for editing or adding a copy
 * @param {string} action - 'edit' or 'addCopy'
 * @param {string} bookId - The ID of the book
 * @param {string} titleTxt - The title of the book
 * @param {string} authorTxt - The author of the book
 */
function openPopup(action, bookId, titleTxt, authorTxt) {
    const popup = document.getElementById('genericPopup');
    const title = document.getElementById('popupTitle');
    const icon = document.getElementById('popupIcon');
    const confirmBtn = document.getElementById('popupConfirmBtn');
    const editContent = document.getElementById('editBookContent');
    const addCopyContent = document.getElementById('addCopyContent');

    if (!popup || !title || !icon || !confirmBtn || !editContent || !addCopyContent) return;

    // Reset visibility
    editContent.classList.add('none');
    addCopyContent.classList.add('none');

    if (action === 'edit') {
        title.innerText = 'Modifica Libro';
        icon.className = 'fa-solid fa-pen-to-square text-white';
        icon.parentElement.style.background = 'var(--color-accent)'; // Accent icon box
        editContent.classList.remove('none');
        document.querySelector('.btn-link-action').classList.remove('none'); // Ensure cancel is visible
        
        const bookNameElem = document.getElementById('editBookName');
        if (bookNameElem) bookNameElem.innerText = titleTxt;
        
        const titleInput = document.getElementById('editTitleInput');
        if (titleInput) titleInput.value = titleTxt;
        
        const authorInput = document.getElementById('editAuthorInput');
        if (authorInput) authorInput.value = authorTxt;
        
        confirmBtn.innerText = 'Salva';
    } 
    else if (action === 'addCopy') {
        title.innerText = 'Successo';
        icon.className = 'fa-solid fa-circle-check text-white';
        icon.parentElement.style.background = 'var(--color-success)'; // Green icon box
        addCopyContent.classList.remove('none');
        
        const addCopyNameElem = document.getElementById('addCopyBookName');
        if (addCopyNameElem) addCopyNameElem.innerText = titleTxt;
        
        confirmBtn.innerText = 'Chiudi';
        // Hide cancel button for success state
        document.querySelector('.btn-link-action').classList.add('none');
    }

    // Show popup with animation
    popup.classList.remove('none');
    document.body.style.overflow = 'hidden'; // Prevent scrolling

    // Frontend-only action for now
    confirmBtn.onclick = () => {
        console.log(`Azione '${action}' eseguita per il libro ID: ${bookId}`);
        alert(`Simulazione: Operazione '${action}' solo frontend manca il backend.`);
        closePopup();
    };
}

/**
 * Close the popup modal
 */
function closePopup() {
    const popup = document.getElementById('genericPopup');
    if (popup) {
        popup.classList.add('none');
        document.body.style.overflow = ''; // Restore scrolling
    }
}

/**
 * Close popup when clicking the backdrop
 */
function closePopupOnBackdrop(event) {
    if (event.target === event.currentTarget) {
        closePopup();
    }
}


/**
 * Open the popup modal for editing or adding a copy
 * @param {string} action - 'edit' or 'addCopy'
 * @param {string} bookId - The ID of the book
 * @param {string} titleTxt - The title of the book
 * @param {string} authorTxt - The author of the book
 * @param {string} categoryTxt - The category of the book
 * @param {string} publisherTxt - The publisher of the book
 */
function openPopup(action, bookId, titleTxt, authorTxt, categoryTxt, publisherTxt) {
    const popup = document.getElementById('genericPopup');
    const title = document.getElementById('popupTitle');
    const icon = document.getElementById('popupIcon');
    const confirmBtn = document.getElementById('popupConfirmBtn');
    const editContent = document.getElementById('editBookContent');
    const addCopyContent = document.getElementById('addCopyContent');

    if (!popup || !title || !icon || !confirmBtn || !editContent || !addCopyContent) return;

    editContent.classList.add('none');
    addCopyContent.classList.add('none');

    if (action === 'edit') {
        title.innerText = 'Modifica Libro';
        icon.className = 'fa-solid fa-pen-to-square text-white';
        icon.parentElement.style.background = 'var(--color-accent)'; 
        editContent.classList.remove('none');
        document.querySelector('.btn-link-action').classList.remove('none'); 
        
        const bookNameElem = document.getElementById('editBookName');
        if (bookNameElem) bookNameElem.innerText = titleTxt;
        
        const titleInput = document.getElementById('editTitleInput');
        if (titleInput) titleInput.value = titleTxt;
        
        const authorSelect = document.getElementById('editAuthorInput');
        if (authorSelect && authorTxt) {
            for (let i = 0; i < authorSelect.options.length; i++) {
                if (authorSelect.options[i].text === authorTxt) {
                    authorSelect.selectedIndex = i;
                    break;
                }
            }
        }
        
        const categorySelect = document.getElementById('editCategoryInput');
        if (categorySelect && categoryTxt) {
            for (let i = 0; i < categorySelect.options.length; i++) {
                if (categorySelect.options[i].text === categoryTxt) {
                    categorySelect.selectedIndex = i;
                    break;
                }
            }
        }
        
        const publisherSelect = document.getElementById('editPublisherInput');
        if (publisherSelect && publisherTxt) {
            for (let i = 0; i < publisherSelect.options.length; i++) {
                if (publisherSelect.options[i].text === publisherTxt) {
                    publisherSelect.selectedIndex = i;
                    break;
                }
            }
        }
        
        confirmBtn.innerText = 'Salva';
    } 
    else if (action === 'addCopy') {
        title.innerText = 'Successo';
        icon.className = 'fa-solid fa-circle-check text-white';
        icon.parentElement.style.background = 'var(--color-success)'; 
        addCopyContent.classList.remove('none');
        
        const addCopyNameElem = document.getElementById('addCopyBookName');
        if (addCopyNameElem) addCopyNameElem.innerText = titleTxt;
        
        confirmBtn.innerText = 'Chiudi';
        document.querySelector('.btn-link-action').classList.add('none');
    }

    popup.classList.remove('none');
    document.body.style.overflow = 'hidden'; 


    confirmBtn.onclick = () => {
        console.log(`Azione '${action}' eseguita per il libro ID: ${bookId}`);
        alert(`Simulazione: Operazione '${action}' solo frontend manca il backend.`);
        closePopup();
    };
}

/**
 * Close pop up
 */
function closePopup() {
    const popup = document.getElementById('genericPopup');
    if (popup) {
        popup.classList.add('none');
        document.body.style.overflow = ''; 
    }
}

/**
 * Close pop up
 */
function closePopupOnBackdrop(event) {
    if (event.target === event.currentTarget) {
        closePopup();
    }
}


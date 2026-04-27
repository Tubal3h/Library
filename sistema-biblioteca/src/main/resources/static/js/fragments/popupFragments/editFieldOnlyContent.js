// Stato corrente del pannello generico
let _currentFieldType = null;

const FIELD_CONFIG = {
  title: {
    titleText:    'Modifica Titolo',
    icon:         'fa-pen-to-square',
    description:  "Stai modificando il titolo dell'opera:",
    label:        'Nuovo Titolo',
    placeholder:  'Inizia a scrivere il titolo...',
    dataSourceId: 'titleDataSource',
    getItems:     () => getItemsFromSource('titleDataSource', 'bookNameId'),
    confirmUrlFn: (id, selectedId) => `/api/updateBookTitle?editionId=${id}&bookNameId=${selectedId}`,
    confirmTitle: 'Cambio Titolo',
    messageFn:    (cur, next) => `Stai cambiando "${cur}" con "${next}".`,
    alertMsg:     'Seleziona un titolo esistente dalla lista.',
  },
  author: {
    titleText:    'Modifica Autore',
    icon:         'fa-user-pen',
    description:  "Stai cambiando l'autore dell'edizione:",
    label:        'Cerca e Seleziona Autore',
    placeholder:  'Cerca autore per nome o cognome...',
    dataSourceId: 'authorDataSource',
    getItems:     () => getAuthorItems(),
    confirmUrlFn: (id, selectedId) => `/api/updateAuthor?editionId=${id}&authorId=${selectedId}`,
    confirmTitle: 'Cambio Autore',
    messageFn:    (cur, next) => `Stai cambiando "${cur}" con "${next}".`,
    alertMsg:     'Seleziona un autore esistente dalla lista.',
  },
  publisher: {
    titleText:    'Modifica Editore',
    icon:         'fa-building-columns',
    description:  "Modifica editore dell'edizione:",
    label:        'Nuovo Editore',
    placeholder:  "Inserisci il nome dell'editore...",
    dataSourceId: 'publisherDataSource',
    getItems:     () => getItemsFromSource('publisherDataSource', 'publisherId'),
    confirmUrlFn: (id, selectedId) => `/api/updatePublisher?editionId=${id}&publisherNameId=${selectedId}`,
    confirmTitle: 'Cambio Editore',
    messageFn:    (cur, next) => `Stai cambiando "${cur}" con "${next}".`,
    alertMsg:     'Seleziona un editore esistente dalla lista.',
  },
  category: {
    titleText:    'Modifica Categoria',
    icon:         'fa-tag',
    description:  "Modifica categoria dell'edizione:",
    label:        'Nuova Categoria',
    placeholder:  'Inserisci la categoria...',
    dataSourceId: 'categoryDataSource',
    getItems:     () => getItemsFromSource('categoryDataSource', 'categoryId'),
    confirmUrlFn: (id, selectedId) => `/api/updateCategory?editionId=${id}&categoryNameId=${selectedId}`,
    confirmTitle: 'Cambio Categoria',
    messageFn:    (cur, next) => `Stai cambiando "${cur}" con "${next}".`,
    alertMsg:     'Seleziona una categoria esistente dalla lista.',
  },
};

function getItemsFromSource(sourceId, idAttr) {
  return Array.from(document.querySelectorAll(`#${sourceId} li`))
    .map(li => ({ label: li.textContent.trim(), id: li.dataset[idAttr] || li.dataset.id }))
    .filter(i => i.label);
}

function getAuthorItems() {
  return Array.from(document.querySelectorAll('#authorDataSource li'))
    .map(li => ({
      label: li.textContent.trim(),
      id:    li.dataset.id,
      first: li.dataset.first,
      last:  li.dataset.last,
    }));
}

function populateFieldSelect(items) {
  const select = document.getElementById('editFieldOnlySelect');
  if (!select) return;
  select.innerHTML = '<option value="">Seleziona...</option>';
  items.forEach(item => {
    const opt = document.createElement('option');
    opt.value       = item.id;
    opt.textContent = item.label;
    select.appendChild(opt);
  });
}

function openEditFieldPopup(fieldType, id, currentValue, useSelect = false) {
  const cfg = FIELD_CONFIG[fieldType];
  if (!cfg) return console.warn(`Campo "${fieldType}" non configurato.`);

  _currentFieldType = fieldType;

  // Usa il manager centrale Popup
  Popup.open({
    title: cfg.titleText,
    icon: cfg.icon,
    panelId: 'editFieldOnlyContent',
    confirmText: `Salva ${cfg.titleText.replace('Modifica ', '')}`,
    onConfirm: () => {
      const inputElem  = document.getElementById('editFieldOnlyInput');
      const selectElem = document.getElementById('editFieldOnlySelect');
      let selectedId, textValue;

      if (useSelect && selectElem) {
        selectedId = selectElem.value;
        textValue  = selectElem.options[selectElem.selectedIndex].text;
      } else if (inputElem) {
        textValue  = inputElem.value.trim();
        selectedId = findIdByText(cfg.dataSourceId, textValue);
      }

      if (!selectedId) { alert(cfg.alertMsg); return; }

      openConfirmPopup(
        'edit',
        cfg.confirmTitle,
        cfg.messageFn(currentValue, textValue),
        cfg.confirmUrlFn(id, selectedId),
        'POST'
      );
    }
  });

  // Popola il contenuto del pannello
  document.getElementById('editFieldOnlyDescription').textContent = cfg.description;
  document.getElementById('editFieldOnlyCurrent').textContent     = currentValue;
  document.getElementById('editFieldOnlyLabel').textContent       = cfg.label;

  const inputElem  = document.getElementById('editFieldOnlyInput');
  const selectElem = document.getElementById('editFieldOnlySelect');
  const dropdown   = document.getElementById('editFieldSuggestionsDropdown');

  if (useSelect && selectElem) {
    const items = cfg.getItems();
    populateFieldSelect(items);
    inputElem.classList.add('none');
    selectElem.classList.remove('none');
    if (dropdown) dropdown.classList.add('none');
    Array.from(selectElem.options).forEach((opt, i) => {
      if (opt.text === currentValue) selectElem.selectedIndex = i;
    });
  } else if (inputElem) {
    inputElem.classList.remove('none');
    inputElem.value       = currentValue;
    inputElem.placeholder = cfg.placeholder;
    if (selectElem) selectElem.classList.add('none');
    if (dropdown) {
      dropdown.classList.add('none');
      dropdown.innerHTML = '';
    }

  }
}

function filterFieldSuggestions(query) {
  if (!_currentFieldType) return;
  const cfg      = FIELD_CONFIG[_currentFieldType];
  const dropdown = document.getElementById('editFieldSuggestionsDropdown');
  const q        = query.trim().toLowerCase();

  if (!q) { dropdown.classList.add('none'); dropdown.innerHTML = ''; return; }


  const matches = cfg.getItems().filter(i => i.label.toLowerCase().includes(q));
  if (!matches.length) { dropdown.classList.add('none'); dropdown.innerHTML = ''; return; }


  const re = new RegExp(q.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'), 'gi');
  dropdown.innerHTML = matches.map(item => {
    const hl = item.label.replace(re,
      m => `<mark style="background:rgba(245,166,35,0.3);border-radius:3px;padding:0 2px">${m}</mark>`
    );
    return `<div class="suggestion-item"
                 onmousedown="selectFieldSuggestion(${JSON.stringify(item).replace(/"/g, '&quot;')})"
                 onmouseover="this.style.background='var(--color-surface-2)'"
                 onmouseout="this.style.background=''">${hl}</div>`;
  }).join('');
  dropdown.classList.remove('none');

}

function selectFieldSuggestion(item) {
  const input    = document.getElementById('editFieldOnlyInput');
  const dropdown = document.getElementById('editFieldSuggestionsDropdown');
  if (input) input.value = item.label;
  if (item.first) document.getElementById('editFieldHiddenFirst').value = item.first;
  if (item.last)  document.getElementById('editFieldHiddenLast').value  = item.last;
  if (dropdown) { dropdown.classList.add('none'); dropdown.innerHTML = ''; }

}

// Esponi globalmente per onclick HTML
window.openEditTitlePopup     = (id, val, useSelect) => openEditFieldPopup('title',     id, val, useSelect);
window.openEditAuthorPopup    = (id, val, useSelect) => openEditFieldPopup('author',    id, val, useSelect);
window.openEditPublisherPopup = (id, val, useSelect) => openEditFieldPopup('publisher', id, val, useSelect);
window.openEditCategoryPopup  = (id, val, useSelect) => openEditFieldPopup('category',  id, val, useSelect);
window.filterFieldSuggestions = filterFieldSuggestions;
window.selectFieldSuggestion = selectFieldSuggestion;
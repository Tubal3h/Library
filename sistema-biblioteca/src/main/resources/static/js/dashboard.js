/**
 * Toggles the visibility of a password input field.
 * @param {HTMLElement} button - The button element that triggered the toggle.
 */
function togglePassword(button) {
    const container = button.closest('.input-field-container');
    const input = container.querySelector('input');
    const icon = button.querySelector('i');
    
    if (input.type === 'password') {
        input.type = 'text';
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    } else {
        input.type = 'password';
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
    }
}

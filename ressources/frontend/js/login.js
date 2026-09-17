// login.js
document.querySelector('.login-container').addEventListener('submit', function(e) {
    e.preventDefault(); // forhindrer siden i at genindlæse
    // evt. validering/login-logik her
    window.location.href = 'dashboard.html';
});
// Check if the user is logged in and redirect if necessary
function checkLoginStatus() {
    const isLoggedIn = sessionStorage.getItem("isLoggedIn");
    if (!isLoggedIn) {
        // Redirect to login page if not logged in
        window.location.href = "login.html";
    }
}

// Handle login form submission
function handleLogin(event) {
    event.preventDefault(); // Prevent form submission

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Simulate login validation (replace with backend validation if needed)
    if (username === "test" && password === "1234") {
        sessionStorage.setItem("isLoggedIn", true); // Mark user as logged in
        alert("Login successful!");
        window.location.href = "index.html"; // Redirect to homepage
    } else {
        alert("Invalid username or password!");
    }
}
// ADMIN LOGIN FOR PAGE
function handleLogin(event) {
    event.preventDefault(); // Prevent form submission

    const username = document.getElementById("adminusername").value;
    const password = document.getElementById("adminpassword").value;

    // Simulate login validation (replace with backend validation if needed)
    if (username === "admin" && password === "1234") {
        sessionStorage.setItem("isLoggedIn", true); // Mark user as logged in
        alert("Login successful!");
        window.location.href = "adminpage.html"; // Redirect to homepage
    } else {
        alert("Invalid username or password!");
    }
}
// Handle logout
function logout() {
    sessionStorage.removeItem("isLoggedIn"); // Clear login session
    alert("You have been logged out!");
    updateAuthButtons(); // Update header buttons
    window.location.href = "login.html"; // Redirect to login page
}

// Dynamically update the header buttons
function updateAuthButtons() {
    const isLoggedIn = sessionStorage.getItem("isLoggedIn");
    const loginButton = document.getElementById("loginButton");
    const logoutButton = document.getElementById("logoutButton");

    if (isLoggedIn) {
        // User is logged in: Show Logout, Hide Login
        if (loginButton) loginButton.style.display = "none";
        if (logoutButton) logoutButton.style.display = "block";
    } else {
        // User is not logged in: Show Login, Hide Logout
        if (loginButton) loginButton.style.display = "block";
        if (logoutButton) logoutButton.style.display = "none";
    }
}

// Add event listeners when the DOM is fully loaded
document.addEventListener("DOMContentLoaded", () => {
    // Automatically check login status on pages requiring authentication
    if (document.body.classList.contains("requires-auth")) {
        checkLoginStatus();
    }

    // Update the login/logout buttons on all pages
    updateAuthButtons();

    // Attach logout functionality to the Logout button
    const logoutButton = document.getElementById("logoutButton");
    if (logoutButton) {
        logoutButton.addEventListener("click", logout);
    }

    // Attach login form handling if on the login page
    const loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", handleLogin);
    }
});

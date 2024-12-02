document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");

    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            const email = document.getElementById("username").value.trim();
            const password = document.getElementById("password").value.trim();

            try {
                const response = await fetch("http://localhost:8080/api/users/login", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ email, password }),
                });

                if (response.ok) {
                    const data = await response.json();
                    sessionStorage.setItem("authToken", data.token); // Store the token
                    sessionStorage.setItem("userName", data.firstName); // Store user's name
                    alert(`Welcome back, ${data.firstName}!`);
                    window.location.href = "index.html"; // Redirect to homepage
                } else if (response.status === 401) {
                    alert("Invalid email or password. Please try again.");
                } else {
                    alert("An error occurred. Please try again.");
                }
            } catch (error) {
                console.error("Login error:", error);
                alert("Unable to connect to the server. Please try again later.");
            }
        });
    }
});



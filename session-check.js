document.addEventListener("DOMContentLoaded", async () => {
    try {
        const token = sessionStorage.getItem("authToken"); 

        if (!token) {
            throw new Error("No session token found. Redirecting to login...");
        }

        const response = await fetch("http://localhost:8080/api/users/validate-session", {
            method: "GET",
            headers: { Authorization: token },
        });

        if (response.ok) {
            const authLink = document.getElementById("authLink");
            const userName = sessionStorage.getItem("userName");

            if (authLink) {
                authLink.textContent = `Logout (${userName})`;
                authLink.href = "#";
                authLink.addEventListener("click", () => {
                    sessionStorage.clear(); 
                    window.location.href = "login.html"; 
                });
            }
        } else {
            throw new Error("Session invalid. Redirecting to login...");
        }
    } catch (error) {
        console.error(error.message);
        sessionStorage.clear();
        window.location.href = "login.html";
    }
});

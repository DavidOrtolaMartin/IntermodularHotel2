import { api } from "/js/core/api.js";

document.addEventListener("DOMContentLoaded", async () => {
    try {
        const user = await api.get("/api/me");

        console.log("USER LOGUEADO:", user);

        document.getElementById("user-name").textContent = user.name;

        document.getElementById("login-link").classList.add("d-none");
        document.getElementById("logout-link").classList.remove("d-none");

    } catch (e) {
        // no logueado
        document.getElementById("user-name").textContent = "";
    }
	
	// 🔥 LOGOUT
	    document.getElementById("logout-link").addEventListener("click", async () => {
	        try {
	            await api.post("/api/logout");

	            window.location.href = "/index.html"; // recarga limpio
	        } catch (e) {
	            console.error("Error logout:", e);
	        }
	    });
});
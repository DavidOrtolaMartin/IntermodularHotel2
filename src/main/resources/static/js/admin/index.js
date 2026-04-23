import { app } from "/js/core/app.js";
import { api } from "/js/core/api.js";

app.run(async () => {

    const user = await api.get("/api/me");

    if (user.role !== "ADMIN") {
        window.location.href = "/login.html";
        return;
    }

    document.getElementById("user-name").textContent = user.email;
});
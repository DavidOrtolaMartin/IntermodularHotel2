import { api } from "/js/core/api.js";

document.addEventListener("DOMContentLoaded", async () => {

    const misReservasBtn = document.getElementById("mis-reservas-btn");
    const loginLink = document.getElementById("login-link");
    const logoutLink = document.getElementById("logout-link");
    const userName = document.getElementById("user-name");
    const adminLink = document.getElementById("admin-link");

    try {
        const user = await api.get("/api/me");

        userName.textContent = user.name;

        loginLink.classList.add("d-none");
        logoutLink.classList.remove("d-none");

        // mostrar botón mis reservas
        misReservasBtn.classList.remove("d-none");

        // 🔥 mostrar botón admin si es ADMIN
        if (user.role === "ADMIN") {
            adminLink.classList.remove("d-none");
        }

    } catch (e) {
        console.log("Usuario no logueado");

        userName.textContent = "";

        // por seguridad
        if (adminLink) {
            adminLink.classList.add("d-none");
        }
    }

    // LOGOUT
    logoutLink.addEventListener("click", async () => {
        await api.post("/api/logout");
        window.location.href = "/index.html";
    });

    // FECHAS MÍNIMAS
    const hoy = new Date().toISOString().split("T")[0];

    const entrada = document.getElementById("fechaEntrada");
    const salida = document.getElementById("fechaSalida");

    entrada.setAttribute("min", hoy);
    salida.setAttribute("min", hoy);

    entrada.addEventListener("change", (e) => {
        salida.setAttribute("min", e.target.value);
    });

    // FORM RESERVA
    document.getElementById("form-reserva").addEventListener("submit", (e) => {
        e.preventDefault();

        const fechaEntrada = entrada.value;
        const fechaSalida = salida.value;

        if (!fechaEntrada || !fechaSalida) {
            alert("Debes seleccionar ambas fechas");
            return;
        }

        window.location.href =
            `/categorias/disponibles/index.html?desde=${fechaEntrada}&hasta=${fechaSalida}`;
    });

    // navegación mis reservas
    misReservasBtn.addEventListener("click", (e) => {
        e.preventDefault();
        window.location.href = "/mis-reservas.html";
    });

});
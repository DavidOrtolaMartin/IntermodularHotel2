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

        // mostrar botón admin si es ADMIN
        if (user.role === "ADMIN") {
            adminLink.classList.remove("d-none");
        }

    } catch (e) {

        console.log("Usuario no logueado");

        userName.textContent = "";

        // ocultar admin por seguridad
        if (adminLink) {
            adminLink.classList.add("d-none");
        }
    }

    // LOGOUT
    logoutLink.addEventListener("click", async () => {

        await api.post("/api/logout");

        window.location.href = "/index.html";
    });

    // FECHAS
    const hoy = new Date().toISOString().split("T")[0];

    const entrada = document.getElementById("fechaEntrada");
    const salida = document.getElementById("fechaSalida");

    // entrada mínimo hoy
    entrada.setAttribute("min", hoy);

    // salida mínimo mañana
    const manana = new Date();

    manana.setDate(manana.getDate() + 1);

    const mananaStr = manana.toISOString().split("T")[0];

    salida.setAttribute("min", mananaStr);

    // cuando cambia entrada
    entrada.addEventListener("change", () => {

        if (!entrada.value) return;

        // calcular día siguiente
        const fechaMinSalida = new Date(entrada.value);

        fechaMinSalida.setDate(fechaMinSalida.getDate() + 1);

        const minSalida =
            fechaMinSalida.toISOString().split("T")[0];

        // actualizar mínimo salida
        salida.setAttribute("min", minSalida);

        // si salida actual es inválida la vaciamos
        if (
            salida.value &&
            salida.value < minSalida
        ) {
            salida.value = "";
        }
    });

    // FORM RESERVA
    document.getElementById("form-reserva")
        .addEventListener("submit", (e) => {

        e.preventDefault();

        const fechaEntrada = entrada.value;
        const fechaSalida = salida.value;

        // comprobar fechas vacías
        if (!fechaEntrada || !fechaSalida) {

            alert("Debes seleccionar ambas fechas");

            return;
        }

        // impedir misma fecha o menor
        if (fechaSalida <= fechaEntrada) {

            alert(
                "La fecha de salida debe ser al menos un día posterior"
            );

            return;
        }

        // redirección
        window.location.href =
            `/categorias/disponibles/index.html?desde=${fechaEntrada}&hasta=${fechaSalida}`;
    });

    // MIS RESERVAS
    misReservasBtn.addEventListener("click", (e) => {

        e.preventDefault();

        window.location.href = "/mis-reservas.html";
    });

});
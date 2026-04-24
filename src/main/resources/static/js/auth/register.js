import { app } from "/js/core/app.js";
import { bind } from "/js/core/events.js";

// 🔹 Cargar provincias
async function loadProvincias() {
    try {
        const res = await fetch("/api/admin/users/provincias");
        const provincias = await res.json();

        const select = document.getElementById("provinciaId");

        provincias.forEach(p => {
            const option = document.createElement("option");
            option.value = p.id;
            option.textContent = p.nombre;
            select.appendChild(option);
        });

    } catch (err) {
        console.error("Error cargando provincias:", err);
    }
}

// 🔹 Registro
async function handleRegister(e) {
    e.preventDefault();

    const errorDiv = document.getElementById("error");
    const successDiv = document.getElementById("success");

    errorDiv.style.display = "none";
    successDiv.style.display = "none";

    const password = document.getElementById("password").value;
    const password2 = document.getElementById("password2").value;

    if (password !== password2) {
        errorDiv.textContent = "Las contraseñas no coinciden";
        errorDiv.style.display = "block";
        return;
    }

    const provinciaValue = document.getElementById("provinciaId").value;

    const user = {
        name: document.getElementById("name").value,
        apellido1: document.getElementById("apellido1").value,
        apellido2: document.getElementById("apellido2").value,
        email: document.getElementById("email").value,
        tlf1: document.getElementById("tlf1").value,
        tlf2: document.getElementById("tlf2").value,
        password: password,
        provincia_id: parseInt(provinciaValue)
    };

    console.log("REGISTER payload:", user);

    try {
        await fetch("/api/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(user)
        });

        successDiv.style.display = "block";

        setTimeout(() => {
            window.location.href = "/login.html";
        }, 1500);

    } catch (err) {
        console.error(err);
        errorDiv.textContent = "Error en el registro";
        errorDiv.style.display = "block";
    }
}

// 🔹 Init
app.run(() => {
    loadProvincias();
    const form = document.getElementById("form-register");
    bind(form, "submit", handleRegister);
});
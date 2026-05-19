import { api } from "/js/core/api.js";

// 🔹 Cargar provincias
async function loadProvincias() {
    try {
        const provincias = await api.get("/api/admin/users/provincias");

        const select = document.getElementById("provinciaId");

        provincias.forEach(p => {
            const option = document.createElement("option");
            option.value = String(p.id);
            option.textContent = p.nombre;
            select.appendChild(option);
        });

    } catch (err) {
        console.error("Error cargando provincias:", err);
    }
}

// 🔹 Submit
document.getElementById("form-user").addEventListener("submit", async (e) => {
    e.preventDefault();

    const provinciaValue = document.getElementById("provinciaId").value;

    const user = {
        name: document.getElementById("name").value,
        apellido1: document.getElementById("apellido1").value,
        apellido2: document.getElementById("apellido2").value,
        email: document.getElementById("email").value,
        tlf1: document.getElementById("tlf1").value,
        tlf2: document.getElementById("tlf2").value,
        role: document.getElementById("role").value,
        password: document.getElementById("password").value,

        // 🔥 IMPORTANTE (snake_case)
        provincia_id: parseInt(provinciaValue)
    };

    console.log("POST payload:", user);

    try {
        await api.post("/api/admin/users", user);
        alert("Usuario creado");
        window.location.href = "/admin/users/index.html";
    } catch (err) {
        console.error("Error creando:", err);
        alert("Error al guardar");
    }
});

// 🔹 Init
document.addEventListener("DOMContentLoaded", async () => {
    await loadProvincias();
});
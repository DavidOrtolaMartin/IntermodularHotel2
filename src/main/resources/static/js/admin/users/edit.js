import { api } from "/js/core/api.js";

const params = new URLSearchParams(window.location.search);
const id = params.get("id");

console.log("ID del usuario:", id);

// Cargar provincias en el selector
async function loadProvincias() {
    try {
        const provincias = await api.get("/api/admin/provincias"); // Endpoint que devuelve [{id:1, nombre:"Madrid"}, ...]
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

// Cargar datos del usuario
async function loadUser() {
    try {
        const user = await api.get(`/api/admin/users/${id}`);
        console.log("USER GET:", user);
        fillForm(user);
    } catch (err) {
        console.error("Error cargando usuario:", err);
    }
}

function fillForm(u) {
    document.getElementById("name").value = u.name || "";
    document.getElementById("apellido1").value = u.apellido1 || "";
    document.getElementById("apellido2").value = u.apellido2 || "";
    document.getElementById("email").value = u.email || "";
    document.getElementById("tlf1").value = u.tlf1 || "";
    document.getElementById("tlf2").value = u.tlf2 || "";
    document.getElementById("role").value = u.role || "";
    document.getElementById("provinciaId").value = u.provinciaId || 1; // 👈 match con DTO
}

// Submit
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
        provinciaId: provinciaValue ? parseInt(provinciaValue) : 1 // 👈 número seguro
    };

    console.log("PUT payload:", user);

    try {
        await api.put(`/api/admin/users/${id}`, user);
        alert("Usuario actualizado");
        window.location.href = "/admin/users/index.html";
    } catch (err) {
        console.error("Error actualizando:", err);
        alert("Error al guardar");
    }
});

// Inicialización
document.addEventListener("DOMContentLoaded", async () => {
    await loadProvincias();
    await loadUser();
});
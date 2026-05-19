import { api } from "/js/core/api.js";

const params = new URLSearchParams(window.location.search);
const id = params.get("id");

console.log("ID del usuario:", id);

// 🔹 Cargar provincias
async function loadProvincias() {
    try {
        const provincias = await api.get("/api/admin/users/provincias");

        const select = document.getElementById("provinciaId");

        provincias.forEach(p => {
            const option = document.createElement("option");
            option.value = String(p.id);        // 👈 lo que se guarda
            option.textContent = p.nombre; // 👈 lo que se muestra
			// ESTO TENGO QUE PONERLO ABAJO CON EL VALUE, HAGO UN BUCLE COMPARANDO EL ID CON EL NOMBRE Y SI COINCIDE HAGO SELECTED
            select.appendChild(option);
        });

    } catch (err) {
        console.error("Error cargando provincias:", err);
    }
}

// 🔹 Cargar usuario
async function loadUser() {
    try {
        const user = await api.get(`/api/admin/users/${id}`);
        console.log("USER GET:", user);
        fillForm(user);
    } catch (err) {
        console.error("Error cargando usuario:", err);
    }
}

// 🔹 Rellenar formulario
function fillForm(u) {
    document.getElementById("name").value = u.name || "";
    document.getElementById("apellido1").value = u.apellido1 || "";
    document.getElementById("apellido2").value = u.apellido2 || "";
    document.getElementById("email").value = u.email || "";
    document.getElementById("tlf1").value = u.tlf1 || "";
    document.getElementById("tlf2").value = u.tlf2 || "";
    document.getElementById("role").value = u.role || "";

    // 👇 ESTO ES LA CLAVE
    document.getElementById("provinciaId").value = String(u.provincia_id);
}

// 🔹 Submit
document.getElementById("form-user").addEventListener("submit", async (e) => {
    e.preventDefault();

    const provinciaValue = document.getElementById("provinciaId").value;
	
	console.log("PUT payload provinciaValue:", provinciaValue);


    const user = {
        name: document.getElementById("name").value,
        apellido1: document.getElementById("apellido1").value,
        apellido2: document.getElementById("apellido2").value,
        email: document.getElementById("email").value,
        tlf1: document.getElementById("tlf1").value,
        tlf2: document.getElementById("tlf2").value,
        role: document.getElementById("role").value,
        provincia_id: parseInt(provinciaValue) // HAY QUE PONERLO COMO ESTA EN LA BASE DE DATOS, que quiere transformarlo el 
    };

    console.log("PUT payload JLD1:", user);

    try {
        await api.put(`/api/admin/users/${id}`, user);
        alert("Usuario actualizado");
        window.location.href = "/admin/users/index.html";
    } catch (err) {
        console.error("Error actualizando:", err);
        alert("Error al guardar");
    }
});

// 🔹 Init
document.addEventListener("DOMContentLoaded", async () => {
    await loadProvincias(); // primero provincias
    await loadUser();       // luego usuario
});
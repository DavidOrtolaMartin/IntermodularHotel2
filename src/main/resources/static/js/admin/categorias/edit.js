import { api } from "/js/core/api.js";

const params = new URLSearchParams(window.location.search);
const id = params.get("id");

// 🔹 Cargar categoría
async function loadCategoria() {
    try {
        const c = await api.get(`/api/admin/categorias/${id}`);

        document.getElementById("nombre").value = c.nombre;
        document.getElementById("descripcion").value = c.descripcion;
        document.getElementById("precio").value = c.precio;

        // 🔥 mostrar valores actuales en label
        document.getElementById("label-nombre").textContent =
            `Nombre (actual: ${c.nombre})`;

        document.getElementById("label-descripcion").textContent =
            `Descripción (actual: ${c.descripcion})`;

        document.getElementById("label-precio").textContent =
            `Precio (actual: ${c.precio}€)`;

    } catch (err) {
        console.error("Error cargando categoría:", err);
    }
}

// 🔹 Submit
document.getElementById("form-categoria").addEventListener("submit", async (e) => {
    e.preventDefault();

    const nombre = document.getElementById("nombre").value;
    const descripcion = document.getElementById("descripcion").value;
    const precio = document.getElementById("precio").value;

    // 🔴 Validaciones
    if (!nombre) {
        alert("Introduce un nombre");
        return;
    }

    if (!precio || parseInt(precio) <= 0) {
        alert("El precio debe ser mayor que 0");
        return;
    }

    const data = {
        nombre: nombre,
        descripcion: descripcion,
        precio: parseInt(precio)
    };

    try {
        await api.put(`/api/admin/categorias/${id}`, data);
        alert("Categoría actualizada");
        window.location.href = "/admin/categorias/index.html";
    } catch (err) {
        console.error("Error actualizando:", err);
        alert("Error actualizando categoría");
    }
});

// 🔹 Init
document.addEventListener("DOMContentLoaded", async () => {
    await loadCategoria();
});
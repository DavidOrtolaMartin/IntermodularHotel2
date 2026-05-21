import { api } from "/js/core/api.js";

document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("form-categoria");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const nombre = document.getElementById("nombre").value.trim();
        const descripcion = document.getElementById("descripcion").value.trim();
        const precio = document.getElementById("precio").value;

        // Validaciones
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
            await api.post("/api/admin/categorias", data);
            alert("Categoría creada");
            window.location.href = "/admin/categorias/index.html";
        } catch (err) {
            console.error("Error creando categoría:", err);
            alert("Error creando categoría");
        }
    });
});
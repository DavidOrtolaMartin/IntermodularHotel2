import { api } from "/js/core/api.js";
import { e } from "/js/core/utils.js";

document.addEventListener("DOMContentLoaded", async () => {
    try {
        const categorias = await api.get("/api/admin/categorias");
        renderCategorias(categorias);
    } catch (err) {
        console.error("Error cargando categorías:", err);
    }
});

function renderCategorias(categorias) {
    const tbody = document.getElementById("tabla-categorias");
    tbody.innerHTML = "";

    if (!categorias || categorias.length === 0) {
        tbody.innerHTML = `<tr><td colspan="5" class="text-center">No hay categorías</td></tr>`;
        return;
    }

    categorias.forEach(c => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${e(c.id)}</td>
            <td>${e(c.nombre)}</td>
            <td>${e(c.descripcion)}</td>
            <td>${e(c.precio)}€</td>
            <td>
                <a href="/admin/categorias/edit.html?id=${c.id}" class="btn btn-sm btn-outline-primary">Editar</a>
                <button class="btn btn-sm btn-outline-danger" onclick="eliminarCategoria(${c.id})">Borrar</button>
            </td>
        `;

        tbody.appendChild(tr);
    });
}

// borrar
window.eliminarCategoria = async (id) => {
    if (!confirm("¿Seguro que quieres eliminar esta categoría?")) return;

    try {
        await api.delete(`/api/admin/categorias/${id}`);
        location.reload();
    } catch (err) {
        console.error("Error eliminando categoría:", err);
        alert("Error al eliminar");
    }
};
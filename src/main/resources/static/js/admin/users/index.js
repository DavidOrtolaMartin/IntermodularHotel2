import { api } from "/js/core/api.js";
import { e } from "/js/core/utils.js";

document.addEventListener("DOMContentLoaded", async () => {
    try {
        const usuarios = await api.get("/api/admin/users");
        renderUsuarios(usuarios);
    } catch (err) {
        console.error("Error al cargar usuarios:", err);
    }
});

function renderUsuarios(usuarios) {
    const tbody = document.getElementById("tabla-usuarios");
    tbody.innerHTML = "";

    if (!usuarios || usuarios.length === 0) {
        tbody.innerHTML = `<tr><td colspan="8" class="text-center text-muted">No hay usuarios</td></tr>`;
        return;
    }

    usuarios.forEach(u => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${e(u.id)}</td>
            <td>${e(u.name)}</td>
            <td>${e(u.apellido1)} ${e(u.apellido2)}</td>
            <td>${e(u.email)}</td>
            <td>${e(u.tlf1 || "")} ${e(u.tlf2 || "")}</td>
            <td>${e(u.role)}</td>
            <td>${e(u.provincia)}</td>
            <td>
                <a href="/admin/users/edit.html?id=${u.id}" class="btn btn-sm btn-outline-primary">Editar</a>
                <button class="btn btn-sm btn-outline-danger" onclick="eliminarUsuario(${u.id})">Borrar</button>
            </td>
        `;

        tbody.appendChild(tr);
    });
}

// Función para borrar usuario
window.eliminarUsuario = async (id) => {
    if (!confirm("¿Seguro que quieres eliminar este usuario?")) return;
    try {
        await api.delete(`/api/admin/users/${id}`);
        alert("Usuario eliminado");
        location.reload(); // recarga la lista
    } catch (err) {
        console.error("Error al eliminar usuario:", err);
        alert("No se pudo eliminar el usuario");
    }
};
import { api } from "/js/core/api.js";
import { e } from "/js/core/utils.js";

document.addEventListener("DOMContentLoaded", async () => {
    try {
        const habitaciones = await api.get("/api/admin/habitaciones");
        renderHabitaciones(habitaciones);
    } catch (err) {
        console.error("Error cargando habitaciones:", err);
    }
});

function renderHabitaciones(habitaciones) {
    const tbody = document.getElementById("tabla-habitaciones");
    tbody.innerHTML = "";

    if (!habitaciones || habitaciones.length === 0) {
        tbody.innerHTML = `<tr><td colspan="5" class="text-center">No hay habitaciones</td></tr>`;
        return;
    }

    habitaciones.forEach(h => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${e(h.id)}</td>
            <td>${e(h.numero)}</td>
            <td>${e(h.categoria)}</td>
            <td>${e(h.precio)}€</td>
            <td>
                <a href="/admin/habitaciones/edit.html?id=${h.id}" class="btn btn-sm btn-outline-primary">Editar</a>
                <button class="btn btn-sm btn-outline-danger" onclick="eliminarHabitacion(${h.id})">Borrar</button>
            </td>
        `;

        tbody.appendChild(tr);
    });
}

window.eliminarHabitacion = async (id) => {
    if (!confirm("¿Seguro que quieres eliminar esta habitación?")) return;

    try {
        await api.delete(`/api/admin/habitaciones/${id}`);
        location.reload();
    } catch (err) {
        console.error("Error eliminando habitación:", err);
        alert("Error al eliminar");
    }
};
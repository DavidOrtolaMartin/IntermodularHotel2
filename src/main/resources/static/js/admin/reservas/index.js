import { api } from "/js/core/api.js";

document.addEventListener("DOMContentLoaded", async () => {

    try {
        const reservas = await api.get("/api/admin/reservas");
        renderReservas(reservas);
    } catch (err) {
        console.error("Error cargando reservas:", err);
        alert("Error cargando reservas");
    }

});

function renderReservas(reservas) {

    const tbody = document.getElementById("tabla-reservas");
    tbody.innerHTML = "";

    if (!reservas || reservas.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="7" class="text-center text-muted">
                    No hay reservas
                </td>
            </tr>
        `;
        return;
    }

    reservas.forEach(r => {

        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${r.id_reserva}</td>
            <td>${r.user_id}</td>
            <td>${r.hab_id}</td>
            <td>${r.fecha_desde}</td>
            <td>${r.fecha_hasta}</td>
            <td>${r.pagado ? "Sí" : "No"}</td>

            <td>
                <a href="/admin/reservas/edit.html?id=${r.id_reserva}" 
                   class="btn btn-sm btn-outline-primary">
                    Editar
                </a>

                <button class="btn btn-sm btn-outline-danger"
                        onclick="eliminarReserva(${r.id_reserva})">
                    Borrar
                </button>
            </td>
        `;

        tbody.appendChild(tr);
    });
}

// 🔹 eliminar reserva
window.eliminarReserva = async (id) => {

    if (!confirm("¿Seguro que quieres eliminar esta reserva?")) return;

    try {
        await api.delete(`/api/admin/reservas/${id}`);
        alert("Reserva eliminada");
        location.reload();
    } catch (err) {
        console.error("Error eliminando reserva:", err);
        alert("Error al eliminar la reserva");
    }
};
import { api } from "/js/core/api.js";

document.addEventListener("DOMContentLoaded", async () => {

    await cargarReservas();

});

async function cargarReservas() {

    try {

        const reservas = await api.get("/api/reservas/mis-reservas");

        const container = document.getElementById("reservas-container");

        if (reservas.length === 0) {

            container.innerHTML = `
                <div class="alert alert-info">
                    No tienes reservas todavía
                </div>
            `;

            return;
        }

        container.innerHTML = reservas.map(r => `

            <div class="card mb-3 p-3">

                <h5>Habitación ${r.hab_id}</h5>

                <p>
                    <strong>Desde:</strong>
                    ${r.fecha_desde}
                </p>

                <p>
                    <strong>Hasta:</strong>
                    ${r.fecha_hasta}
                </p>

                <p>
                    <strong>Pagado:</strong>
                    ${r.pagado ? "Sí" : "No"}
                </p>

                <div class="d-flex gap-2">

                    <button
                        class="btn btn-warning"
                        onclick="mostrarEditar(
                            ${r.id_reserva},
                            '${r.fecha_desde}',
                            '${r.fecha_hasta}'
                        )"
                    >
                        Modificar
                    </button>

                    <button
                        class="btn btn-danger"
                        onclick="cancelarReserva(${r.id_reserva})"
                    >
                        Cancelar
                    </button>

                </div>

                <div
                    id="edit-${r.id_reserva}"
                    class="mt-3"
                    style="display:none"
                >

                    <input
                        type="date"
                        id="desde-${r.id_reserva}"
                        class="form-control mb-2"
                        value="${r.fecha_desde}"
                    >

                    <input
                        type="date"
                        id="hasta-${r.id_reserva}"
                        class="form-control mb-2"
                        value="${r.fecha_hasta}"
                    >

                    <button
                        class="btn btn-primary"
                        onclick="guardarCambios(${r.id_reserva})"
                    >
                        Guardar cambios
                    </button>

                </div>

            </div>

        `).join("");

        // 👇 bloquear fechas pasadas
        const hoy = new Date().toISOString().split("T")[0];

        reservas.forEach(r => {

            document.getElementById(
                `desde-${r.id_reserva}`
            ).min = hoy;

            document.getElementById(
                `hasta-${r.id_reserva}`
            ).min = hoy;

        });

    } catch (e) {

        console.error(e);

        alert("Error cargando reservas");
    }
}

window.mostrarEditar = (id) => {

    const div = document.getElementById(`edit-${id}`);

    div.style.display =
        div.style.display === "none"
            ? "block"
            : "none";
};

window.cancelarReserva = async (id) => {

    if (!confirm("¿Cancelar reserva?")) return;

    try {

        await api.delete(`/api/reservas/${id}`);

        alert("Reserva cancelada");

        await cargarReservas();

    } catch (e) {

        console.error(e);

        alert("Error cancelando reserva");
    }
};

window.guardarCambios = async (id) => {

    const fechaDesde =
        document.getElementById(`desde-${id}`).value;

    const fechaHasta =
        document.getElementById(`hasta-${id}`).value;

    try {

		const reservaOriginal = (
		    await api.get("/api/reservas/mis-reservas")
		).find(r => r.id_reserva === id);

		const reserva = {

		    hab_id: reservaOriginal.hab_id,

		    fecha_desde: fechaDesde,

		    fecha_hasta: fechaHasta
		};

		console.log("PUT RESERVA:", reserva);

		await api.put(
		    `/api/reservas/${id}`,
		    reserva
		);

        alert("Reserva modificada");

        await cargarReservas();

    } catch (e) {

        console.error(e);

        alert(
            "No se puede modificar la reserva en esas fechas"
        );
    }
};
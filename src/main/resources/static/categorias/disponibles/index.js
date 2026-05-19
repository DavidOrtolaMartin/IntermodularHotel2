import { api } from "/js/core/api.js";

document.addEventListener("DOMContentLoaded", async () => {

    const params = new URLSearchParams(window.location.search);

    const desde = params.get("desde");
    const hasta = params.get("hasta");

    document.getElementById("fechas-seleccionadas").textContent =
        `Desde ${desde} hasta ${hasta}`;

    try {
        const categorias = await api.get(
            `/api/reservas/disponibles?desde=${desde}&hasta=${hasta}`
        );

        const container = document.getElementById("lista-categorias");

        if (categorias.length === 0) {
            container.innerHTML = `
                <div class="alert alert-warning">
                    No hay habitaciones disponibles para esas fechas
                </div>
            `;
            return;
        }

        categorias.forEach(c => {
            const card = document.createElement("div");
            card.className = "col-md-4";

            card.innerHTML = `
                <div class="card shadow h-100">
                    <div class="card-body">
                        <h4>${c.nombre}</h4>

                        <p>
                            Precio por día:
                            <strong>${c.precio_por_dia}€</strong>
                        </p>

                        <p>
                            Precio total:
                            <strong>${c.precio_total}€</strong>
                        </p>

                        <button
                            class="btn btn-success w-100 btn-confirmar"
                            data-hab-id="${c.habitacion_id}"
                        >
                            Confirmar reserva
                        </button>
                    </div>
                </div>
            `;

            container.appendChild(card);
        });

        document.querySelectorAll(".btn-confirmar")
            .forEach(btn => {
                btn.addEventListener("click", async () => {

                    const habId = parseInt(
                        btn.getAttribute("data-hab-id")
                    );

                    try {
                        await api.post("/api/reservas/confirmar", {
                            hab_id: habId,
                            fecha_desde: desde,
                            fecha_hasta: hasta
                        });

                        alert("Reserva confirmada correctamente");

                        window.location.href = "/index.html";

                    } catch (e) {
                        console.error(e);
                        alert("Error al confirmar la reserva");
                    }
                });
            });

    } catch (e) {
        console.error(e);
        alert("Error cargando habitaciones");
    }
});
window.reservar = function(idCategoria) {
    alert("Aquí irá la reserva final de la categoría ID: " + idCategoria);
};
import { api } from "/js/core/api.js";

const params = new URLSearchParams(window.location.search);

const id = params.get("id");

// 🔹 Cargar reserva
async function loadReserva() {

    try {

        const reserva = await api.get(`/api/admin/reservas/${id}`);

        console.log("RESERVA:", reserva);

        fillForm(reserva);

    } catch (err) {

        console.error("Error cargando reserva:", err);

        alert("Error cargando reserva");
    }
}

// 🔹 Rellenar formulario
function fillForm(r) {

    document.getElementById("userId").value =
        r.user_id || "";

    document.getElementById("habId").value =
        r.hab_id || "";

    document.getElementById("fechaDesde").value =
        r.fecha_desde || "";

    document.getElementById("fechaHasta").value =
        r.fecha_hasta || "";

    document.getElementById("pagado").value =
        String(r.pagado);
}

// 🔹 Submit editar
document.getElementById("form-reserva")
.addEventListener("submit", async (e) => {

    e.preventDefault();

    const fechaDesde =
        document.getElementById("fechaDesde").value;

    const fechaHasta =
        document.getElementById("fechaHasta").value;

    // 🔹 Validar fechas
    if (fechaHasta < fechaDesde) {

        alert(
            "La fecha hasta no puede ser menor que la fecha desde"
        );

        return;
    }

    const reserva = {

        user_id: parseInt(
            document.getElementById("userId").value
        ),

        hab_id: parseInt(
            document.getElementById("habId").value
        ),

        fecha_desde: fechaDesde,

        fecha_hasta: fechaHasta,

        pagado:
            document.getElementById("pagado").value === "true"
    };

    console.log("PUT RESERVA:", reserva);

    try {

        await api.put(
            `/api/admin/reservas/${id}`,
            reserva
        );

        alert("Reserva actualizada");

        window.location.href =
            "/admin/reservas/index.html";

    } catch (err) {

        console.error(
            "Error actualizando reserva:",
            err
        );

        alert("Error actualizando reserva");
    }
});

// 🔹 Init
document.addEventListener(
    "DOMContentLoaded",
    async () => {

        // 🔹 bloquear fechas pasadas
        const hoy =
            new Date()
                .toISOString()
                .split("T")[0];

        document.getElementById("fechaDesde").min =
            hoy;

        document.getElementById("fechaHasta").min =
            hoy;

        await loadReserva();
    }
);
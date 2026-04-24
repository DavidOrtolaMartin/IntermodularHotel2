const params = new URLSearchParams(window.location.search);

const fechaEntrada = params.get("desde");
const fechaSalida = params.get("hasta");

document.addEventListener("DOMContentLoaded", () => {

    document.getElementById("fechas-seleccionadas").textContent =
        `Estancia desde ${fechaEntrada} hasta ${fechaSalida}`;

    // TEMPORAL:
    // luego esto vendrá de la API
    const categorias = [
        {
            id: 1,
            nombre: "Habitación Estándar",
            precio: 90,
            descripcion: "Habitación cómoda con vistas al jardín"
        },
        {
            id: 2,
            nombre: "Habitación Deluxe",
            precio: 140,
            descripcion: "Habitación amplia con vistas al mar"
        },
        {
            id: 3,
            nombre: "Suite Premium",
            precio: 220,
            descripcion: "Suite de lujo con terraza privada"
        }
    ];

    renderCategorias(categorias);
});

function renderCategorias(categorias) {
    const contenedor = document.getElementById("lista-categorias");

    categorias.forEach(cat => {
        contenedor.innerHTML += `
            <div class="col-md-4">
                <div class="card shadow h-100">
                    <div class="card-body">

                        <h4>${cat.nombre}</h4>

                        <p>${cat.descripcion}</p>

                        <h5>${cat.precio}€ / noche</h5>

                        <button
                            class="btn btn-primary mt-3"
                            onclick="reservar(${cat.id})">
                            Reservar esta
                        </button>

                    </div>
                </div>
            </div>
        `;
    });
}

window.reservar = function(idCategoria) {
    alert("Aquí irá la reserva final de la categoría ID: " + idCategoria);
};
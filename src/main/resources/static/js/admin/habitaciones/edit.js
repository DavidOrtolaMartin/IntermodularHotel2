import { api } from "/js/core/api.js";

const params = new URLSearchParams(window.location.search);
const id = params.get("id");

async function loadCategorias() {
    const categorias = await api.get("/api/admin/categorias");

    const select = document.getElementById("categoriaId");

    categorias.forEach(c => {
        const option = document.createElement("option");
        option.value = c.id;
        option.textContent = `${c.nombre} (${c.precio}€)`;
        select.appendChild(option);
    });
}

async function loadHabitacion() {
	const h = await api.get(`/api/admin/habitaciones/${id}`);

	   document.getElementById("numHabitacion").value = h.num_habitacion;
	   document.getElementById("categoriaId").value = h.categoria_id;
}

document.getElementById("form-habitacion").addEventListener("submit", async (e) => {
    e.preventDefault();

    const data = {
        num_habitacion: parseInt(document.getElementById("numHabitacion").value),
        categoria_id: parseInt(document.getElementById("categoriaId").value)
    };

	//console.log("DATA:", data); //para ver qué querry hace y cambiar de orden los datos segun lo q pide
    try {
        await api.put(`/api/admin/habitaciones/${id}`, data);
        alert("Habitación actualizada");
        window.location.href = "/admin/habitaciones/index.html";
    } catch (err) {
        console.error(err);
        alert("Error actualizando: el número de la habitación ya existe");
    }
});

document.addEventListener("DOMContentLoaded", async () => {
    await loadCategorias();
    await loadHabitacion();
});



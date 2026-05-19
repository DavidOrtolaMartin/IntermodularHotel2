import { api } from "/js/core/api.js";

document.addEventListener("DOMContentLoaded", () => {

    loadCategorias();

    const form = document.getElementById("form-habitacion");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

		const numHabitacion = document.getElementById("numHabitacion").value;
		const categoria = document.getElementById("categoriaId").value;

		if (!numHabitacion) {
		    alert("Introduce el número de habitación");
		    return;
		}

		if (!categoria) {
		    alert("Selecciona una categoría");
		    return;
		}

		const data = {
			num_habitacion: parseInt(numHabitacion),
			categoria_id: parseInt(categoria)
		};
		
        try {
            await api.post("/api/admin/habitaciones", data);
            alert("Habitación creada");
            window.location.href = "/admin/habitaciones/index.html";
        } catch (err) {
            console.error("Error creando habitación:", err);
            alert("Error creando habitación");
        }
    });
});


async function loadCategorias() {
    try {
        const categorias = await api.get("/api/admin/categorias");

        const select = document.getElementById("categoriaId");

        categorias.forEach(c => {
            const option = document.createElement("option");
            option.value = c.id;
            option.textContent = `${c.nombre} (${c.precio}€)`;
            select.appendChild(option);
        });
		
		

    } catch (err) {
        console.error("Error cargando categorías:", err);
    }
}


//error numHabitacion duplicado

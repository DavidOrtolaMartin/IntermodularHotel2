import { app } from "/js/core/app.js";
import { bind } from "/js/core/events.js";

// 🔹 Cargar provincias
async function loadProvincias() {
    try {
        const res = await fetch("/api/provincias");
        const provincias = await res.json();

        const select = document.getElementById("provinciaId");

        provincias.forEach(p => {
            const option = document.createElement("option");
            option.value = p.id;
            option.textContent = p.nombre;
            select.appendChild(option);
        });

    } catch (err) {
        console.error("Error cargando provincias:", err);
    }
}

// 🔹 Registro
async function handleRegister(e) {
    e.preventDefault();

    const errorDiv = document.getElementById("error");
    const successDiv = document.getElementById("success");

    errorDiv.style.display = "none";
    successDiv.style.display = "none";

    const password = document.getElementById("password").value;
    const password2 = document.getElementById("password2").value;

    if (password !== password2) {
        errorDiv.textContent = "Las contraseñas no coinciden";
        errorDiv.style.display = "block";
        return;
    }

    const provinciaValue = document.getElementById("provinciaId").value;

    const user = {
        name: document.getElementById("name").value,
        apellido1: document.getElementById("apellido1").value,
        apellido2: document.getElementById("apellido2").value,
        email: document.getElementById("email").value,
        tlf1: document.getElementById("tlf1").value,
        tlf2: document.getElementById("tlf2").value,
        password: password,
        provincia_id: parseInt(provinciaValue)
    };

    console.log("REGISTER payload:", user);

	try {

	    const res = await fetch("/api/register", {
	        method: "POST",
	        headers: {
	            "Content-Type": "application/json"
	        },
	        body: JSON.stringify(user)
	    });

	    // 👇 comprobar si ha fallado
	    if (!res.ok) {

	        let mensaje = "Error en el registro";

			try {

			    const data = await res.json();

			    mensaje = data.message || "Error en el registro";

			} catch {}

	        // 👇 email duplicado
			if (
			    mensaje.toLowerCase().includes("existe")
			    || mensaje.toLowerCase().includes("duplicate")
			    || mensaje.toLowerCase().includes("duplicado")
			) {

	            errorDiv.textContent = "Ese email ya está registrado";

	        } else {

	            errorDiv.textContent = mensaje;

	        }

	        errorDiv.style.display = "block";
	        return;
	    }

	    // 👇 éxito
	    successDiv.textContent =
	        "Registro realizado correctamente. Redirigiendo al login...";

	    successDiv.style.display = "block";

	    setTimeout(() => {
	        window.location.href = "/login.html";
	    }, 3000);

	} catch (err) {

	    console.error(err);

	    errorDiv.textContent = "Error de conexión";

	    errorDiv.style.display = "block";
	}
}
// 🔹 Init
app.run(() => {
    loadProvincias();
    const form = document.getElementById("form-register");
    bind(form, "submit", handleRegister);
});
import { bind } from "/js/core/events.js";

// CARGAR PROVINCIAS
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

// REGISTRO
async function handleRegister(e) {

    e.preventDefault();

    const errorDiv = document.getElementById("error");
    const successDiv = document.getElementById("success");

    errorDiv.style.display = "none";
    successDiv.style.display = "none";
    errorDiv.innerHTML = "";

    document.querySelectorAll(".is-invalid")
        .forEach(el => el.classList.remove("is-invalid"));

    const name = document.getElementById("name");
    const apellido1 = document.getElementById("apellido1");
    const apellido2 = document.getElementById("apellido2");
    const email = document.getElementById("email");
    const tlf1 = document.getElementById("tlf1");
    const tlf2 = document.getElementById("tlf2");
    const provincia = document.getElementById("provinciaId");
    const password = document.getElementById("password");
    const password2 = document.getElementById("password2");

    const errores = [];

    const soloLetras = /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+$/;
    const soloNumeros = /^[0-9]+$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // NOMBRE
    if (!name.value.trim()) {

        errores.push("El nombre es obligatorio");
        name.classList.add("is-invalid");

    } else if (!soloLetras.test(name.value)) {

        errores.push("El nombre solo puede contener letras");
        name.classList.add("is-invalid");
    }

    // APELLIDO 1
    if (!apellido1.value.trim()) {

        errores.push("El apellido 1 es obligatorio");
        apellido1.classList.add("is-invalid");

    } else if (!soloLetras.test(apellido1.value)) {

        errores.push("El apellido 1 solo puede contener letras");
        apellido1.classList.add("is-invalid");
    }

    // APELLIDO 2
    if (apellido2.value.trim() && !soloLetras.test(apellido2.value)) {

        errores.push("El apellido 2 solo puede contener letras");
        apellido2.classList.add("is-invalid");
    }

    // EMAIL
    if (!email.value.trim()) {

        errores.push("El email es obligatorio");
        email.classList.add("is-invalid");

    } else if (!emailRegex.test(email.value)) {

        errores.push("El email debe tener un formato válido (ejemplo@correo.com)");
        email.classList.add("is-invalid");
    }

    // TELÉFONO 1
    if (!tlf1.value.trim()) {

        errores.push("El teléfono 1 es obligatorio");
        tlf1.classList.add("is-invalid");

    } else if (!soloNumeros.test(tlf1.value)) {

        errores.push("El teléfono 1 solo puede contener números");
        tlf1.classList.add("is-invalid");
    }

    // TELÉFONO 2
    if (tlf2.value.trim() && !soloNumeros.test(tlf2.value)) {

        errores.push("El teléfono 2 solo puede contener números");
        tlf2.classList.add("is-invalid");
    }

    // PROVINCIA
    if (!provincia.value) {

        errores.push("Debes seleccionar una provincia");
        provincia.classList.add("is-invalid");
    }

    // PASSWORD
    if (!password.value.trim()) {

        errores.push("La contraseña es obligatoria");
        password.classList.add("is-invalid");
    }

    // CONFIRMAR PASSWORD
    if (!password2.value.trim()) {

        errores.push("Debes confirmar la contraseña");
        password2.classList.add("is-invalid");
    }

    // COMPARAR PASSWORDS
    if (
        password.value.trim()
        && password2.value.trim()
        && password.value !== password2.value
    ) {

        errores.push("Las contraseñas no coinciden");

        password.classList.add("is-invalid");
        password2.classList.add("is-invalid");
    }

    // MOSTRAR ERRORES
    if (errores.length > 0) {

        errorDiv.innerHTML = errores.join("<br>");
        errorDiv.style.display = "block";

        return;
    }

    const user = {
        name: name.value.trim(),
        apellido1: apellido1.value.trim(),
        apellido2: apellido2.value.trim(),
        email: email.value.trim(),
        tlf1: tlf1.value.trim(),
        tlf2: tlf2.value.trim(),
        password: password.value,
        provincia_id: parseInt(provincia.value)
    };

    try {

        const res = await fetch("/api/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(user)
        });

        if (!res.ok) {

            let mensaje = "Error en el registro";

            try {

                const data = await res.json();

                mensaje = data.message || mensaje;

            } catch {}

            // EMAIL DUPLICADO
            if (
                mensaje.toLowerCase().includes("duplicate")
                || mensaje.toLowerCase().includes("duplicado")
                || mensaje.toLowerCase().includes("existe")
            ) {

                mensaje = "Ese email ya está registrado";
                email.classList.add("is-invalid");
            }

            errorDiv.innerHTML = mensaje;
            errorDiv.style.display = "block";

            return;
        }

        successDiv.textContent =
            "Registro correcto. Redirigiendo al login...";

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

// INIT
loadProvincias();

const form = document.getElementById("form-register");

bind(form, "submit", handleRegister);
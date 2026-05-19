import { api } from "/js/core/api.js";

document.addEventListener("DOMContentLoaded", async () => {

    const id = obtenerId();

    await cargarCategoria(id);

    // ── Subir imagen ──────────────────────────────────────────────────
    document.getElementById("btn-subir").onclick = async () => {

        const input = document.getElementById("input-imagen");

        if (!input.files || !input.files[0]) {
            alert("Selecciona una imagen primero");
            return;
        }

        const formData = new FormData();
        formData.append("file", input.files[0]);

        try {
            await fetch(`/api/admin/categorias/${id}/imagenes`, {
                method: "POST",
                body: formData
                // NO ponemos Content-Type — el navegador lo pone solo con el boundary
            });

            input.value = ""; // limpia el input
            await cargarCategoria(id); // recarga las imágenes

        } catch (err) {
            console.error("Error subiendo imagen:", err);
            alert("Error al subir la imagen");
        }
    };
});

// ── Carga y pinta la categoría completa ──────────────────────────────────────

async function cargarCategoria(id) {

    const c = await api.get(`/api/admin/categorias/${id}`);

    document.getElementById("nombre").textContent      = c.nombre;
    document.getElementById("descripcion").textContent = c.descripcion;
    document.getElementById("precio").textContent      = c.precio;

    const imagenes      = c.imagenes ?? [];
    const imgPrincipal  = document.getElementById("imagen-principal");
    const galeria       = document.getElementById("galeria");

    if (!imagenes.length) {
        imgPrincipal.style.display = "none";
        galeria.innerHTML = "";
        return;
    }

    // imagen principal
    imgPrincipal.src           = imagenes[0].url;
    imgPrincipal.style.display = "block";
    imgPrincipal.style.cursor  = "pointer";
    imgPrincipal.onclick       = () => abrirModal(imagenes[0].url);

    // galería
    galeria.innerHTML = "";
    imagenes.slice(1).forEach(img => {
        const el        = document.createElement("img");
        el.src          = img.url;
        el.style.width  = "120px";
        el.style.cursor = "pointer";
        el.onclick      = () => abrirModal(img.url);
        galeria.appendChild(el);
    });

    // modal eventos (solo los registramos una vez)
    document.getElementById("cerrar-modal").onclick = cerrarModal;
    document.getElementById("modal").onclick = (e) => {
        if (e.target.id === "modal") cerrarModal();
    };
    document.addEventListener("keydown", (e) => {
        if (e.key === "Escape") cerrarModal();
    });
}

// ── Helpers ───────────────────────────────────────────────────────────────────

function abrirModal(url) {
    document.getElementById("modal-img").src        = url;
    document.getElementById("modal").style.display  = "flex";
}

function cerrarModal() {
    document.getElementById("modal").style.display = "none";
}

function obtenerId() {
    return new URLSearchParams(window.location.search).get("id");
}














/*import { api } from "/js/core/api.js";

document.addEventListener("DOMContentLoaded", async () => {

    const id = obtenerId();

    const c = await api.get(`/api/admin/categorias/${id}`);

    // datos básicos
    document.getElementById("nombre").textContent = c.nombre;
    document.getElementById("descripcion").textContent = c.descripcion;
    document.getElementById("precio").textContent = c.precio;

    const imagenes = c.imagenes;

    if (!imagenes || !imagenes.length) return;

    const imgPrincipal = document.getElementById("imagen-principal");
    const galeria = document.getElementById("galeria");

    // 🔥 imagen principal
    imgPrincipal.src = imagenes[0].url;
    imgPrincipal.style.display = "block";
    imgPrincipal.style.cursor = "pointer";
    imgPrincipal.onclick = () => abrirModal(imagenes[0].url);

    // 🔥 galería
    galeria.innerHTML = "";

    imagenes.slice(1).forEach(img => {

        const el = document.createElement("img");

        el.src = img.url;
        el.style.width = "120px";
        el.style.cursor = "pointer";

        el.onclick = () => abrirModal(img.url);

        galeria.appendChild(el);
    });

    // modal eventos
    document.getElementById("cerrar-modal").onclick = cerrarModal;

    document.getElementById("modal").onclick = (e) => {
        if (e.target.id === "modal") cerrarModal();
    };

    document.addEventListener("keydown", (e) => {
        if (e.key === "Escape") cerrarModal();
    });
});

// 🔧 helpers

function abrirModal(url) {
    const modal = document.getElementById("modal");
    const img = document.getElementById("modal-img");

    img.src = url;
    modal.style.display = "flex";
}

function cerrarModal() {
    document.getElementById("modal").style.display = "none";
}

function obtenerId() {
    const params = new URLSearchParams(window.location.search);
    return params.get("id");
}*/
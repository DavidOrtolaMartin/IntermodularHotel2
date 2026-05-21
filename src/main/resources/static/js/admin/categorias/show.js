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

    const imagenes     = c.imagenes ?? [];
    const placeholder  = document.getElementById("img-placeholder");
    const infoCard     = document.getElementById("info-card");
    const galeria      = document.getElementById("galeria");

    // ── Imagen principal en la card ───────────────────────────────────
    if (imagenes.length) {
        // Sustituir el placeholder por una <img> real
        const imgPrincipal = document.createElement("img");
        imgPrincipal.src       = imagenes[0].url;
        imgPrincipal.className = "info-card-img";
        imgPrincipal.alt       = "Imagen principal";
        imgPrincipal.style.cursor = "pointer";
        imgPrincipal.onclick   = () => abrirModal(imagenes[0].url);
        placeholder.replaceWith(imgPrincipal);
    }

    if (!imagenes.length) {
        galeria.innerHTML = "";
        return;
    }

    // ── Galería (resto de imágenes) ───────────────────────────────────
    galeria.innerHTML = "";
    imagenes.forEach((img, i) => {
        const item = document.createElement("div");
        item.className = "galeria-item";

        const el = document.createElement("img");
        el.src    = img.url;
        el.alt    = `Imagen ${i + 1}`;
        el.onclick = () => abrirModal(img.url);
        item.appendChild(el);

        if (i === 0) {
            const badge = document.createElement("span");
            badge.className   = "badge-principal";
            badge.textContent = "Principal";
            item.appendChild(badge);
        }

        galeria.appendChild(item);
    });

    // ── Modal eventos (una sola vez) ──────────────────────────────────
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
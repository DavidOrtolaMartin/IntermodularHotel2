import { api } from "/js/core/api.js";

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
}
import { api } from "/js/core/api.js";

const params = new URLSearchParams(window.location.search);
const id = params.get("id");

// ── Cargar categoría y pintar imágenes ───────────────────────────────────────

async function loadCategoria() {
    try {
        const c = await api.get(`/api/admin/categorias/${id}`);

        document.getElementById("nombre").value      = c.nombre;
        document.getElementById("descripcion").value = c.descripcion;
        document.getElementById("precio").value      = c.precio;

        renderImagenes(c.imagenes ?? []);

    } catch (err) {
        console.error("Error cargando categoría:", err);
    }
}

// ── Pintar galería con botón eliminar ────────────────────────────────────────

function renderImagenes(imagenes) {
    const galeria = document.getElementById("galeria-edit");
    galeria.innerHTML = "";

    if (!imagenes.length) {
        galeria.innerHTML = `<p class="text-muted">Sin imágenes</p>`;
        return;
    }

    imagenes.forEach(img => {
        const wrap = document.createElement("div");
        wrap.style.cssText = "position:relative; display:inline-block;";

        const el = document.createElement("img");
        el.src = img.url;
        el.style.cssText = "width:120px; height:90px; object-fit:cover; border-radius:6px; display:block;";

        const btn = document.createElement("button");
        btn.textContent = "✕";
        btn.title = "Eliminar imagen";
        btn.style.cssText = `
            position:absolute; top:4px; right:4px;
            background:rgba(220,53,69,0.85); color:#fff;
            border:none; border-radius:50%;
            width:24px; height:24px;
            font-size:0.75rem; cursor:pointer;
            display:flex; align-items:center; justify-content:center;
            padding:0;
        `;

        btn.onclick = async () => {
            if (!confirm("¿Eliminar esta imagen?")) return;
            try {
                await api.delete(`/api/admin/categorias/${id}/imagenes/${img.id}`);
                await loadCategoria();
            } catch (err) {
                console.error("Error eliminando imagen:", err);
                alert("Error al eliminar la imagen");
            }
        };

        wrap.appendChild(el);
        wrap.appendChild(btn);
        galeria.appendChild(wrap);
    });
}

// ── Guardar datos básicos ────────────────────────────────────────────────────

document.getElementById("form-categoria").addEventListener("submit", async (e) => {
    e.preventDefault();

    const nombre      = document.getElementById("nombre").value.trim();
    const descripcion = document.getElementById("descripcion").value.trim();
    const precio      = document.getElementById("precio").value;

    if (!nombre) {
        alert("El nombre no puede estar vacío");
        return;
    }

    if (!precio || parseInt(precio) <= 0) {
        alert("El precio debe ser mayor que 0");
        return;
    }

    const data = { nombre, descripcion, precio: parseInt(precio) };

    try {
        await api.put(`/api/admin/categorias/${id}`, data);
        alert("Categoría actualizada");
        window.location.href = "/admin/categorias/index.html";
    } catch (err) {
        console.error("Error actualizando:", err);
        alert("Error actualizando categoría");
    }
});

// ── Init: carga datos + botón subir imagen ───────────────────────────────────

document.addEventListener("DOMContentLoaded", async () => {

    await loadCategoria();

    document.getElementById("btn-subir").onclick = async () => {
        const input = document.getElementById("input-imagen");

        if (!input.files || !input.files[0]) {
            alert("Selecciona una imagen primero");
            return;
        }

        const formData = new FormData();
        formData.append("file", input.files[0]);

        try {
            const response = await fetch(`/api/admin/categorias/${id}/imagenes`, {
                method: "POST",
                body: formData
            });

			if (!response.ok) {
			    alert("Error al subir la imagen. Comprueba que sea jpg, png o webp y pese menos de 20MB.");
			    return;
			}

            input.value = "";
            await loadCategoria();

        } catch (err) {
            console.error("Error subiendo imagen:", err);
            alert("Error al subir la imagen");
        }
    };
});
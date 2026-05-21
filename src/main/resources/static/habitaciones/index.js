import { api } from "/js/core/api.js";
import { e }   from "/js/core/utils.js";

document.addEventListener("DOMContentLoaded", async () => {
    try {
        // 1. Listado de categorías (igual que el admin index)
        const categorias = await api.get("/api/categorias");

        // 2. Por cada categoría pedimos el detalle (igual que show.js)
        //    para obtener el array imagenes[]
        for (const c of categorias) {
            const detalle = await api.get(`/api/categorias/${c.id}`);
            c.imagenes = detalle.imagenes ?? [];
        }

        renderCategorias(categorias);

    } catch (err) {
        console.error("Error cargando categorías:", err);
    }
});

function renderCategorias(categorias) {
    const tbody = document.getElementById("tabla-categorias");
    tbody.innerHTML = "";

    if (!categorias || categorias.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4" class="text-center">No hay categorías</td></tr>`;
        return;
    }

    categorias.forEach(c => {
        const tr = document.createElement("tr");
        tr.style.verticalAlign = "middle";

        // Celda del carrusel — la construimos aparte porque necesita JS
        const tdCarrusel = document.createElement("td");
        tdCarrusel.style.width = "220px";
        tdCarrusel.appendChild(crearCarrusel(c.imagenes));

        const tdNombre = document.createElement("td");
        tdNombre.textContent = c.nombre;

        const tdDesc = document.createElement("td");
        tdDesc.textContent = c.descripcion;

        const tdPrecio = document.createElement("td");
        tdPrecio.textContent = c.precio + " €";

        tr.appendChild(tdCarrusel);
        tr.appendChild(tdNombre);
        tr.appendChild(tdDesc);
        tr.appendChild(tdPrecio);

        tbody.appendChild(tr);
    });
}

// ── Carrusel puro HTML + JS ───────────────────────────────────────────────────

function crearCarrusel(imagenes) {

    if (!imagenes || !imagenes.length) {
        const vacio = document.createElement("span");
        vacio.className = "text-muted";
        vacio.textContent = "-";
        return vacio;
    }

    let indice = 0;

    const wrap = document.createElement("div");
    wrap.style.cssText = "position:relative; width:200px; text-align:center;";

    const img = document.createElement("img");
    img.style.cssText = "width:200px; height:140px; object-fit:cover; border-radius:6px; display:block;";

    const contador = document.createElement("div");
    contador.style.cssText = "font-size:0.78rem; color:#666; margin-top:4px;";

    const miniaturas = document.createElement("div");
    miniaturas.style.cssText = "display:flex; gap:4px; margin-top:6px; flex-wrap:wrap; justify-content:center;";

    function ir(i) {
        indice = (i + imagenes.length) % imagenes.length;
        img.src = imagenes[indice].url;
        img.alt = "Imagen " + (indice + 1);
        contador.textContent = (indice + 1) + " / " + imagenes.length;

        Array.from(miniaturas.children).forEach((m, j) => {
            m.style.outline = j === indice ? "2px solid #0d6efd" : "2px solid transparent";
            m.style.opacity = j === indice ? "1" : "0.55";
        });
    }

    if (imagenes.length > 1) {
        const prev = document.createElement("button");
        prev.textContent = "‹";
        prev.style.cssText = estiloFlecha("left:4px;");
        prev.onclick = () => ir(indice - 1);

        const next = document.createElement("button");
        next.textContent = "›";
        next.style.cssText = estiloFlecha("right:4px;");
        next.onclick = () => ir(indice + 1);

        wrap.appendChild(prev);
        wrap.appendChild(next);

        imagenes.forEach((imagen, i) => {
            const thumb = document.createElement("img");
            thumb.src = imagen.url;
            thumb.style.cssText = "width:40px; height:30px; object-fit:cover; border-radius:3px; cursor:pointer;";
            thumb.onclick = () => ir(i);
            miniaturas.appendChild(thumb);
        });
    }

    wrap.appendChild(img);
    wrap.appendChild(contador);
    if (imagenes.length > 1) wrap.appendChild(miniaturas);

    ir(0);

    return wrap;
}

function estiloFlecha(posicion) {
    return `position:absolute; top:55px; ${posicion}
        z-index:10; background:rgba(0,0,0,0.45); color:#fff;
        border:none; border-radius:50%; width:28px; height:28px;
        font-size:1.2rem; cursor:pointer; padding:0;
        display:flex; align-items:center; justify-content:center;`;
}
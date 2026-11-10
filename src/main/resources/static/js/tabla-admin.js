// Búsqueda y paginación reutilizable para las tablas de Administración.
window.iniciarTablaAdmin = function (config) {
    const cuerpo = document.getElementById(config.cuerpo);
    if (!cuerpo) return;

    const buscar = document.getElementById(config.buscar);
    const btnBuscar = document.getElementById(config.btnBuscar);
    const contador = document.getElementById(config.contador);
    const paginas = document.getElementById(config.paginas);
    const anterior = document.getElementById(config.anterior);
    const siguiente = document.getElementById(config.siguiente);
    const sinResultados = document.getElementById(config.sinResultados);
    const porPagina = config.porPagina || 4;

    // Todas las filas de datos (todas menos la de "sin resultados")
    const filas = Array.from(cuerpo.querySelectorAll("tr")).filter(fila => fila !== sinResultados);

    // Minúsculas y sin tildes, para que "mecanico" encuentre "Mecánico"
    const normalizar = texto => texto
        .toLocaleLowerCase("es")
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "");

    const textos = new Map(filas.map(fila => [fila, normalizar(fila.textContent)]));

    let paginaActual = 1;
    let filtrados = [...filas];

    function renderizar() {
        const total = filtrados.length;
        const totalPaginas = Math.ceil(total / porPagina);

        paginaActual = Math.max(1, Math.min(paginaActual, totalPaginas || 1));

        filas.forEach(fila => fila.hidden = true);

        const inicio = (paginaActual - 1) * porPagina;
        filtrados.slice(inicio, inicio + porPagina).forEach(fila => fila.hidden = false);

        sinResultados.hidden = total !== 0;

        contador.textContent = total === 0
            ? `No hay ${config.plural} para mostrar`
            : `Mostrando ${inicio + 1} a ${Math.min(inicio + porPagina, total)} de ${total} ${config.plural}`;

        anterior.disabled = paginaActual === 1 || total === 0;
        siguiente.disabled = paginaActual === totalPaginas || total === 0;

        paginas.replaceChildren();

        for (let i = 1; i <= totalPaginas; i++) {
            const boton = document.createElement("button");
            boton.type = "button";
            boton.className = "admin-pagina-btn" + (i === paginaActual ? " activa" : "");
            boton.textContent = i;
            boton.setAttribute("aria-label", `Página ${i}`);
            boton.addEventListener("click", () => {
                paginaActual = i;
                renderizar();
            });
            paginas.appendChild(boton);
        }
    }

    function filtrar() {
        const termino = normalizar(buscar.value.trim());
        filtrados = filas.filter(fila => textos.get(fila).includes(termino));
        paginaActual = 1;
        renderizar();
    }

    buscar.addEventListener("input", filtrar);
    btnBuscar.addEventListener("click", filtrar);

    anterior.addEventListener("click", () => {
        if (paginaActual > 1) {
            paginaActual--;
            renderizar();
        }
    });

    siguiente.addEventListener("click", () => {
        if (paginaActual < Math.ceil(filtrados.length / porPagina)) {
            paginaActual++;
            renderizar();
        }
    });

    renderizar();
};
document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("formOrden");
    // Este archivo se carga en todas las pestañas: solo actúa en Órdenes
    if (!form) return;

    const esNueva = form.dataset.nueva === "true";
    const urlBase = form.dataset.urlBase;

    const selectorOrden = document.getElementById("selectorOrden");
    const selectorCita = document.getElementById("selectorCita");
    const diagnostico = document.getElementById("diagnostico");
    const listaServicios = document.getElementById("listaServicios");
    const cuerpoArticulos = document.getElementById("cuerpoArticulos");
    const nuevoServicio = document.getElementById("nuevoServicio");
    const nuevoArticulo = document.getElementById("nuevoArticulo");

    const moneda = valor => "S/ " + Number(valor).toLocaleString("es-PE", {
        minimumFractionDigits: 2, maximumFractionDigits: 2
    });

    // ---------- Totales ----------
    function actualizarTotales() {
        let totalServicios = 0;
        let totalArticulos = 0;

        listaServicios.querySelectorAll(".service-row").forEach(fila => {
            totalServicios += Number(fila.dataset.precio) || 0;
        });

        cuerpoArticulos.querySelectorAll("tr").forEach(fila => {
            const precio = Number(fila.dataset.precio) || 0;
            let cantidad = Number(fila.querySelector(".cantidad-articulo").value);
            if (!Number.isFinite(cantidad) || cantidad < 0) cantidad = 0;

            const subtotal = precio * cantidad;
            fila.querySelector(".subtotal-articulo").textContent = moneda(subtotal);
            totalArticulos += subtotal;
        });

        document.getElementById("subtotalServicios").textContent = moneda(totalServicios);
        document.getElementById("subtotalArticulos").textContent = moneda(totalArticulos);
        document.getElementById("totalOrden").textContent = moneda(totalServicios + totalArticulos);
    }

    // ---------- Cambiar de orden ----------
    selectorOrden.addEventListener("change", () => {
        const id = selectorOrden.value;
        window.location.href = id ? `${urlBase}?id=${encodeURIComponent(id)}` : urlBase;
    });

    // ---------- Orden nueva: datos de la cita elegida ----------
    let motivoAutomatico = "";

    function mostrarVehiculo(datos) {
        const texto = valor => (valor && valor !== "null") ? valor : "—";
        document.getElementById("vMarca").textContent = texto(datos.marca);
        document.getElementById("vPlaca").textContent = texto(datos.placa);
        document.getElementById("vCliente").textContent = texto(datos.cliente);
        document.getElementById("vAnio").textContent = texto(datos.anio);
        document.getElementById("vKm").textContent = texto(datos.km);
    }

    if (selectorCita) {
        selectorCita.addEventListener("change", () => {
            const opcion = selectorCita.selectedOptions[0];

            if (!opcion || !opcion.value) {
                mostrarVehiculo({});
                return;
            }

            mostrarVehiculo(opcion.dataset);

            // Propone el motivo de la cita como diagnóstico inicial (sin pisar lo que el mecánico escribió)
            if (!diagnostico.value.trim() || diagnostico.value === motivoAutomatico) {
                motivoAutomatico = opcion.dataset.motivo || "";
                diagnostico.value = motivoAutomatico;
            }
        });
    }

    // ---------- Servicios ----------
    document.getElementById("btnAgregarServicio").addEventListener("click", () => {
        const opcion = nuevoServicio.selectedOptions[0];

        if (!opcion || !opcion.value) {
            alert("Selecciona un servicio para agregar.");
            return;
        }

        const repetido = [...listaServicios.querySelectorAll('input[name="servicioIds"]')]
            .some(input => input.value === opcion.value);

        if (repetido) {
            alert("Este servicio ya está agregado.");
            return;
        }

        const precio = Number(opcion.dataset.precio) || 0;
        const fila = document.getElementById("tplServicio").content.firstElementChild.cloneNode(true);

        fila.dataset.precio = precio;
        fila.querySelector('input[name="servicioIds"]').value = opcion.value;
        fila.querySelector(".service-name").textContent = opcion.dataset.nombre;
        fila.querySelector(".service-price").textContent = moneda(precio);

        listaServicios.appendChild(fila);
        nuevoServicio.value = "";
        actualizarTotales();
    });

    // ---------- Artículos ----------
    document.getElementById("btnAgregarArticulo").addEventListener("click", () => {
        const opcion = nuevoArticulo.selectedOptions[0];

        if (!opcion || !opcion.value) {
            alert("Selecciona un artículo para agregar.");
            return;
        }

        // Si ya está en la tabla, suma una unidad
        const existente = [...cuerpoArticulos.querySelectorAll("tr")].find(fila =>
            fila.querySelector('input[name="articuloIds"]').value === opcion.value);

        if (existente) {
            const cantidad = existente.querySelector(".cantidad-articulo");
            cantidad.value = (Number(cantidad.value) + 1).toFixed(2).replace(/\.00$/, "");
        } else {
            const precio = Number(opcion.dataset.precio) || 0;
            const fila = document.getElementById("tplArticulo").content.firstElementChild.cloneNode(true);

            fila.dataset.precio = precio;
            fila.querySelector('input[name="articuloIds"]').value = opcion.value;
            fila.querySelector(".articulo-nombre").textContent = opcion.dataset.nombre;
            fila.querySelector(".articulo-unidad").textContent = opcion.dataset.unidad || "";
            fila.querySelector(".articulo-precio").textContent = moneda(precio);
            fila.querySelector(".cantidad-articulo").value = "1";

            cuerpoArticulos.appendChild(fila);
        }

        nuevoArticulo.value = "";
        actualizarTotales();
    });

    // ---------- Quitar filas y cambiar cantidades ----------
    form.addEventListener("click", event => {
        const boton = event.target.closest(".js-quitar");
        if (!boton) return;

        boton.closest(".service-row, tr").remove();
        actualizarTotales();
    });

    form.addEventListener("input", event => {
        if (event.target.classList.contains("cantidad-articulo")) actualizarTotales();
    });

    // ---------- Envío ----------
    form.addEventListener("submit", event => {
        if (esNueva && (!selectorCita || !selectorCita.value)) {
            event.preventDefault();
            alert("Selecciona la cita que vas a atender.");
            return;
        }

        if (!form.querySelector('input[name="servicioIds"], input[name="articuloIds"]')) {
            event.preventDefault();
            alert("Agrega al menos un servicio o un artículo.");
            return;
        }

        if (esNueva && !confirm("¿Finalizar y guardar esta orden? La cita pasará a atendida.")) {
            event.preventDefault();
        }
    });

    actualizarTotales();
});
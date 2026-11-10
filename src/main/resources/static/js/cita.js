document.addEventListener("DOMContentLoaded", function () {
    const calendarEl = document.getElementById("calendar");
    // Este archivo se carga en todas las pestañas: solo actúa en Citas
    if (!calendarEl || typeof FullCalendar === "undefined") return;

    // ---------- Elementos ----------
    const pagina = document.getElementById("citasPagina");
    const tituloPanel = document.getElementById("tituloPanelCitas");
    const tituloFecha = document.getElementById("fechaTextoSeleccionada");
    const contenedorLista = document.getElementById("contenedorCitasDia");
    const detalle = document.getElementById("detalleCita");
    const totalEl = document.getElementById("totalCitasProgramadas");

    const modalCita = bootstrap.Modal.getOrCreateInstance(document.getElementById("modalCita"));
    const formCita = document.getElementById("formCita");
    const urlRegistrar = formCita.getAttribute("action");
    const urlModificar = formCita.dataset.urlModificar;
    const tituloModal = document.getElementById("tituloModalCita");
    const textoGuardar = document.getElementById("textoGuardarCita");
    const campoId = document.getElementById("citaId");
    const selCliente = document.getElementById("citaCliente");
    const selVehiculo = document.getElementById("citaVehiculo");
    const campoFecha = document.getElementById("citaFecha");
    const campoHora = document.getElementById("citaHora");
    const selContacto = document.getElementById("citaContacto");
    const campoMotivo = document.getElementById("citaMotivo");

    const MEDIOS = { PRESENCIAL: "Presencial", WHATSAPP: "WhatsApp", FACEBOOK: "Facebook", TELEFONO: "Teléfono" };
    const ESTADOS = { PENDIENTE: "Pendiente", ATENDIDA: "Atendida", CANCELADA: "Cancelada" };

    // ---------- Estado ----------
    let fechaSeleccionada = pagina.dataset.fecha || fechaLocal(new Date());
    const citaInicial = pagina.dataset.cita ? Number(pagina.dataset.cita) : null;
    let inicialResuelto = false;
    let vista = "lista";            // "lista" o "detalle"
    let citaActual = null;
    let cargaVehiculosId = 0;
    const citasPorId = new Map();   // todas las citas cargadas hasta ahora

    // ---------- Utilidades ----------
    function fechaLocal(fecha) {
        const anio = fecha.getFullYear();
        const mes = String(fecha.getMonth() + 1).padStart(2, "0");
        const dia = String(fecha.getDate()).padStart(2, "0");
        return `${anio}-${mes}-${dia}`;
    }

    function hora5(hora) {
        return (hora || "").substring(0, 5);
    }

    function textoFecha(fechaStr) {
        return new Date(fechaStr + "T00:00:00").toLocaleDateString("es-PE", {
            weekday: "long", day: "numeric", month: "long", year: "numeric"
        });
    }

    function aplicarEstado(elemento, estado) {
        elemento.className = "cita-estado estado-" + estado.toLowerCase();
        elemento.textContent = ESTADOS[estado] || estado;
    }

    // ---------- Calendario ----------
    const calendario = new FullCalendar.Calendar(calendarEl, {
        initialView: "dayGridMonth",
        initialDate: pagina.dataset.fecha || fechaLocal(new Date()),
        locale: "es",
        firstDay: 1,
        height: "auto",
        fixedWeekCount: false,
        dayMaxEvents: 2,
        eventDisplay: "block",
        displayEventTime: false,
        allDaySlot: false,
        slotMinTime: "07:00:00",   // ajusta al horario del taller
        slotMaxTime: "21:00:00",
        headerToolbar: {
            left: "prev,title,next",
            center: "",
            right: "today dayGridMonth,timeGridWeek"
        },
        buttonText: { today: "Hoy", month: "Mes", week: "Semana" },

        events: cargarEventos,
        datesSet: marcarDia,

        dateClick: function (info) {
            fechaSeleccionada = info.dateStr.slice(0, 10);
            marcarDia();
            mostrarLista();
        },

        eventClick: function (info) {
            mostrarDetalle(info.event.extendedProps.cita);
        }
    });

    calendario.render();

    function aEvento(c) {
        return {
            id: String(c.idCita),
            title: `${hora5(c.hora)} · ${c.placa}`,
            start: `${c.fecha}T${c.hora}`,
            classNames: ["evento-" + c.estado.toLowerCase()],
            extendedProps: { cita: c }
        };
    }

    // El calendario pide solo las citas del rango que está mostrando
    async function cargarEventos(info, exito, fallo) {
        try {
            const desde = info.startStr.slice(0, 10);
            const hasta = info.endStr.slice(0, 10);
            const respuesta = await fetch(`/citas/api/listar?desde=${desde}&hasta=${hasta}`);

            if (!respuesta.ok) {
                throw new Error("Error HTTP: " + respuesta.status);
            }

            const citas = await respuesta.json();
            citas.forEach(c => citasPorId.set(c.idCita, c));

            exito(citas.map(aEvento));
            despuesDeCargar(citas);
        } catch (error) {
            console.error("Error al cargar las citas:", error);
            fallo(error);
            alert("No se pudieron cargar las citas.");
        }
    }

    function despuesDeCargar(citas) {
        totalEl.textContent = citas.length;

        // Primera carga: abrir el detalle de la cita que acabamos de guardar
        if (!inicialResuelto) {
            inicialResuelto = true;
            const inicial = citaInicial ? citasPorId.get(citaInicial) : null;

            if (inicial) {
                mostrarDetalle(inicial);
                return;
            }
        }

        marcarDia();
        if (vista === "lista") mostrarLista();
    }

    // Resalta en el calendario el día seleccionado
    function marcarDia() {
        calendarEl.querySelectorAll(".dia-seleccionado")
            .forEach(el => el.classList.remove("dia-seleccionado"));

        calendarEl.querySelectorAll(
            `.fc-daygrid-day[data-date="${fechaSeleccionada}"], .fc-timegrid-col[data-date="${fechaSeleccionada}"]`
        ).forEach(el => el.classList.add("dia-seleccionado"));
    }

    // ---------- Panel derecho: lista del día ----------
    function mostrarLista() {
        vista = "lista";
        citaActual = null;

        detalle.hidden = true;
        contenedorLista.hidden = false;
        tituloFecha.hidden = false;
        tituloPanel.textContent = "CITAS DEL DÍA";
        tituloFecha.textContent = textoFecha(fechaSeleccionada);
        contenedorLista.replaceChildren();

        const citas = [...citasPorId.values()]
            .filter(c => c.fecha === fechaSeleccionada)
            .sort((a, b) => a.hora.localeCompare(b.hora));

        if (citas.length === 0) {
            const vacio = document.createElement("div");
            vacio.className = "empty-appointments";

            const icono = document.createElement("i");
            icono.className = "bi bi-calendar2-x";

            const texto = document.createElement("p");
            texto.textContent = "No hay citas registradas para esta fecha.";

            vacio.append(icono, texto);
            contenedorLista.appendChild(vacio);
            return;
        }

        citas.forEach(c => contenedorLista.appendChild(crearItemLista(c)));
    }

    function crearItemLista(c) {
        const boton = document.createElement("button");
        boton.type = "button";
        boton.className = "cita-lista-item" + (c.estado === "CANCELADA" ? " appointment-cancelled" : "");

        const hora = document.createElement("span");
        hora.className = "appointment-time";
        hora.textContent = hora5(c.hora);

        const info = document.createElement("span");
        info.className = "cita-lista-info";

        const codigo = document.createElement("strong");
        codigo.textContent = c.codigo;
        info.append(codigo, `${c.placa} · ${c.nombreCliente}`);

        const estado = document.createElement("span");
        aplicarEstado(estado, c.estado);

        boton.append(hora, info, estado);
        boton.addEventListener("click", () => mostrarDetalle(c));
        return boton;
    }

    // ---------- Panel derecho: detalle de la cita ----------
    function mostrarDetalle(c) {
        vista = "detalle";
        citaActual = c;
        fechaSeleccionada = c.fecha;
        marcarDia();

        contenedorLista.hidden = true;
        tituloFecha.hidden = true;
        detalle.hidden = false;
        tituloPanel.textContent = "DETALLE DE LA CITA";

        document.getElementById("detCodigo").textContent = c.codigo;
        aplicarEstado(document.getElementById("detEstado"), c.estado);
        document.getElementById("detFechaHora").textContent = `${textoFecha(c.fecha)} · ${hora5(c.hora)}`;
        document.getElementById("detCliente").textContent = c.nombreCliente || "No registrado";
        document.getElementById("detVehiculo").textContent = c.marcaModelo || "No registrado";
        document.getElementById("detPlaca").textContent = c.placa || "No registrada";
        document.getElementById("detContacto").textContent = MEDIOS[c.medioContacto] || c.medioContacto;
        document.getElementById("detMotivo").textContent = c.motivo;

        const pendiente = c.estado === "PENDIENTE";
        document.getElementById("detAcciones").hidden = !pendiente;

        const sinAcciones = document.getElementById("detSinAcciones");
        sinAcciones.hidden = pendiente;
        sinAcciones.textContent = c.estado === "ATENDIDA"
            ? "Esta cita ya fue atendida."
            : "Esta cita fue cancelada.";
    }

    document.getElementById("btnVolverLista").addEventListener("click", mostrarLista);

    // ---------- Modal agendar / modificar ----------
    function abrirModalAgendar() {
        const hoy = fechaLocal(new Date());

        formCita.reset();
        formCita.action = urlRegistrar;
        campoId.value = "";
        tituloModal.textContent = "Agendar nueva cita";
        textoGuardar.textContent = "Guardar cita";
        campoFecha.min = hoy;
        // Propone el día que está seleccionado en el calendario
        campoFecha.value = fechaSeleccionada >= hoy ? fechaSeleccionada : hoy;

        cargarVehiculos(null);
        modalCita.show();
    }

    async function abrirModalEditar(c) {
        formCita.reset();
        formCita.action = urlModificar;
        campoId.value = c.idCita;
        tituloModal.textContent = "Modificar cita " + c.codigo;
        textoGuardar.textContent = "Guardar cambios";
        campoFecha.min = fechaLocal(new Date());

        selCliente.value = String(c.idCliente);
        campoFecha.value = c.fecha;
        campoHora.value = hora5(c.hora);
        selContacto.value = c.medioContacto;
        campoMotivo.value = c.motivo;

        modalCita.show();
        await cargarVehiculos(c.idCliente, c.idVehiculo);
    }

    function agregarOpcion(select, valor, texto) {
        const opcion = document.createElement("option");
        opcion.value = valor;
        opcion.textContent = texto;
        select.appendChild(opcion);
    }

    // Carga los vehículos activos del cliente elegido
    async function cargarVehiculos(idCliente, idSeleccionado) {
        const peticion = ++cargaVehiculosId;
        selVehiculo.replaceChildren();

        if (!idCliente) {
            selVehiculo.disabled = true;
            agregarOpcion(selVehiculo, "", "Primero selecciona un cliente");
            return;
        }

        selVehiculo.disabled = true;
        agregarOpcion(selVehiculo, "", "Cargando vehículos...");

        try {
            const respuesta = await fetch(`/citas/api/vehiculos?clienteId=${encodeURIComponent(idCliente)}`);

            if (!respuesta.ok) {
                throw new Error("Error HTTP: " + respuesta.status);
            }

            const vehiculos = await respuesta.json();

            // Si el usuario cambió de cliente mientras cargaba, se descarta esta respuesta
            if (peticion !== cargaVehiculosId) return;

            selVehiculo.replaceChildren();

            if (vehiculos.length === 0) {
                agregarOpcion(selVehiculo, "", "Este cliente no tiene vehículos activos");
                return;
            }

            agregarOpcion(selVehiculo, "", "Selecciona un vehículo");
            vehiculos.forEach(v => agregarOpcion(selVehiculo, v.idVehiculo,
                `${v.placa} - ${v.marca} ${v.modelo}`));

            selVehiculo.disabled = false;
            if (idSeleccionado) selVehiculo.value = String(idSeleccionado);

        } catch (error) {
            console.error("Error al cargar vehículos:", error);
            if (peticion !== cargaVehiculosId) return;
            selVehiculo.replaceChildren();
            agregarOpcion(selVehiculo, "", "No se pudieron cargar los vehículos");
        }
    }

    selCliente.addEventListener("change", () => cargarVehiculos(selCliente.value));
    document.getElementById("btnAgendarCita").addEventListener("click", abrirModalAgendar);
    document.getElementById("btnModificarCita").addEventListener("click", () => {
        if (citaActual) abrirModalEditar(citaActual);
    });

    // ---------- Modal cancelar ----------
    document.getElementById("modalCancelarCita").addEventListener("show.bs.modal", function () {
        if (!citaActual) return;

        document.getElementById("cancelarIdCita").value = citaActual.idCita;
        document.getElementById("cancelarDescripcion").textContent =
            `${citaActual.codigo} · ${citaActual.placa} · ${citaActual.fecha} ${hora5(citaActual.hora)}`;
    });
});
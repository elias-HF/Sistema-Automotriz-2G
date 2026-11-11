document.addEventListener("DOMContentLoaded", function () {

    // MODAL DESACTIVAR
    const modalDesactivar = document.getElementById("modalDesactivarServicio");

    if (modalDesactivar) {
        modalDesactivar.addEventListener("show.bs.modal", function (event) {
            const boton = event.relatedTarget;

            if (!boton || !boton.dataset.id) {
                console.error("No se recibió el ID del servicio para desactivar.");
                return;
            }

            document.getElementById("desactivarIdServicio").value = boton.dataset.id;
            document.getElementById("desactivarNombreServicio").textContent = boton.dataset.nombre;
        });
    }

    // MODAL EDITAR
    const modalEditar = document.getElementById("modalEditarServicio");

    if (modalEditar) {
        modalEditar.addEventListener("show.bs.modal", async function (event) {
            const boton = event.relatedTarget;

            if (!boton || !boton.dataset.id) {
                console.error("No se recibió el ID del servicio para modificar.");
                return;
            }

            const form = modalEditar.querySelector("form");

            try {
                const respuesta = await fetch(`/servicios/buscar/${boton.dataset.id}`);

                if (!respuesta.ok) {
                    throw new Error("Error HTTP: " + respuesta.status);
                }

                const s = await respuesta.json();

                form.querySelector('[name="idServicio"]').value = s.idServicio;
                form.querySelector('[name="nombre"]').value = s.nombre;
                form.querySelector('[name="categoria"]').value = s.categoria;
                form.querySelector('[name="precioPorDefecto"]').value = s.precioPorDefecto;
                form.querySelector('[name="descripcion"]').value = s.descripcion;

            } catch (error) {
                console.error("Error al cargar servicio:", error);
                alert("No se pudieron cargar los datos del servicio.");
            }
        });
    }

    iniciarTablaAdmin({
        cuerpo: "tablaServicios",
        buscar: "buscarServicio",
        btnBuscar: "btnBuscarServicio",
        contador: "contadorServicios",
        paginas: "numerosPaginasServicios",
        anterior: "serviciosAnterior",
        siguiente: "serviciosSiguiente",
        sinResultados: "serviciosSinResultados",
        plural: "servicios"
    });
});
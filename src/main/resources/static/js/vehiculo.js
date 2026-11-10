document.addEventListener("DOMContentLoaded", function () {

    // PLACA SIEMPRE EN MAYÚSCULAS (la validación del modelo exige ABC-123)
    document.querySelectorAll(
        '#modalRegistrarVehiculo [name="placa"], #modalEditarVehiculo [name="placa"]'
    ).forEach(campo => {
        campo.addEventListener("input", () => {
            campo.value = campo.value.toUpperCase();
        });
    });

    // AÑO MÁXIMO = AÑO ACTUAL + 1
    document.querySelectorAll(
        '#modalRegistrarVehiculo [name="anioFabricacion"], #modalEditarVehiculo [name="anioFabricacion"]'
    ).forEach(campo => {
        campo.max = new Date().getFullYear() + 1;
    });

    // MODAL DESACTIVAR
    const modalDesactivar = document.getElementById("modalDesactivarVehiculo");

    if (modalDesactivar) {
        modalDesactivar.addEventListener("show.bs.modal", function (event) {
            const boton = event.relatedTarget;

            if (!boton || !boton.dataset.id) {
                console.error("No se recibió el ID del vehículo para desactivar.");
                return;
            }

            document.getElementById("desactivarIdVehiculo").value = boton.dataset.id;
            document.getElementById("desactivarNombreVehiculo").textContent = boton.dataset.nombre;
        });
    }

    // MODAL EDITAR
    const modalEditar = document.getElementById("modalEditarVehiculo");

    if (modalEditar) {
        modalEditar.addEventListener("show.bs.modal", async function (event) {
            const boton = event.relatedTarget;

            if (!boton || !boton.dataset.id) {
                console.error("No se recibió el ID del vehículo para modificar.");
                return;
            }

            const form = modalEditar.querySelector("form");

            try {
                const respuesta = await fetch(`/vehiculos/buscar/${boton.dataset.id}`);

                if (!respuesta.ok) {
                    throw new Error("Error HTTP: " + respuesta.status);
                }

                const v = await respuesta.json();

                form.querySelector('[name="idVehiculo"]').value = v.idVehiculo;
                form.querySelector('[name="marca"]').value = v.marca;
                form.querySelector('[name="modelo"]').value = v.modelo;
                form.querySelector('[name="placa"]').value = v.placa;
                form.querySelector('[name="anioFabricacion"]').value = v.anioFabricacion;
                form.querySelector('[name="kilometros"]').value = v.kilometros;
                form.querySelector('[name="idCliente"]').value = v.idCliente;

            } catch (error) {
                console.error("Error al cargar vehículo:", error);
                alert("No se pudieron cargar los datos del vehículo.");
            }
        });
    }

    iniciarTablaAdmin({
        cuerpo: "tablaVehiculos",
        buscar: "buscarVehiculo",
        btnBuscar: "btnBuscarVehiculo",
        contador: "contadorVehiculos",
        paginas: "numerosPaginasVehiculos",
        anterior: "vehiculosAnterior",
        siguiente: "vehiculosSiguiente",
        sinResultados: "vehiculosSinResultados",
        plural: "vehículos"
    });
});
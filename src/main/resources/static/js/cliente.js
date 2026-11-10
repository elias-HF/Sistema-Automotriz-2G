document.addEventListener("DOMContentLoaded", function () {

    // MODAL DESACTIVAR
    const modalDesactivar = document.getElementById("modalDesactivarCliente");

    if (modalDesactivar) {
        modalDesactivar.addEventListener("show.bs.modal", function (event) {
            const boton = event.relatedTarget;

            if (!boton || !boton.dataset.id) {
                console.error("No se recibió el ID del cliente para desactivar.");
                return;
            }

            document.getElementById("desactivarIdCliente").value = boton.dataset.id;
            document.getElementById("desactivarNombreCliente").textContent = boton.dataset.nombre;
        });
    }

    // MODAL EDITAR
    const modalEditar = document.getElementById("modalEditarCliente");

    if (modalEditar) {
        modalEditar.addEventListener("show.bs.modal", async function (event) {
            const boton = event.relatedTarget;

            if (!boton || !boton.dataset.id) {
                console.error("No se recibió el ID del cliente para modificar.");
                return;
            }

            const id = boton.dataset.id;
            const form = modalEditar.querySelector("form");

            try {
                const respuesta = await fetch(`/clientes/buscar/${id}`);

                if (!respuesta.ok) {
                    throw new Error("Error HTTP: " + respuesta.status);
                }

                const cliente = await respuesta.json();

                form.querySelector('[name="idCliente"]').value = cliente.idCliente;
                form.querySelector('[name="nombre"]').value = cliente.nombre;
                form.querySelector('[name="dni"]').value = cliente.dni;
                form.querySelector('[name="telefono"]').value = cliente.telefono;
                form.querySelector('[name="correo"]').value = cliente.correo;

            } catch (error) {
                console.error("Error al cargar cliente:", error);
                alert("No se pudieron cargar los datos del cliente.");
            }
        });
    }

    // BÚSQUEDA Y PAGINACIÓN
    iniciarTablaAdmin({
        cuerpo: "tablaClientesDemo",
        buscar: "buscarClienteDemo",
        btnBuscar: "btnBuscarClienteDemo",
        contador: "contadorClientesDemo",
        paginas: "numerosPaginasClientes",
        anterior: "clientesAnterior",
        siguiente: "clientesSiguiente",
        sinResultados: "clientesSinResultados",
        plural: "clientes"
    });
});
document.addEventListener("DOMContentLoaded", function () {

    // MODAL DESACTIVAR
    const modalDesactivar = document.getElementById("modalDesactivarEmpleado");

    if (modalDesactivar) {
        modalDesactivar.addEventListener("show.bs.modal", function (event) {
            const boton = event.relatedTarget;

            if (!boton || !boton.dataset.id) {
                console.error("No se recibió el ID del empleado para desactivar.");
                return;
            }

            document.getElementById("desactivarIdEmpleado").value = boton.dataset.id;
            document.getElementById("desactivarNombreEmpleado").textContent = boton.dataset.nombre;
        });
    }

    // MODAL EDITAR
    const modalEditar = document.getElementById("modalEditarEmpleado");

    if (modalEditar) {
        modalEditar.addEventListener("show.bs.modal", async function (event) {
            const boton = event.relatedTarget;

            if (!boton || !boton.dataset.id) {
                console.error("No se recibió el ID del empleado para modificar.");
                return;
            }

            const form = modalEditar.querySelector("form");

            try {
                const respuesta = await fetch(`/empleados/buscar/${boton.dataset.id}`);

                if (!respuesta.ok) {
                    throw new Error("Error HTTP: " + respuesta.status);
                }

                const empleado = await respuesta.json();

                form.querySelector('[name="idEmpleado"]').value = empleado.idEmpleado;
                form.querySelector('[name="nombre"]').value = empleado.nombre;
                form.querySelector('[name="correo"]').value = empleado.correo;
                form.querySelector('[name="usuario"]').value = empleado.usuario;
                form.querySelector('[name="rol"]').value = empleado.rol;
                form.querySelector('[name="contrasenia"]').value = "";

            } catch (error) {
                console.error("Error al cargar empleado:", error);
                alert("No se pudieron cargar los datos del empleado.");
            }
        });
    }

    iniciarTablaAdmin({
        cuerpo: "tablaEmpleados",
        buscar: "buscarEmpleado",
        btnBuscar: "btnBuscarEmpleado",
        contador: "contadorEmpleados",
        paginas: "numerosPaginasEmpleados",
        anterior: "empleadosAnterior",
        siguiente: "empleadosSiguiente",
        sinResultados: "empleadosSinResultados",
        plural: "empleados"
    });
});
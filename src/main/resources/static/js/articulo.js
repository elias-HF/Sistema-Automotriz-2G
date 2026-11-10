document.addEventListener("DOMContentLoaded", function () {

    // MODAL DESACTIVAR
    const modalDesactivar = document.getElementById("modalDesactivarArticulo");

    if (modalDesactivar) {
        modalDesactivar.addEventListener("show.bs.modal", function (event) {
            const boton = event.relatedTarget;

            if (!boton || !boton.dataset.id) {
                console.error("No se recibió el ID del artículo para desactivar.");
                return;
            }

            document.getElementById("desactivarIdArticulo").value = boton.dataset.id;
            document.getElementById("desactivarNombreArticulo").textContent = boton.dataset.nombre;
        });
    }

    // MODAL EDITAR
    const modalEditar = document.getElementById("modalEditarArticulo");

    if (modalEditar) {
        modalEditar.addEventListener("show.bs.modal", async function (event) {
            const boton = event.relatedTarget;

            if (!boton || !boton.dataset.id) {
                console.error("No se recibió el ID del artículo para modificar.");
                return;
            }

            const form = modalEditar.querySelector("form");

            try {
                const respuesta = await fetch(`/articulos/buscar/${boton.dataset.id}`);

                if (!respuesta.ok) {
                    throw new Error("Error HTTP: " + respuesta.status);
                }

                const a = await respuesta.json();

                form.querySelector('[name="idArticulo"]').value = a.idArticulo;
                form.querySelector('[name="nombre"]').value = a.nombre;
                form.querySelector('[name="unidadMedida"]').value = a.unidadMedida;
                form.querySelector('[name="descripcion"]').value = a.descripcion;
                form.querySelector('[name="precioPorDefecto"]').value = a.precioPorDefecto;
                form.querySelector('[name="stock"]').value = a.stock;
                form.querySelector('[name="stockMinimo"]').value = a.stockMinimo;

            } catch (error) {
                console.error("Error al cargar artículo:", error);
                alert("No se pudieron cargar los datos del artículo.");
            }
        });
    }

    // BÚSQUEDA Y PAGINACIÓN
    iniciarTablaAdmin({
        cuerpo: "tablaArticulos",
        buscar: "buscarArticulo",
        btnBuscar: "btnBuscarArticulo",
        contador: "contadorArticulos",
        paginas: "numerosPaginasArticulos",
        anterior: "articulosAnterior",
        siguiente: "articulosSiguiente",
        sinResultados: "articulosSinResultados",
        plural: "artículos"
    });
});
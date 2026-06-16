import {useParams} from "react-router-dom";

function FormularioProducto() {
    const {id} = useParams();

    return (
        <>
            <h1>{id ? "Editar Producto" : "Agregar Producto"}</h1>

            <form>
                <input placeholder="Nombre" />
                <input placeholder="Precio" />
                <textarea placeholder="Descripción" />

                <button>Guardar</button>
            </form>
        </>
    );
}

export default FormularioProducto;
import {Link} from "react-router-dom";

function GestionProductos() {
    return (
        <> 
            <h1>Gestión de Productos</h1>

            <Link to="/productos/nuevo">Agregar Producto</Link>
        </>
    );
}

export default GestionProductos;
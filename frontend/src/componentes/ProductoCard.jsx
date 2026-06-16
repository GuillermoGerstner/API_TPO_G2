import { Link } from "react-router-dom";

function ProductoCard({ producto }) {
    return (
        <div>
            <h3>{producto.nombre}</h3>
            <p>{producto.descripcion}</p>
            <p>{producto.precio}</p>

            <Link to={`/productos/${producto.id}`}>
                Ver detalles
            </Link>
        </div>
    );
}

export default ProductoCard;
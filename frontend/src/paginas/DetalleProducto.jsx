import {
    useEffect,
    useState,
    useContext,
} from "react";

import {
    useParams,
    useNavigate,
} from "react-router-dom";

import api from "../api/api";

import {CarritoContext} from "../contexto/CarritoProvider";

function DetalleProducto() {
    const {id} = useParams();

    const navigate = useNavigate();

    const {addToCarrito} = useContext(CarritoContext);

    const [producto, setProducto] = useState(null);

    useEffect(() => {
        api
            .get('/productos/${id}')
            .then((res) => setProducto(res.data));
    }, [id]);

    if (!producto){
        return <h2>Cargando...</h2>;
    }

    return (
        <>
            <h1>{producto.nombre}</h1>
            <p>{producto.descripcion}</p>
            <p>Precio: ${producto.precio}</p>

            <button onClick={() => addToCarrito(producto)}>Agregar al carrito</button>

            <button onClick={() => {
                addToCarrito(producto);
                navigate("/carrito");
            }}>
                Comprar ahora
            </button>
        </>
    );
}

export default DetalleProducto;

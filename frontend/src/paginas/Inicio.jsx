import {useEffect, useState} from "react";
import api from "../api/api";
import ProductoCard from "../componentes/ProductoCard";
import Loader from "../componentes/Loader";
import ErrorMessage from "../componentes/ErrorMessage";

function Inicio() {
    const [productos, setProductos] = useState([]);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");

    useEffect(() => {
        api
            .get("/productos")
            .then((res) => setProductos(res.data))
            .catch(() => setError("Error al cargar los productos"))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <Loader />;

    if(error)
        return (
            <ErrorMessage message={error} />
        );
    
    return (
        <>
            <h1>Catálogo</h1>
            
            {productos.map((producto) => (
                <ProductoCard
                    key={producto.id}
                    producto={producto}
                />
            ))}
        </>
    );
}

export default Inicio;
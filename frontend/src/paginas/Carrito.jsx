import {useContext} from "react";
import {CarritoContext} from "../context/CarritoProvider";

function Carrito() {
    const{
        carritoItems,
        removeFromCarrito,
        clearCarrito,
    } = useContext(CarritoContext);

    return (
        <> 
            <h1>Carrito</h1>

            {carritoItems.length === 0 ? (
                <p>Carrito vacío</p>
            ) : (
                <>
                    {carritoItems.map((item) => (
                        <div key={item.id}>
                            <h3>{item.nombre}</h3>

                            <button onClick={() => removeFromCarrito(item.id)}>
                                Eliminar
                            </button>
                        </div>
                    ))}

                    <button onClick={clearCarrito}>Vaciar Carrito</button>
                </>
            )}  
        </>
    );
}

export default Carrito;
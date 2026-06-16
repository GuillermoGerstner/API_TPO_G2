import { createContext, useState } from "react";

export const CarritoContext = createContext();

export function CarritoProvider({ children }) {
    const [carritoItems, setCarritoItems] = useState([]);

    const addToCarrito = (producto) => {
        setCarritoItems((prev) => [...prev, producto]);
    };

    const removeFromCarrito = (id) => {
        setCarritoItems((prev) =>
            prev.filter((item) => item.id !== id)
        );
    };

    const clearCarrito = () => {
        setCarritoItems([]);
    };

    return (
        <CarritoContext.Provider
            value = {{
                carritoItems,
                addToCarrito,
                removeFromCarrito,
                clearCarrito,
            }}
        >
            {children}
        </CarritoContext.Provider>
    );
}
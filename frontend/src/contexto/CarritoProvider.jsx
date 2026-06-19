import { createContext, useState } from "react";

export const CarritoContext = createContext();

export function CarritoProvider({ children }) {
  const [carritoItems, setCarritoItems] = useState([]);

  const addToCarrito = (producto) => {
    const cantidadAAgregar = Number(producto.cantidadPedida) || 1;
    const stockDisponible = Number(producto.stock) || 0;

    if (stockDisponible <= 0) {
      alert("Este producto no tiene stock disponible.");
      return;
    }

    const itemInCart = carritoItems.find((item) => item.id === producto.id);

    if (itemInCart) {
      if (itemInCart.cantidad >= stockDisponible) {
        alert(
          `No podés agregar más unidades. El stock máximo es de ${stockDisponible} unidades.`,
        );
        return;
      }

      const cantidadDeseada = itemInCart.cantidad + cantidadAAgregar;
      const cantidadFinal = Math.min(cantidadDeseada, stockDisponible);

      if (cantidadDeseada > stockDisponible) {
        alert(
          `¡Atención! Solo quedan ${stockDisponible} unidades disponibles de este producto.`,
        );
      }

      setCarritoItems(
        carritoItems.map((item) =>
          item.id === producto.id ? { ...item, cantidad: cantidadFinal } : item,
        ),
      );

      return;
    }

    if (cantidadAAgregar > stockDisponible) {
      alert(
        `¡Atención! Añadimos el máximo disponible de este producto (${stockDisponible} unidades).`,
      );
    }

    setCarritoItems([
      ...carritoItems,
      {
        ...producto,
        cantidad: Math.min(cantidadAAgregar, stockDisponible),
      },
    ]);
  };

  const incrementarCantidad = (id) => {
    const item = carritoItems.find((item) => item.id === id);

    if (!item) return;

    if (item.cantidad >= item.stock) {
      alert(
        `No podés agregar más unidades. El stock máximo es de ${item.stock} unidades.`,
      );
      return;
    }

    setCarritoItems(
      carritoItems.map((item) =>
        item.id === id ? { ...item, cantidad: item.cantidad + 1 } : item,
      ),
    );
  };

  const decrementarCantidad = (id) => {
    setCarritoItems(
      carritoItems.map((item) =>
        item.id === id && item.cantidad > 1
          ? { ...item, cantidad: item.cantidad - 1 }
          : item,
      ),
    );
  };

  const removeFromCarrito = (id) => {
    setCarritoItems(carritoItems.filter((item) => item.id !== id));
  };

  const clearCarrito = () => {
    setCarritoItems([]);
  };

  return (
    <CarritoContext.Provider
      value={{
        carritoItems,
        addToCarrito,
        incrementarCantidad,
        decrementarCantidad,
        removeFromCarrito,
        clearCarrito,
      }}
    >
      {children}
    </CarritoContext.Provider>
  );
}
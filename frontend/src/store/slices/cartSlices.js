import { createSlice } from "@reduxjs/toolkit";

export const cartSlice = createSlice({
  name: "cart",
  initialState: {
    items: [],
  },
  reducers: {
        addToCart: (state, action) => {
            const itemInCart = state.items.find(
                (item) => item.id === action.payload.id
            );
           
            const cantidadAAgregar = action.payload.cantidadPedida || 1;
            
            const stockDisponible = action.payload.stock || 0;

            if (itemInCart) {
                // valido si ya está en el carrito, que la suma no supere el stock real
                if (itemInCart.cantidad + cantidadAAgregar <= stockDisponible) {
                    itemInCart.cantidad += cantidadAAgregar;
                } else {
                    // Si se pasa, lo clavamos en el máximo disponible
                    itemInCart.cantidad = stockDisponible;
                    alert(`¡Atención! Solo quedan ${stockDisponible} unidades disponibles de este producto.`);
                }
            } else {
                // si no esta en el carrito valido que no pase el stock disponible
                if (cantidadAAgregar <= stockDisponible) {
                    state.items.push({ ...action.payload, cantidad: cantidadAAgregar });
                } else {
                    state.items.push({ ...action.payload, cantidad: stockDisponible });
                    alert(`¡Atención! Añadimos el máximo disponible de este producto (${stockDisponible} unidades).`);
                }
            }
        },
    incrementarCantidad: (state, action) => {
            const item = state.items.find((item) => item.id === action.payload);
            
            if (item) {
                // solo suma si hay stock
                if (item.cantidad < item.stock) {
                    item.cantidad += 1;
                } else {
                    alert(`No podés agregar más unidades. El stock máximo es de ${item.stock} unidades.`);
                }
            }
        },
    decrementarCantidad: (state, action) => {
      const item = state.items.find((item) => item.id === action.payload);
      if (item && item.cantidad > 1) {
        item.cantidad -= 1;
      }
    },
    removeFromCart: (state, action) => {
      state.items = state.items.filter((item) => item.id !== action.payload);
    },
    clearCart: (state) => {
      state.items = [];
    },
  },
});

export const {
  addToCart,
  incrementarCantidad,
  decrementarCantidad,
  removeFromCart,
  clearCart,
} = cartSlice.actions;

export default cartSlice.reducer;

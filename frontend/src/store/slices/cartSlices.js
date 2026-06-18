import { createSlice } from "@reduxjs/toolkit";

export const cartSlice = createSlice({
  name: "cart",
  initialState: {
    items: [],
  },
  reducers: {
    addToCart: (state, action) => {
      const itemInCart = state.items.find(
        (item) => item.id === action.payload.id,
      );
      const cantidadAAgregar = action.payload.cantidadPedida || 1;

      if (itemInCart) {
        itemInCart.cantidad += cantidadAAgregar;
      } else {
        state.items.push({ ...action.payload, cantidad: cantidadAAgregar });
      }
    },
    incrementarCantidad: (state, action) => {
      const item = state.items.find((item) => item.id === action.payload);
      if (item) {
        item.cantidad += 1;
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

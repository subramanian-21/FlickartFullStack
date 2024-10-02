import { createSlice } from "@reduxjs/toolkit";

const cartSlice = createSlice({
  name: "cart",
  initialState: {
    products: [],
  },
  reducers: {
    addToCart: (state, action) => {
      state.products.push(action.payload);
    },
    removeFromCart: (state, action) => {
      return {
        products: state.products.filter((k) => k.id !== action.payload.id),
      };
    },
    cleaCart: (state) => {
      state.products.length = 0;
    },
  },
});

export const { addToCart, removeFromCart, cleaCart } = cartSlice.actions;

export default cartSlice.reducer;

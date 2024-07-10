import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  visible: false,
};

export const flagSlice = createSlice({
  name: "counter",
  initialState,
  reducers: {
    changeFlag: (state) => {
      state.visible = !state.visible;
    },
  },
});

// Action creators are generated for each case reducer function
export const { changeFlag } = flagSlice.actions;

export default flagSlice.reducer;

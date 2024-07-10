import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { fetchCategories } from "../../utils/Api";
const initialState = {
  status: "ideal",
  data: [
    {
      expCatId: "",
      name: "",
      date: "",
      time: "",
    },
  ],
};

export const fetchCat = createAsyncThunk("categories", async () => {
  const response = await fetchCategories();
  return response;
});

export const categorySlice = createSlice({
  name: "categories",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchCat.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchCat.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.data = action.payload;
      })
      .addCase(fetchCat.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      });
  },
});

export default categorySlice.reducer;

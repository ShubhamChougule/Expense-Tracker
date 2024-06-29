import { createSlice , createAsyncThunk} from "@reduxjs/toolkit";
import { fetchExpenses } from "../../utils/Api";

const initialState = {
  status: "ideal",
  data: ["hey"],
};


export const fetchData = createAsyncThunk('expense/fetchExpenses', async () => {
  const response = await fetchExpenses();
  return response;
})

export const expenseSlice = createSlice({
  name: "expense",
  initialState,
  reducers: {},
  extraReducers : (builder) => {
    builder
    .addCase(fetchData.pending, (state) => {
      state.status = 'loading';
    })
    .addCase(fetchData.fulfilled, (state, action) => {
      state.status = 'succeeded';
      state.data = action.payload;
    })
    .addCase(fetchData.rejected, (state, action) => {
      state.status = 'failed';
      state.error = action.error.message;
    });
  }
});

export default expenseSlice.reducer;



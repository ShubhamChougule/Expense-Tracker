import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { getMonthlyExpensesReport } from "../../utils/Api";

const initialState = {
  status: "ideal",
  data: {
    totalExpenseByExpCat: {
      recharges: 3182,
      travel: 3400,
      food: 413,
    },
    totalExpenses: 6995,
  },
};

export const fetchMonthlyChart = createAsyncThunk("monthlyReport", async () => {
  const response = await getMonthlyExpensesReport();
  return response;
});

export const monthlyReportSlice = createSlice({
  name: "monthlyReport",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchMonthlyChart.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchMonthlyChart.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.data = action.payload;
      })
      .addCase(fetchMonthlyChart.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      });
  },
});

export default monthlyReportSlice.reducer;

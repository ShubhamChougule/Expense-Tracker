import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { getWeeklyExpensesReport } from "../../utils/Api";

const initialState = {
  status: "ideal",
  data: {
    weeklyReport: {
      totalExpenseByDate: {
        "06-Jul-2024": 2000,
        "05-Jul-2024": 255,
        "07-Jul-2024": 3435,
      },
      totalExpenses: 5690,
    },
  },
};

export const fetchWeeklyChart = createAsyncThunk("weeklyReport", async () => {
  const response = await getWeeklyExpensesReport();
  return response;
});

export const weeklyReportSlice = createSlice({
  name: "weeklyReport",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchWeeklyChart.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchWeeklyChart.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.data = action.payload;
      })
      .addCase(fetchWeeklyChart.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      });
  },
});

export default weeklyReportSlice.reducer;

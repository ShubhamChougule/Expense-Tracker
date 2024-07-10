import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { fetchExpenses } from "../../utils/Api";

const initialState = {
  status: "ideal",
  data: {
    expenses: [
      {
        expId: "",
        title: "lonavala",
        amount: "3400.0",
        expCat: {
          expCatId: "668a515c28081b74b03229d2",
          name: "travel",
          date: "07-Jul-2024",
          time: "08:27 AM",
        },
        date: "07-Jul-2024",
        time: "08:27 AM",
      },
    ],
    nextPage: 0,
    previousPage: 0,
    firstPage: false,
    lastPage: true,
  },
};

export const fetchData = createAsyncThunk(
  "expense/fetchExpenses",
  async ({ startDate, endDate, page, size }) => {
    const response = await fetchExpenses(startDate, endDate, page, size);
    return response;
  }
);

export const expenseSlice = createSlice({
  name: "expense",
  initialState,
  reducers: {
    // filter Expense By Category action will have category name

    filterExpenseByCategory: (state, action) => {
      if (!action.payload.catName) return state;

      state.data.expenses = state.data.expenses.filter((expense) => {
        return expense.expCat.name === action.payload.catName;
      });
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchData.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchData.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.data = action.payload;
      })
      .addCase(fetchData.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error.message;
      });
  },
});

export const { filterExpenseByCategory } = expenseSlice.actions;

export default expenseSlice.reducer;

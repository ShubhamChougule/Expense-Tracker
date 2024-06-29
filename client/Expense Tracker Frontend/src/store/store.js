import { combineReducers, configureStore } from "@reduxjs/toolkit";
import counterSlice from "../slices/counter/counerSlice";
import expenseSlice from "../slices/expenses/expenseSlice";
import monthlyExpenseReportSlice from "../slices/expenses/monthlyExpenseReportSlice";
import weeklyExpenseReportReducer from "../slices/expenses/weeklyExpenseReportReducer";

export const store = configureStore({
  reducer: {
    counter: counterSlice,
    expenses: expenseSlice,
    monthlyExpenseReport : monthlyExpenseReportSlice,
    weeklyReportReducer: weeklyExpenseReportReducer
  },
});

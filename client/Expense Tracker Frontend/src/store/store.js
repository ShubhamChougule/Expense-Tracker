import { configureStore } from "@reduxjs/toolkit";
import expenseSlice from "../slices/expenses/expenseSlice";
import monthlyExpenseReportSlice from "../slices/expenses/monthlyExpenseReportSlice";
import weeklyExpenseReportReducer from "../slices/expenses/weeklyExpenseReportReducer";
import userSlice from "../slices/user/userSlice";
import FlagSlice from "../slices/flags/showAllExpense";
import categorySlice from "../slices/expenses/categoriesSlice";

export const store = configureStore({
  reducer: {
    flagReducer: FlagSlice,
    expenses: expenseSlice,
    monthlyExpenseReport: monthlyExpenseReportSlice,
    weeklyReportReducer: weeklyExpenseReportReducer,
    user: userSlice,
    category: categorySlice,
  },
});

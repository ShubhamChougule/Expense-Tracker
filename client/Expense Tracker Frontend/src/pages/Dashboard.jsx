import React, { useEffect, useState } from "react";
import "tailwindcss/tailwind.css";
import WeeklyReport from "../components/WeeklyReport";
import MonthlyReport from "../components/MonthlyReport";
import AddExpense from "../components/AddExpense";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { fetchData } from "../slices/expenses/expenseSlice";
import { fetchUserDetails } from "../slices/user/userSlice";
import RecentExpenses from "../components/RecentExpenses";

const Dashboard = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const token = useSelector((state) => state.user.userToken);

  useEffect(() => {
    dispatch(fetchUserDetails({}));
  }, [navigate, dispatch]);

  if (!token) {
    return (
      <div className="flex flex-col justify-center items-center w-full h-screen bg-blue-50">
        <h1 className="text-2xl font-semibold text-red-600 mb-6">
          You are Unauthorized. Please login.
        </h1>
        <button
          className="w-full max-w-sm bg-blue-500 text-white font-bold py-3 px-6 rounded-lg hover:bg-blue-700"
          onClick={() => navigate("/login")}
        >
          Go to Login
        </button>
      </div>
    );
  }

  return (
    <div className="min-h-screen md:px-20 py-20 bg-gray-100">
      <div className="flex flex-col md:flex-row gap-6">
        <AddExpense />
        <WeeklyReport />
      </div>
      <div className="mt-6 flex flex-col md:flex-row gap-6">
        <RecentExpenses />
        <MonthlyReport />
      </div>
    </div>
  );
};

export default Dashboard;

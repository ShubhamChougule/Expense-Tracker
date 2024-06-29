import React, { useEffect, useState } from "react";
import "tailwindcss/tailwind.css";
import WeeklyReport from "./WeeklyReport";
import MonthlyReport from "./MonthlyReport";
import AddExpense from "./AddExpense";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { fetchData } from "../slices/expenses/expenseSlice";

const ExpenseTracker = () => {
  const [userName, setUserName] = useState("");
  const navigate = useNavigate();
  const expenses = useSelector((state) => state.expenses.data);
  const dispatch = useDispatch();

  useEffect(() => {
    const fetchUserInfo = async () => {
      const accessToken = localStorage.getItem("accessToken");

      const response = await fetch("http://localhost:8085/api/v1/users/info", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      if (response.ok) {
        const data = await response.json();
        setUserName(data.name);
      } else {
        console.error("Failed to fetch user info");
        navigate("/");
      }
    };

    fetchUserInfo();
    dispatch(fetchData());
  }, [navigate, dispatch]);

  return (
    <div className="min-h-screen bg-blue-50 px-6 md:px-20">
      <header className="flex justify-between items-center py-6">
        <h1 className="text-4xl font-bold text-blue-900">Expense Tracker</h1>
        <div className="text-lg text-blue-700">Hello, {userName}</div>
      </header>
      <div className="flex flex-col md:flex-row gap-6">
        <AddExpense />
        <WeeklyReport />
      </div>
      <div className="mt-6 flex flex-col md:flex-row gap-6">
        <div className="bg-white p-6 rounded-lg shadow-lg w-full md:w-1/3">
          <h2 className="text-xl font-semibold mb-4 text-blue-900">
            Recent Expenses
          </h2>
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-300">
              <thead className="bg-blue-100">
                <tr>
                  <th className="px-6 py-3 text-left text-sm font-semibold text-blue-800 uppercase tracking-wider">
                    Title
                  </th>
                  <th className="px-6 py-3 text-right text-sm font-semibold text-blue-800 uppercase tracking-wider">
                    Amount
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {expenses.slice(-5).map((expense, index) => (
                  <tr key={index} className="hover:bg-blue-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                      {expense.title}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-right text-gray-700">
                      {"Rs. " + expense.amount}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
        <MonthlyReport />
      </div>
    </div>
  );
};

export default ExpenseTracker;

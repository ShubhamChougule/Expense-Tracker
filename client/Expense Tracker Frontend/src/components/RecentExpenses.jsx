import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { fetchData } from "../slices/expenses/expenseSlice";

const RecentExpenses = () => {
  const expenses = useSelector((state) => state.expenses.data.expenses);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchData({ page: 0, size: 5 }));
  }, [dispatch]);

  return (
    <div className="bg-white p-6 rounded-lg shadow-lg w-full">
      <div className="flex items-center justify-between">
        <h2 className="text-xl font-semibold mb-4 text-blue-900">
          Recent Expenses
        </h2>
        <Link to="/allexpenses" className="mb-4 underline">
          View All
        </Link>
      </div>

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
            {expenses.map((expense, index) => (
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
  );
};

export default RecentExpenses;

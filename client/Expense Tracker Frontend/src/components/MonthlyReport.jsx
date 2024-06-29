import React, { useEffect, useState } from "react";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import { Pie } from "react-chartjs-2";
import { useSelector, useDispatch } from "react-redux";
import { fetchMonthlyChart } from "../slices/expenses/monthlyExpenseReportSlice";

ChartJS.register(ArcElement, Tooltip, Legend);

const MonthlyReport = () => {
  const [chartData, setChartData] = useState({
    labels: [],
    datasets: [
      {
        label: "Expenses",
        data: [],
        backgroundColor: [
          "rgba(255, 99, 132, 0.6)",
          "rgba(54, 162, 235, 0.6)",
          // Add more colors if needed
        ],
      },
    ],
  });

  const dispatch = useDispatch();
  const apiResponse = useSelector((state) => state.monthlyExpenseReport.data);
  const status = useSelector((state) => state.monthlyExpenseReport.status);

  useEffect(() => {
    dispatch(fetchMonthlyChart());
  }, [dispatch]);

  useEffect(() => {
    if (status === "succeeded") {
      const data = apiResponse.monthlyReport.totalExpenseByExpCat;
      const formattedData = formatDataForChart(data);
      setChartData(formattedData);
    }
  }, [apiResponse, status]);

  const formatDataForChart = (data) => {
    const labels = Object.keys(data);
    const expenseData = Object.values(data);

    return {
      labels,
      datasets: [
        {
          label: "Expenses",
          data: expenseData,
          backgroundColor: labels.map(
            (_, index) =>
              `rgba(${(index * 50) % 255}, ${(index * 100) % 255}, ${
                (index * 150) % 255
              }, 0.6)`
          ),
        },
      ],
    };
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow-lg w-full max-w-3xl mx-auto">
      <h2 className="text-2xl font-bold text-center mb-6">
        Monthly Expenses Report
      </h2>
      <div className="flex flex-col md:flex-row items-center">
        <div className="w-full md:w-1/2">
          <h2 className="text-xl font-semibold mb-4 text-blue-900">
            Expenses by Category
          </h2>
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-300">
              <thead className="bg-blue-100">
                <tr>
                  <th className="px-6 py-3 text-left text-sm font-semibold text-blue-800 uppercase tracking-wider">
                    Category
                  </th>
                  <th className="px-6 py-3 text-right text-sm font-semibold text-blue-800 uppercase tracking-wider">
                    Amount
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {chartData.labels.map((label, index) => (
                  <tr key={index} className="hover:bg-blue-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                      {label}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-right text-gray-700">
                      {"Rs. " + chartData.datasets[0].data[index]}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
        <div className="w-full md:w-1/2 mt-6 md:mt-0 h-[300px]">
          <Pie data={chartData} options={{ maintainAspectRatio: false }} />
        </div>
      </div>
    </div>
  );
};

export default MonthlyReport;

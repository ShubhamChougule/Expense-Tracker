import React, { useEffect, useState } from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Bar } from "react-chartjs-2";
import "tailwindcss/tailwind.css";
import { useSelector, useDispatch } from "react-redux";
import { fetchWeeklyChart } from "../slices/expenses/weeklyExpenseReportReducer";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

const WeeklyReport = () => {
  const [chartData, setChartData] = useState({
    labels: [],
    datasets: [
      {
        label: "Expenses",
        data: [],
        backgroundColor: [],
      },
    ],
  });

  const dispatch = useDispatch();
  const apiResponse = useSelector((state) => state.weeklyReportReducer.data);
  const status = useSelector((state) => state.weeklyReportReducer.status);

  useEffect(() => {
    dispatch(fetchWeeklyChart());
  }, [dispatch]);

  useEffect(() => {
    if (status === "succeeded") {
      const data = apiResponse.weeklyReport.totalExpenseByDate;
      const formattedData = formatDataForChart(data);
      setChartData(formattedData);
    }
  }, [apiResponse, status]);

  const formatDataForChart = (data) => {
    const labels = Object.keys(data);
    const expenseData = Object.values(data);

    // Generating random colors for each bar based on its value
    const backgroundColors = expenseData.map(() => {
      const r = Math.floor(Math.random() * 256);
      const g = Math.floor(Math.random() * 256);
      const b = Math.floor(Math.random() * 256);
      return `rgba(${r}, ${g}, ${b}, 0.6)`;
    });

    return {
      labels,
      datasets: [
        {
          label: "Expenses",
          data: expenseData,
          backgroundColor: backgroundColors,
        },
      ],
    };
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow-lg w-full max-w-3xl mx-auto">
      <h2 className="text-xl font-semibold mb-4">Weekly Expenses Report</h2>
      <div className="h-[200px]">
        <Bar data={chartData} options={{ maintainAspectRatio: false }} />
      </div>
    </div>
  );
};

export default WeeklyReport;

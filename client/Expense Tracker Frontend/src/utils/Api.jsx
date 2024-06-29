import axios from "axios";

// access token
const accessToken = localStorage.getItem("accessToken");
const config = {
  headers: { Authorization: `Bearer ${accessToken}` },
};

export const addExpense = async (formData) => {
  try {
    const response = await axios.post(
      "http://localhost:8085/api/v1/expenses",
      formData,
      config
    );
  } catch (error) {
    console.error(error);
  }
};

export const fetchCategories = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8085/api/v1/categories",
      config
    );
    return response.data;
  } catch (error) {
    console.error(error);
  }
};

export const addCategory = async (newCategory) => {
  try {
    const response = await axios.post(
      "http://localhost:8085/api/v1/categories",
      { name: newCategory },
      config
    );
    if (response.status === 201) {
      alert("Category added successfully!");
    }
    return response;
  } catch (error) {
    console.error(error);
  }
};

export const getWeeklyExpensesReport = async () => {
  try {
    const response = await axios.post(
      "http://localhost:8085/api/v1/reports/weekly-report",
      {}, // Assuming an empty body is required
      config
    );
    return response.data;
  } catch (error) {
    console.error(error);
  }
};

export const getMonthlyExpensesReport = async () => {
  try {
    const response = await axios.post(
      "http://localhost:8085/api/v1/reports/monthly-report?startDate=01-06-2024",
      {}, // Assuming an empty body is required
      config
    );

    
    console.log("data --", response);
    return response.data;
  } catch (error) {
    console.error(error);
  }
};

export const fetchExpenses = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8085/api/v1/expenses",
      config
    );

    return response.data.expenses;
  } catch (error) {
    console.error(error);
  }
};

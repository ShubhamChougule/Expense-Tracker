import axios from "axios";

// access token
let accessToken = localStorage.getItem("accessToken");

export const loadAccessToken = async () => {
  accessToken = localStorage.getItem("accessToken");
};

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
    return response;
  } catch (error) {
    return error;
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
  const d = new Date();
  d.setDate(d.getDate() - 30);
  let startDate = String(d.toLocaleDateString("en-GB")).replaceAll("/", "-");
  let api =
    "http://localhost:8085/api/v1/reports/monthly-report?startDate=" +
    startDate;
  try {
    const response = await axios.post(
      api,
      {}, // Assuming an empty body is required
      config
    );
    return response.data;
  } catch (error) {
    console.error(error);
  }
};

export const fetchExpenses = async (startDate, endDate, page = 0, size = 5) => {
  try {
    let api = "http://localhost:8085/api/v1/expenses?";
    api += `size=${size}&page=${page}`;

    // Conditionally add startDate and endDate to the query string
    if (startDate) {
      api += `&startDate=${startDate}`;
    }
    if (endDate) {
      api += `&endDate=${endDate}`;
    }

    api += `&sort=createdAt,DESC`;

    console.log(api);

    const response = await axios.get(api, config);

    return response.data;
  } catch (error) {
    console.error(error);
    // Handle error as needed
    throw error;
  }
};

export const fetchUserInformation = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8085/api/v1/users/info",
      config
    );

    return response.data;
  } catch (error) {
    console.error(error);
  }
};

export const deleteExpense = async (expenseId) => {
  try {
    const response = await axios.delete(
      "http://localhost:8085/api/v1/expenses/" + expenseId,
      config
    );

    return response.data;
  } catch (error) {
    console.error(error);
  }
};

export const updateExpense = async (expenseId, formData) => {
  try {
    const response = await axios.put(
      "http://localhost:8085/api/v1/expenses/" + expenseId,
      formData,
      config
    );
    return response;
  } catch (error) {
    return error;
  }
};

export const registerUser = async (formData) => {
  try {
    const response = await axios.post(
      "http://localhost:8085/api/v1/sign-up",
      formData
    );
    return response;
  } catch (error) {
    return error;
  }
};

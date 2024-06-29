import React, { useState, useEffect } from "react";
import axios from "axios";
import { addCategory, addExpense, fetchCategories } from "../utils/Api";
import { useDispatch, useSelector } from "react-redux";
import { fetchData } from "../slices/expenses/expenseSlice";
import { fetchMonthlyChart } from "../slices/expenses/monthlyExpenseReportSlice";
import { fetchWeeklyChart } from "../slices/expenses/weeklyExpenseReportReducer";

const AddExpense = () => {
  const [title, setTitle] = useState("");
  const [amount, setAmount] = useState("");
  const [category, setCategory] = useState({});
  const [categories, setCategories] = useState([]);
  const [isNewCategoryOptionVisible, setIsNewCategoryOptionVisible] =
    useState(false);
  const [newCategory, setNewCategory] = useState("");

  const dispatch = useDispatch();
  const dataFromRedux = useSelector((state) => state.expenses.data);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    const data = await fetchCategories();
    setCategories(data);
  };

  const handleAddExpense = async () => {
    const selectedCategory = categories.find((cat) => cat.name === category);
    const expCatId = selectedCategory.expCatId;

    const newExpense = {
      title,
      amount,
      expCatId,
    };

    await addExpense(newExpense);

    dispatch(fetchData());
    dispatch(fetchMonthlyChart());
    dispatch(fetchWeeklyChart());

    setTitle("");
    setAmount("");
    setCategory("");
    setIsNewCategoryOptionVisible(false);
    setNewCategory("");
  };

  const handleNewCategory = async () => {
    const response = await addCategory(newCategory);
    if (response.status === 201) {
      setIsNewCategoryOptionVisible(false);
      loadData();
      setNewCategory("");
    }
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow-md w-full md:w-1/3">
      <h2 className="text-2xl font-semibold mb-6">Add Expense</h2>
      <div className="flex flex-col space-y-4">
        <input
          type="text"
          placeholder="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          className="p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <input
          type="number"
          placeholder="Amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          className="p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <div className="flex items-center space-x-4">
          <select
            value={category}
            onChange={(e) => {
              if (e.target.value === "addNewCategory") {
                setIsNewCategoryOptionVisible(true);
                setCategory("");
              } else {
                setCategory(e.target.value);
                setIsNewCategoryOptionVisible(false);
              }
            }}
            className="p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 flex-1"
          >
            <option value="">Select Category</option>
            <option value="addNewCategory">Add new category</option>
            {categories.map((categorie) => (
              <option key={categorie.expCatId} value={categorie.name}>
                {categorie.name}
              </option>
            ))}
          </select>
          <button
            onClick={handleAddExpense}
            className="p-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            Add Expense
          </button>
        </div>

        {isNewCategoryOptionVisible && (
          <div className="flex items-center space-x-4">
            <input
              type="text"
              placeholder="New category name"
              value={newCategory}
              onChange={(e) => setNewCategory(e.target.value)}
              className="p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 flex-1"
            />
            <button
              onClick={handleNewCategory}
              className="p-3 bg-green-500 text-white rounded-lg hover:bg-green-600 focus:outline-none focus:ring-2 focus:ring-green-500"
            >
              Add Category
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default AddExpense;

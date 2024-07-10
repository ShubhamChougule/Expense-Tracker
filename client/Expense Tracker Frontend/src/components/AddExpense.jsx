import React, { useState, useEffect } from "react";
import { addCategory, addExpense, fetchCategories } from "../utils/Api";
import { useDispatch, useSelector } from "react-redux";
import { fetchData } from "../slices/expenses/expenseSlice";
import { fetchMonthlyChart } from "../slices/expenses/monthlyExpenseReportSlice";
import { fetchWeeklyChart } from "../slices/expenses/weeklyExpenseReportReducer";
import { fetchCat } from "../slices/expenses/categoriesSlice";
import { ToastContainer, toast } from "react-toastify";

const AddExpense = () => {
  const [title, setTitle] = useState("");
  const [amount, setAmount] = useState("");
  const [category, setCategory] = useState("");
  // const [categories, setCategories] = useState([]);
  const [isNewCategoryOptionVisible, setIsNewCategoryOptionVisible] =
    useState(false);
  const [newCategory, setNewCategory] = useState("");
  const [errors, setErrors] = useState({});

  const dispatch = useDispatch();
  const categories = useSelector((state) => state.category.data);

  useEffect(() => {
    dispatch(fetchCat());
  }, []);

  const validateForm = () => {
    const newErrors = {};
    if (!title) newErrors.title = "Title is required";
    if (!amount) newErrors.amount = "Amount is required";
    if (!category) newErrors.category = "Category is required";
    return newErrors;
  };

  const handleAddExpense = async (e) => {
    e.preventDefault();
    const newErrors = validateForm();
    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
      return;
    }

    const selectedCategory = categories.find((cat) => cat.name === category);
    const expCatId = selectedCategory.expCatId;

    const newExpense = {
      title,
      amount,
      expCatId,
    };

    await addExpense(newExpense);

    toast.success(`Expense [${title}] added successfully`);

    dispatch(fetchData({ page: 0, size: 5 }));
    dispatch(fetchMonthlyChart());
    dispatch(fetchWeeklyChart());
    dispatch(fetchCat());

    setTitle("");
    setAmount("");
    setCategory("");
    setIsNewCategoryOptionVisible(false);
    setNewCategory("");
    setErrors({});
  };

  const handleNewCategory = async () => {
    if (!newCategory) {
      setErrors({ newCategory: "New category name is required" });
      return;
    }

    const response = await addCategory(newCategory);

    try {
      if (response.status != undefined && response.status === 201) {
        toast.success(`Category [${newCategory}] added successfully`);
        setIsNewCategoryOptionVisible(false);
        dispatch(fetchCat());
        setNewCategory("");
        setErrors({});
      } else {
        toast.error(response.response.data.errorMessage);
      }
    } catch (error) {
      console.log(response);
      toast.error(response.response.data.errorMessage);
    }
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow-md w-full">
      <h2 className="text-xl font-semibold mb-4 text-blue-900">Add Expense</h2>
      <form className="flex flex-col space-y-4" onSubmit={handleAddExpense}>
        <div>
          {errors.title && (
            <p className="text-red-500 text-sm">{errors.title}</p>
          )}
          <input
            type="text"
            placeholder="Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 w-full"
          />
        </div>
        <div>
          {errors.amount && (
            <p className="text-red-500 text-sm">{errors.amount}</p>
          )}
          <input
            type="number"
            placeholder="Amount"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            className="p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 w-full"
          />
        </div>
        <div>
          {errors.category && (
            <p className="text-red-500 text-sm">{errors.category}</p>
          )}
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
            className="p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 w-full"
          >
            <option value="">Select Category</option>
            <option value="addNewCategory">Add new category</option>
            {categories.map((categorie) => (
              <option key={categorie.expCatId} value={categorie.name}>
                {categorie.name}
              </option>
            ))}
          </select>
        </div>
        <button
          type="submit"
          className="p-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
        >
          Add Expense
        </button>

        {isNewCategoryOptionVisible && (
          <div className="flex items-center flex-col gap-3">
            {errors.newCategory && (
              <p className="text-red-500 text-sm">{errors.newCategory}</p>
            )}
            <input
              type="text"
              placeholder="New category name"
              value={newCategory}
              onChange={(e) => setNewCategory(e.target.value)}
              className="p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 w-full"
            />
            <button
              type="button"
              onClick={handleNewCategory}
              className="p-3 w-full bg-green-500 text-white rounded-lg hover:bg-green-600 focus:outline-none focus:ring-2 focus:ring-green-500"
            >
              Add Category
            </button>
          </div>
        )}
      </form>
      <ToastContainer />
    </div>
  );
};

export default AddExpense;

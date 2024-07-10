import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { fetchUserDetails } from "../slices/user/userSlice";
import {
  faEdit,
  faTrash,
  faArrowLeft,
  faArrowRight,
} from "@fortawesome/free-solid-svg-icons";
import AddExpense from "../components/AddExpense";
import RecentExpenses from "../components/RecentExpenses";
import {
  fetchData,
  filterExpenseByCategory,
} from "../slices/expenses/expenseSlice";
import { useNavigate } from "react-router-dom";
import { deleteExpense, fetchCategories, updateExpense } from "../utils/Api";

const AllExpenses = () => {
  const dispatch = useDispatch();
  const expenses = useSelector((state) => state.expenses.data.expenses);
  const { firstPage, lastPage, nextPage, PreviousPage } = useSelector(
    (state) => state.expenses.data
  );

  const token = useSelector((state) => state.user.userToken);
  // create states for year,month and category
  // const [year, setYear] = useState("2024");
  // const [month, setMonth] = useState("");
  const [categoryFilter, setCategoryFilter] = useState("");

  const navigate = useNavigate();
  const categories = useSelector((state) => state.category.data);

  useEffect(() => {
    dispatch(fetchUserDetails({}));
    dispatch(fetchData());
  }, []);

  const handleDelete = async (expense) => {
    const id = expense.expId;
    const res = await deleteExpense(id);
    dispatch(fetchData({ page: 0, size: 5 }));
  };

  // category
  const handleChangeCategory = (e) => {
    const category = e.target.value;
    setCategoryFilter(category);
    dispatch(fetchData({ page: null, size: null }));
    setTimeout(() => {
      dispatch(filterExpenseByCategory({ catName: category }));
    }, 500);
  };

  const handleClearAll = () => {
    setCategoryFilter("");
    dispatch(fetchData({ page: 0, size: 5 }));
  };

  // edit feature

  const [title, setTitle] = useState("");
  const [amount, setAmount] = useState("");
  const [category, setCategory] = useState("");
  const [showEdit, setShowEdit] = useState(false);
  const [expenseId, setExpenseId] = useState("");

  const handleEditClickClick = (expense) => {
    setTitle(expense.title);
    setAmount(expense.amount);
    setCategory(expense.expCat.name);
    setExpenseId(expense.expId);
    setShowEdit(true);
  };

  const handleEditClickSubmit = async (e) => {
    e.preventDefault();

    const selectedCategory = categories.find((cat) => cat.name === category);
    const expCatId = selectedCategory?.expCatId; // added optional chaining to avoid null/undefined error

    const EditedExpense = {
      title,
      amount,
      expCatId,
    };

    try {
      const response = await updateExpense(expenseId, EditedExpense);

      if (response.status === 200) {
        toast.success("Expense Updated !");
      } else {
        console.log(response);
        toast.error(response.response.data.errorMessage);
      }
    } catch (error) {
      console.error("Error occurred:", error);
      toast.error("An unexpected error occurred!");
    }

    dispatch(fetchData({ page: 0, size: 5 }));
    setShowEdit(false);
  };

  const handleNextPage = () => {
    dispatch(fetchData({ page: nextPage + 2 }));
  };

  const handlePreviousPage = () => {
    dispatch(fetchData({ page: nextPage }));
  };

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
    <div className="py-20 flex px-5 bg-gray-100 relative">
      <div className="flex flex-col gap-6 w-[30%]">
        <AddExpense />
        <RecentExpenses />
      </div>
      <div className="flex flex-col gap-2 px-6 w-[70%]">
        <h2 className="text-xl font-semibold mb-4 text-blue-900">
          All Expenses
        </h2>
        <div>
          <div className="flex justify-start gap-5 mb-4">
            <select
              name="category"
              id="categorySelect"
              className="rounded-md px-4"
              onChange={handleChangeCategory}
              value={categoryFilter}
            >
              <option value="">Select Category</option>
              {categories.map((categorie) => (
                <option key={categorie.expCatId} value={categorie.name}>
                  {categorie.name}
                </option>
              ))}
            </select>

            <button
              className="bg-blue-400 rounded-2xl px-6 py-2 text-black text-sm"
              onClick={() => handleClearAll()}
            >
              Clear All
            </button>
          </div>

          <div>
            <div className="w-full mb-4">
              <table className="min-w-full divide-y divide-gray-300">
                <thead className="bg-blue-100">
                  <tr>
                    <th className="px-3 py-3 text-left text-sm font-semibold text-blue-800 uppercase tracking-wider">
                      Title
                    </th>
                    <th className="px-3 py-3 text-right text-sm font-semibold text-blue-800 uppercase tracking-wider">
                      Amount
                    </th>
                    <th className="px-3 py-3 text-right text-sm font-semibold text-blue-800 uppercase tracking-wider">
                      Date
                    </th>
                    <th className="px-3 py-3 text-right text-sm font-semibold text-blue-800 uppercase tracking-wider">
                      Category
                    </th>
                    <th className="px-3 py-3 text-right text-sm font-semibold text-blue-800 uppercase tracking-wider">
                      Actions
                    </th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                  {expenses.map((expense, index) => (
                    <tr key={index} className="hover:bg-blue-50">
                      <td className="px-3 py-3 whitespace-nowrap text-sm font-medium text-gray-900">
                        {expense.title}
                      </td>
                      <td className="px-3 py-3 whitespace-nowrap text-sm text-right text-gray-700">
                        {"Rs. " + expense.amount}
                      </td>
                      <td className="px-3 py-3 whitespace-nowrap text-sm text-right text-gray-700">
                        {expense.date}
                      </td>
                      <td className="px-3 py-3 whitespace-nowrap text-sm text-right text-gray-700">
                        {expense.expCat.name}
                      </td>
                      <td className="px-3 py-3 justify-end flex whitespace-nowrap text-sm text-right text-gray-700">
                        <button
                          className="bg-blue-400 rounded-2xl px-4 py-2 text-white text-sm flex items-center"
                          onClick={() => {
                            handleEditClickClick(expense);
                          }}
                        >
                          <FontAwesomeIcon icon={faEdit} className="mr-2" />
                          Edit
                        </button>
                        <button
                          className="bg-red-400 rounded-2xl px-4 py-2 text-white text-sm ml-2 flex items-center"
                          onClick={() => {
                            handleDelete(expense);
                          }}
                        >
                          <FontAwesomeIcon icon={faTrash} className="mr-2" />
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>

        <div className="flex justify-center gap-3">
          {firstPage ? (
            <button
              className="bg-gray-400 rounded-2xl px-6 py-3 text-white text-sm flex items-center"
              disabled
            >
              <FontAwesomeIcon icon={faArrowLeft} className="mr-2" />
              Previous
            </button>
          ) : (
            <button
              className="bg-red-400 rounded-2xl px-6 py-3 text-white text-sm flex items-center"
              onClick={handlePreviousPage}
            >
              <FontAwesomeIcon icon={faArrowLeft} className="mr-2" />
              Previous
            </button>
          )}

          {lastPage ? (
            <button
              className="bg-gray-400 rounded-2xl px-6 py-3 text-white text-sm flex items-center"
              disabled
            >
              Next
              <FontAwesomeIcon icon={faArrowRight} className="ml-2" />
            </button>
          ) : (
            <button
              className="bg-green-400 rounded-2xl px-6 py-3 text-white text-sm flex items-center"
              onClick={handleNextPage}
            >
              Next
              <FontAwesomeIcon icon={faArrowRight} className="ml-2" />
            </button>
          )}
        </div>
      </div>

      {showEdit && (
        <>
          <div className="fixed inset-0 bg-black opacity-50 z-10"></div>
          <div className="fixed inset-0 flex items-center justify-center z-20">
            <div className="w-full max-w-md bg-white rounded-lg p-6">
              <h2 className="text-2xl font-semibold text-gray-800 mb-4">
                Edit Expense
              </h2>

              <form onSubmit={handleEditClickSubmit}>
                <div className="mb-4">
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Title
                  </label>
                  <input
                    type="text"
                    className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md text-gray-700 placeholder-gray-400 focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                  />

                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Amount
                  </label>
                  <input
                    type="text"
                    className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md text-gray-700 placeholder-gray-400 focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                  />

                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Category
                  </label>

                  <select
                    name="category"
                    id="categorySelect"
                    className="rounded-md border w-full px-3 py-2 placeholder-gray-400 focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                  >
                    <option value="">Select Category</option>
                    {categories.map((categorie) => (
                      <option key={categorie.expCatId} value={categorie.name}>
                        {categorie.name}
                      </option>
                    ))}
                  </select>

                  <button className="mt-4 bg-blue-500 rounded-2xl px-6 py-3 text-white text-sm flex items-center justify-center w-full">
                    Edit Now
                  </button>
                  <button
                    className="mt-4 bg-red-400 rounded-2xl px-6 py-3 text-white text-sm flex items-center justify-center w-full"
                    onClick={() => {
                      setShowEdit(false);
                    }}
                  >
                    Cancel
                  </button>
                </div>
              </form>
            </div>
          </div>
        </>
      )}

      <ToastContainer />
    </div>
  );
};

export default AllExpenses;

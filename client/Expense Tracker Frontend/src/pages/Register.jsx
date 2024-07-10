import React, { useState } from "react";
import { registerUser } from "../utils/Api";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Link, useNavigate } from "react-router-dom";

const Register = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [cpassword, setCPassword] = useState("");
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();

    if (!email || !password || !name || !cpassword) {
      toast.error("All fields are required!!");
      return;
    }

    if (password != cpassword) {
      toast.error("Both password should be same !!");
      return;
    }

    const newUserform = {
      name,
      email,
      password,
    };

    try {
      const response = await registerUser(newUserform);
      console.log(response);

      if (response.status === 201) {
        toast.success("User Created successfully !!");
        setTimeout(() => {
          navigate("/login");
        }, 1500);
      } else {
        toast.error(response.response.data.errorMessage);
      }
    } catch (error) {
      console.error("Error occurred:", error);
      toast.error("An unexpected error occurred!");
    }
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <div className="bg-white p-8 rounded-lg shadow-md w-80">
        <h2 className="text-2xl font-bold mb-4 text-center">Register</h2>
        <form onSubmit={handleRegister} className="flex flex-col space-y-4">
          <input
            type="name"
            placeholder="Full Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <input
            type="password"
            placeholder="Confirm Password"
            value={cpassword}
            onChange={(e) => setCPassword(e.target.value)}
            className="p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <button
            type="submit"
            className="p-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            Register
          </button>
        </form>

        <div className="flex justify-between pt-3">
          <p className="text-sm">Already have an Account ? </p>
          <Link to="/login" className="underline text-sm">
            Login
          </Link>
        </div>
      </div>

      <ToastContainer />
    </div>
  );
};

export default Register;

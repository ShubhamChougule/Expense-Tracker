import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchUserDetails, logout } from "../slices/user/userSlice";
import { Link, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSignOutAlt, // for logout
  faTachometerAlt, // for dashboard
  faListAlt, // for view all expenses
} from "@fortawesome/free-solid-svg-icons";

const NavBar = () => {
  const userInfo = useSelector((state) => state.user.userInfo);
  const token = useSelector((state) => state.user.userToken);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    dispatch(fetchUserDetails({}));
  }, []);

  const handleLogout = () => {
    dispatch(logout());
  };

  return (
    <nav className="fixed w-full z-20 top-0  start-0 shadow-md shadow-gray-600 border-gray-200 items-center py-3 px-12 bg-gray-900">
      {token ? (
        <div className="flex justify-between items-center">
          <p className="text-white">You are logged in as : {userInfo.name}</p>

          <div className="flex gap-3">
            <button
              className=" bg-yellow-400 rounded-2xl px-6 py-2 text-black text-sm"
              onClick={() => navigate("/dashboard")}
            >
              <FontAwesomeIcon icon={faTachometerAlt} className="mr-2" />
              Dashboard
            </button>

            <button
              className=" bg-cyan-400 rounded-2xl px-6 py-2 text-black text-sm"
              onClick={() => navigate("/allexpenses")}
            >
              <FontAwesomeIcon icon={faListAlt} className="mr-2" />
              View All Expenses
            </button>
            <button
              className=" bg-blue-500 rounded-2xl px-6 py-2 text-black text-sm"
              onClick={() => handleLogout()}
            >
              <FontAwesomeIcon icon={faSignOutAlt} className="mr-2" />
              Logout
            </button>
          </div>
        </div>
      ) : (
        <div className="flex justify-between items-center">
          <p className="text-white">Please Login...</p>
          <button
            className=" bg-blue-400 rounded-2xl px-6 py-2 text-black text-sm"
            onClick={() => navigate("/login")}
          >
            Login
          </button>
        </div>
      )}
    </nav>
  );
};

export default NavBar;

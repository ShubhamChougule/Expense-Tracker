import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { act } from "react";
import { fetchUserInformation } from "../../utils/Api";

// asyncthunk function to fetch user details
export const loginUser = createAsyncThunk(
  "loginUser",
  async ({ email, password }, { rejectWithValue }) => {
    try {
      const response = await axios.post("http://localhost:8085/api/v1/login", {
        email,
        password,
      });
      return response.data; // Return the data part of the response
    } catch (err) {
      if (err.response && err.response.data) {
        console.log("here");
        // Return custom error message from backend
        return rejectWithValue(err.response.data);
      } else {
        return rejectWithValue({
          errorMessage: "An unknown error occurred",
        });
      }
    }
  }
);

export const fetchUserDetails = createAsyncThunk(
  "fetchUserDetails",
  async ({}, { rejectWithValue }) => {
    try {
      const response = await fetchUserInformation();
      return response; // Return the data part of the response
    } catch (err) {
      if (err.response && err.response.data) {
        console.log("here");
        // Return custom error message from backend
        return rejectWithValue(err.response.data);
      } else {
        return rejectWithValue({
          errorMessage: "An unknown error occurred",
        });
      }
    }
  }
);

const userToken = localStorage.getItem("accessToken") || null;

const initialState = {
  loading: false,
  userInfo: {}, // for user object
  userToken, // for storing the JWT
  error: null, // for storing the error message
};

const userSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    logout: (state) => {
      localStorage.removeItem("accessToken");
      state.userToken = null;
      state.userInfo = {};
    },
  },
  extraReducers: (builder) =>
    builder
      .addCase(loginUser.pending, (state) => {
        state.loading = true;
        state.error = null; // Reset error state on new request
      })
      .addCase(loginUser.fulfilled, (state, action) => {
        state.loading = false;
        state.userToken = action.payload.accessToken;
        state.error = null; // Clear any previous errors on successful login
        localStorage.setItem("accessToken", action.payload.accessToken); // Store JWT in local storage
      })
      .addCase(loginUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload.errorCode; // Store the error message
      })

      .addCase(fetchUserDetails.pending, (state) => {
        state.loading = true;
        state.error = null; // Reset error state on new request
      })
      .addCase(fetchUserDetails.fulfilled, (state, action) => {
        state.loading = false;
        state.userInfo = action.payload;
      })
      .addCase(fetchUserDetails.rejected, (state, action) => {
        state.loading = false;
        state.error = "eerr";
      }),
});

export const { logout } = userSlice.actions;
export default userSlice.reducer;

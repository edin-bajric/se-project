import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { RegisterFormData } from "../components/Register/Register";
import { SignInFormData } from "../components/SignIn/SignIn";
import appAxios from "../services/appAxios";
import { decodeJwtToken } from "../utils/decoder";

const userToken = localStorage.getItem("userToken")
  ? localStorage.getItem("userToken")
  : null;

const initialState = {
  loading: false,
  userInfo: null,
  userToken,
  error: null,
  success: false,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    logout: (state) => {
      localStorage.removeItem("userToken");
      state.loading = false;
      state.userInfo = null;
      state.userToken = null;
      state.error = null;
    },
    checkTokenValidity: (state) => {
      const token = state.userToken;
      if (token) {
        try {
          const decodedToken: any = decodeJwtToken(token);
          const currentTime = Date.now() / 1000;
          if (decodedToken.exp < currentTime) {
            localStorage.removeItem("userToken");
            state.loading = false;
            state.userInfo = null;
            state.userToken = null;
            state.error = null;
          }
        } catch (error) {
          console.error("Error decoding token:", error);
          state.userToken = null;
          state.userInfo = null;
          localStorage.removeItem("userToken");
        }
      }
    },
  },
  extraReducers: (builder) => {
    builder.addCase(login.pending, (state) => {
      state.loading = true;
      state.error = null;
    });
    builder.addCase(login.fulfilled, (state, action: any) => {
      state.loading = false;
      state.userInfo = action.payload;
      state.userToken = action.payload.jwt;
    });
    builder.addCase(login.rejected, (state, action: any) => {
      state.loading = false;
      state.error = action.payload;
    });
    builder.addCase(registerUser.pending, (state) => {
      state.loading = true;
      state.error = null;
    });
    builder.addCase(registerUser.fulfilled, (state) => {
      state.loading = false;
      state.success = true;
    });
    builder.addCase(registerUser.rejected, (state, action: any) => {
      state.loading = false;
      state.error = action.payload;
    });
  },
});

export const registerUser = createAsyncThunk(
  "auth/register",
  async (data: RegisterFormData, { rejectWithValue }) => {
    try {
      await appAxios.post("/auth/register", data);
    } catch (error: any) {
      if (error.response && error.response.data.message) {
        return rejectWithValue(error.response.data.message);
      } else {
        return rejectWithValue(error.message);
      }
    }
  }
);

export const login = createAsyncThunk(
  "auth/login",
  async (body: SignInFormData, { rejectWithValue }) => {
    try {
      const { data } = await appAxios.post("/auth/login", body);
      localStorage.setItem("userToken", data.jwt);
      return data;
    } catch (error: any) {
      if (error.response && error.response.data.message) {
        return rejectWithValue(error.response.data.message);
      } else {
        return rejectWithValue(error.message);
      }
    }
  }
);

export const { logout, checkTokenValidity } = authSlice.actions;
export default authSlice.reducer;
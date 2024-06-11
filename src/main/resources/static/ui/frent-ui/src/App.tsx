import "bootstrap/dist/css/bootstrap.min.css";
import Navbar from "./components/Navbar";
import {
  Home,
  Movies,
  Rentals,
  SignInPage,
  RegisterPage,
  NotFound,
  Search,
  AdminPanel
} from "./pages";
import { Routes, Route } from "react-router-dom";
import ProtectedRoute from "./utils/ProtectedRoute";
import AdminProtectedRoute from "./utils/AdminProtectedRoute";
import { useDispatch } from "react-redux";
import { checkTokenValidity } from "./store/authSlice";
import { useEffect } from "react";

export default function App() {
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(checkTokenValidity());
  }, [dispatch]);
  
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route path="/movies" element={<Movies />} />
        <Route element={<ProtectedRoute />}>
          <Route path="/rentals" element={<Rentals />} />
        </Route>
        <Route path="/login" element={<SignInPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/search/:keyword/:page/:size" element={<Search />} />
        <Route element={<AdminProtectedRoute />}>
        <Route path="/dashboard" element={<AdminPanel />} />
        </Route>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  );
}

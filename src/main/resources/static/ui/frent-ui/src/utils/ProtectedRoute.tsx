import { useSelector } from "react-redux";
import { Outlet } from "react-router-dom";
import { RootState } from "../store";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

const ProtectedRoute = () => {
  const { userToken } = useSelector((state: RootState) => state.auth);

  const navigate = useNavigate();

  useEffect(() => {
    if (!userToken) {
      navigate("/login");
    }
  }, [navigate, userToken]);

  return <Outlet />;
};

export default ProtectedRoute;

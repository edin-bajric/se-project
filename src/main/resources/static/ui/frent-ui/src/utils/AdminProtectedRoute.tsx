import { useSelector } from "react-redux";
import { Outlet } from "react-router-dom";
import { RootState } from "../store";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { decodeJwtToken } from "./decoder";

const AdminProtectedRoute = () => {
  const { userToken } = useSelector((state: RootState) => state.auth);
  const [isAdmin, setIsAdmin] = useState<boolean | null>(null);

  useEffect(() => {
    const checkUserRole = async () => {
      if (userToken) {
        const decodedToken = decodeJwtToken(userToken);
        setIsAdmin(decodedToken?.iss === "admin");
      } else {
        setIsAdmin(false);
      }
    };

    checkUserRole();
  }, [userToken]);

  const navigate = useNavigate();

  useEffect(() => {
    if (isAdmin === false) {
      navigate("*");
    }
  }, [navigate, isAdmin]);

  return <Outlet />;
};

export default AdminProtectedRoute;

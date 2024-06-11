import { useQuery } from "react-query";
import { UserService } from "../services";

const useUsers = () => {
  return useQuery("users", () => UserService.getUsers());
};

export default useUsers;

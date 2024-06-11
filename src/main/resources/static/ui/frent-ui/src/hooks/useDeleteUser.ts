import { useMutation, useQueryClient } from "react-query";
import { UserService } from "../services";

const useDeleteUser = () => {
  const queryClient = useQueryClient();

  return useMutation((userId: string) => UserService.deleteUser(userId), {
    onSuccess: () => {
      queryClient.invalidateQueries("users");
    },
  });
};

export default useDeleteUser;

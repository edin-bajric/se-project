import { useMutation, useQueryClient } from "react-query";
import UserService from "../services/users";
import { User } from "../utils/types";

type UnsuspendUserPayload = User;

const useUnsuspendUser = () => {
  const queryClient = useQueryClient();

  return useMutation(
    (payload: UnsuspendUserPayload) => UserService.unsuspendUser(payload),
    {
      onSuccess: () => {
        queryClient.invalidateQueries("users");
      },
    }
  );
};

export default useUnsuspendUser;
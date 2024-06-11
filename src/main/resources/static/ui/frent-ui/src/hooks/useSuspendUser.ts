import { useMutation, useQueryClient } from "react-query";
import UserService from "../services/users";
import { User } from "../utils/types";

type SuspendUserPayload = User;

const useSuspendUser = () => {
  const queryClient = useQueryClient();

  return useMutation(
    (payload: SuspendUserPayload) => UserService.suspendUser(payload),
    {
      onSuccess: () => {
        queryClient.invalidateQueries("users");
      },
    }
  );
};

export default useSuspendUser;
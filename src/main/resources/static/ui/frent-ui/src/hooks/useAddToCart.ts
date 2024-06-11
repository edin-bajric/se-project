import { useMutation, useQueryClient } from "react-query";
import { UserService } from "../services";

const useAddToCartForUser = () => {
  const queryClient = useQueryClient();

  return useMutation(
    (movieId: string) => UserService.addToCartForUser(movieId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries("cart");
      },
    }
  );
};

export default useAddToCartForUser;

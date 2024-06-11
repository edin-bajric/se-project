import { useMutation, useQueryClient } from "react-query";
import { UserService } from "../services";

const useRemoveFromCart = () => {
  const queryClient = useQueryClient();

  return useMutation((movieId: string) => UserService.removeFromCart(movieId), {
    onSuccess: () => {
      queryClient.invalidateQueries("cart");
    },
  });
};

export default useRemoveFromCart;

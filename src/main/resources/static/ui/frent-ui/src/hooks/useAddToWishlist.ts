import { useMutation, useQueryClient } from "react-query";
import { UserService } from "../services";

const useAddToWishlistForUser = () => {
  const queryClient = useQueryClient();

  return useMutation(
    (movieId: string) => UserService.addToWishlistForUser(movieId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries("wishlist");
      },
    }
  );
};

export default useAddToWishlistForUser;

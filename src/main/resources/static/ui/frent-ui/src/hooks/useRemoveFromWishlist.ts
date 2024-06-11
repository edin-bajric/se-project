import { useMutation, useQueryClient } from "react-query";
import { UserService } from "../services";

const useRemoveFromWishlistForUser = () => {
  const queryClient = useQueryClient();

  return useMutation(
    (movieId: string) => UserService.removeFromWishlistForUser(movieId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries("wishlist");
      },
    }
  );
};

export default useRemoveFromWishlistForUser;

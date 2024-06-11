import { useQuery } from "react-query";
import { UserService } from "../services";
import { Movie } from "../utils/types";

const useIsMovieInCart = (movieId: string) => {
  return useQuery(["cart", movieId], async () => {
    const cart: Movie[] = await UserService.getCartForUser();
    return cart.some((movie) => movie.id === movieId);
  });
};

export default useIsMovieInCart;

import { useQuery } from "react-query";
import { UserService } from "../services";
import { Movie } from "../utils/types";

const useIsMovieInWishlist = (movieId: string) => {
  return useQuery(["wishlist", movieId], async () => {
    const wishlist: Movie[] = await UserService.getWishlistForUser();
    return wishlist.some((movie) => movie.id === movieId);
  });
};

export default useIsMovieInWishlist;

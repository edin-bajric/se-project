import { useQuery } from "react-query";
import { UserService } from "../services";

const useWishlist = () => {
  return useQuery("wishlist", async () => {
    const movies = await UserService.getWishlistForUser();
    return movies;
  });
};

export default useWishlist;

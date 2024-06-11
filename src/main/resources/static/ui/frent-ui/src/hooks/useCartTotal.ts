import { useQuery } from "react-query";
import { UserService } from "../services";

const useCartTotal = () => {
  return useQuery("cartTotal", async () => {
    const cartTotal = await UserService.getCartTotalForUser();

    return cartTotal;
  });
};

export default useCartTotal;

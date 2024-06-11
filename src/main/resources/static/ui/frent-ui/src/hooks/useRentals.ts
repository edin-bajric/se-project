import { useQuery } from "react-query";
import { RentalService } from "../services";

const useRentals = () => {
  return useQuery("rentalsMovies", async () => {
    const rentalsWithMovies = await RentalService.getRentalsForUser();

    return rentalsWithMovies.reverse();
  });
};

export default useRentals;

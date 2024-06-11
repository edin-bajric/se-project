import { useMutation, useQueryClient } from "react-query";
import RentalService from "../services/rentals";

const useReturnRentalForUser = () => {
  const queryClient = useQueryClient();

  return useMutation(
    (rentalId: string) => RentalService.returnRentalForUser(rentalId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries("rentalsMovies");
      },
    }
  );
};

export default useReturnRentalForUser;

import { useMutation, useQueryClient } from "react-query";
import RentalService from "../services/rentals";

type AddRentalPayload = {
  movieId: string;
};

const useAddRentalForUser = () => {
  const queryClient = useQueryClient();

  return useMutation(
    (payload: AddRentalPayload) =>
      RentalService.addRentalForUser(payload.movieId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries("rentalsMovies");
        queryClient.invalidateQueries("rentalsTotal");
      },
    }
  );
};

export default useAddRentalForUser;

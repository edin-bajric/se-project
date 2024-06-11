import { useMutation, useQueryClient } from "react-query";
import { MovieService } from "../services";

const useSetMovieAvailable = () => {
  const queryClient = useQueryClient();

  return useMutation(
    (movieId: string) => MovieService.setMovieAvailable(movieId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries("movies");
      },
    }
  );
};

export default useSetMovieAvailable;

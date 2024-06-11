import { useMutation, useQueryClient } from "react-query";
import { MovieService } from "../services";

const useSetMovieUnavailable = () => {
  const queryClient = useQueryClient();

  return useMutation(
    (movieId: string) => MovieService.setMovieUnavailable(movieId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries("movies");
      },
    }
  );
};

export default useSetMovieUnavailable;

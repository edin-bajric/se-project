import { useMutation, useQueryClient } from "react-query";
import MovieService from "../services/movies";
import { Movie } from "../utils/types";

type AddMoviePayload = Movie;

const useUpdateMovie = () => {
  const queryClient = useQueryClient();

  return useMutation(
    (payload: AddMoviePayload) => MovieService.updateMovie(payload),
    {
      onSuccess: () => {
        queryClient.invalidateQueries("movies");
      },
    }
  );
};

export default useUpdateMovie;

import { useMutation, useQueryClient } from "react-query";
import MovieService from "../services/movies"; 
import { Movie } from "../utils/types";

type AddMoviePayload = Movie;

const useAddMovie = () => {
  const queryClient = useQueryClient();

  return useMutation(
    (payload: AddMoviePayload) => MovieService.addMovie(payload),
    {
      onSuccess: () => {
        queryClient.invalidateQueries("movies");
      },
    }
  );
};

export default useAddMovie;

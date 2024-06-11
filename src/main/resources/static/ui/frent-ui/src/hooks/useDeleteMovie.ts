import { useMutation, useQueryClient } from "react-query";
import { MovieService } from "../services";

const useDeleteMovie = () => {
  const queryClient = useQueryClient();

  return useMutation((movieId: string) => MovieService.deleteMovie(movieId), {
    onSuccess: () => {
      queryClient.invalidateQueries("movies");
    },
  });
};

export default useDeleteMovie;

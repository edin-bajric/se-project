import { useQuery } from "react-query";
import { MovieService } from "../services";

const useMovies = (page: number, size: number) => {
  return useQuery(["movies", page, size], () => MovieService.getMovies(page, size));
};

export default useMovies;

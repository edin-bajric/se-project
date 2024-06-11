import { useEffect, useState } from "react";
import { MovieService } from "../services";

const useSearchMovies = (keyword: string, page: number, size: number) => {
  const [data, setData] = useState<any>(null);
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true);
        const result = await MovieService.searchMovies(keyword, page, size);
        setData(result);
      } catch (err) {
        setError("Error searching movies");
      } finally {
        setIsLoading(false);
      }
    };

    if (keyword) {
      fetchData();
    }

    return () => {
      setData(null);
      setError(null);
      setIsLoading(true);
    };
  }, [keyword, page, size]);

  return { data, error, isLoading };
};

export default useSearchMovies;

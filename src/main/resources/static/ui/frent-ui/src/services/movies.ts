import appAxios from "./appAxios";
import { Movie } from "../utils/types";

const getMovies = async (page: number, size: number): Promise<Movie[]> => {
  return appAxios.get(`/movies/?page=${page}&size=${size}`).then((response) => {
    const data = response.data;
    return data;
  });
};

const getMovieById = async (id: string): Promise<Movie> => {
  return appAxios.get(`/movies/${id}`).then((response) => {
    const data = response.data;
    return data;
  });
};

const addMovie = async (movie: Movie): Promise<Movie> => {
  try {
    const token = localStorage.getItem("userToken");
    if (!token) {
      throw new Error("User token not found.");
    }

    const response = await appAxios.post("/movies/add", movie, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
  } catch (error: any) {
    throw new Error("Failed to add movie: " + error.message);
  }
};

const deleteMovie = async (id: string): Promise<Movie> => {
  try {
    const token = localStorage.getItem("userToken");
    if (!token) {
      throw new Error("User token not found.");
    }

    const response = await appAxios.delete(`/movies/${id}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
  } catch (error: any) {
    throw new Error("Failed to delete movie: " + error.message);
  }
};

const updateMovie = async (movie: Movie): Promise<Movie> => {
  try {
    const token = localStorage.getItem("userToken");
    if (!token) {
      throw new Error("User token not found.");
    }

    const response = await appAxios.put(`/movies/${movie.id}`, movie, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
  } catch (error: any) {
    throw new Error("Failed to update movie: " + error.message);
  }
};

const searchMovies = async (keyword: string, page: number, size: number) => {
  try {
    const response = await appAxios.get(
      `/movies/search/${keyword}/${page}/${size}`
    );
    return response.data;
  } catch (error: any) {
    throw error.response?.data || error.message || "Error searching movies";
  }
};

const setMovieAvailable = async (movieId: string): Promise<void> => {
  try {
    await appAxios.put(`movies/setAvailable/${movieId}`, null, {
      headers: { Authorization: `Bearer ${localStorage.getItem("userToken")}` },
    });
  } catch (error: any) {
    throw new Error("Failed to set movie available: " + error.message);
  }
};

const setMovieUnavailable = async (movieId: string): Promise<void> => {
  try {
    await appAxios.put(`movies/setUnavailable/${movieId}`, null, {
      headers: { Authorization: `Bearer ${localStorage.getItem("userToken")}` },
    });
  } catch (error: any) {
    throw new Error("Failed to set movie unavailable: " + error.message);
  }
};

const getAllMovies = async (): Promise<Movie[]> => {
  try {
    const response = await appAxios.get("movies/allMovies", {
      headers: { Authorization: `Bearer ${localStorage.getItem("userToken")}` },
    });
    return response.data;
  } catch (error: any) {
    throw new Error("Failed to fetch all movies: " + error.message);
  }
};


export default {
  getMovies,
  getMovieById,
  addMovie,
  deleteMovie,
  updateMovie,
  searchMovies,
  setMovieAvailable,
  setMovieUnavailable,
  getAllMovies,
};

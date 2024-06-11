import appAxios from "./appAxios";
import { Movie, User } from "../utils/types";
import { MovieService } from ".";

const getMovieById = MovieService.getMovieById;

const getCartForUser = async (): Promise<Movie[]> => {
  const token = localStorage.getItem("userToken");
  if (!token) return [];
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  return appAxios.get("/users/cart", { headers }).then(async (response) => {
    const movieIds: string[] = response.data;
    const movieDetailsPromises = movieIds.map((movieId: string) =>
      getMovieById(movieId)
    );
    const movies = await Promise.all(movieDetailsPromises);
    return movies;
  });
};

const getCartTotalForUser = async (): Promise<number> => {
  const token = localStorage.getItem("userToken");
  if (!token) return 0;
  const headers = {
    Authorization: `Bearer ${token}`,
  };
  return appAxios.get("/users/cartTotal", { headers }).then((response) => {
    const cartTotal: number = response.data;
    return cartTotal;
  });
};

const getWishlistForUser = async (): Promise<Movie[]> => {
  const token = localStorage.getItem("userToken");
  if (!token) return [];
  const headers = {
    Authorization: `Bearer ${token}`,
  };

  return appAxios.get("/users/wishlist", { headers }).then((response) => {
    const movieIds: string[] = response.data;
    const movieDetailsPromises = movieIds.map((movieId: string) =>
      getMovieById(movieId)
    );
    const movies = Promise.all(movieDetailsPromises);
    return movies;
  });
};

const addToCartForUser = async (movieId: string): Promise<void> => {
  const token = localStorage.getItem("userToken");

  const headers = {
    Authorization: `Bearer ${token}`,
  };

  await appAxios.put(`/users/addToCart/${movieId}`, {}, { headers });
};

const removeFromCart = async (movieId: string): Promise<void> => {
  const token = localStorage.getItem("userToken");

  const headers = {
    Authorization: `Bearer ${token}`,
  };

  await appAxios.put(`/users/removeFromCart/${movieId}`, {}, { headers });
};

const addToWishlistForUser = async (movieId: string): Promise<void> => {
  const token = localStorage.getItem("userToken");

  const headers = {
    Authorization: `Bearer ${token}`,
  };

  await appAxios.put(`/users/addToWishlist/${movieId}`, {}, { headers });
};

const removeFromWishlistForUser = async (movieId: string): Promise<void> => {
  const token = localStorage.getItem("userToken");

  const headers = {
    Authorization: `Bearer ${token}`,
  };

  await appAxios.put(`/users/removeFromWishlist/${movieId}`, {}, { headers });
};

const isMovieInWishlist = async (movieId: string): Promise<boolean> => {
  const wishlist = await getWishlistForUser();
  return wishlist.some((movie) => movie.id === movieId);
};

const isMovieInCart = async (movieId: string): Promise<boolean> => {
  const cart = await getCartForUser();
  return cart.some((movie) => movie.id === movieId);
};

const getUsers = async (): Promise<User[]> => {
  try {
    const response = await appAxios.get("users/", {
      headers: { Authorization: `Bearer ${localStorage.getItem("userToken")}` },
    });
    return response.data;
  } catch (error: any) {
    throw new Error("Failed to fetch all users: " + error.message);
  }
};

const deleteUser = async (id: string): Promise<User> => {
  try {
    const token = localStorage.getItem("userToken");
    if (!token) {
      throw new Error("User token not found.");
    }

    const response = await appAxios.delete(`/users/${id}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
  } catch (error: any) {
    throw new Error("Failed to delete user: " + error.message);
  }
};

const suspendUser = async (user: User): Promise<User> => {
  try {
    const token = localStorage.getItem("userToken");
    if (!token) {
      throw new Error("User token not found.");
    }

    const response = await appAxios.patch(`/users/suspend/${user.id}`, user, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
  } catch (error: any) {
    throw new Error("Failed to suspend user: " + error.message);
  }
};

const unsuspendUser = async (user: User): Promise<User> => {
  try {
    const token = localStorage.getItem("userToken");
    if (!token) {
      throw new Error("User token not found.");
    }

    const response = await appAxios.patch(`/users/unsuspend/${user.id}`, user, {
      headers: { Authorization: `Bearer ${token}` },
    });
    return response.data;
  } catch (error: any) {
    throw new Error("Failed to unsuspend user: " + error.message);
  }
};

export default {
  getCartForUser,
  getCartTotalForUser,
  getWishlistForUser,
  addToCartForUser,
  removeFromCart,
  addToWishlistForUser,
  removeFromWishlistForUser,
  isMovieInWishlist,
  isMovieInCart,
  getUsers,
  deleteUser,
  suspendUser,
  unsuspendUser,
};

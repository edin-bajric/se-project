import { useState } from "react";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import Badge from "react-bootstrap/Badge";
import { Movie } from "../../utils/types";
import "../../assets/css/MovieRentalCard.css";
import useAddToCartForUser from "../../hooks/useAddToCart";
import useAddToWishlistForUser from "../../hooks/useAddToWishlist";
import useIsMovieInWishlist from "../../hooks/useIsMovieInWishlist";
import useIsMovieInCart from "../../hooks/useIsMovieInCart";
import DangerAlert from "../DangerAlert";
import SuccessAlert from "../SuccessAlert";
import MovieInfo from "../MovieInfo";
import useCartTotal from "../../hooks/useCartTotal";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useNavigate } from "react-router-dom";

type Props = {
  movie: Movie;
};

const BasicExample = ({ movie }: Props) => {
  const navigate = useNavigate();
  const { userToken } = useSelector((state: RootState) => state.auth);
  const [showDangerAlert, setShowDangerAlert] = useState(false);
  const [showSuccessAlert, setShowSuccessAlert] = useState(false);
  const [showSuccessAlertWishlist, setShowSuccessAlertWishlist] =
    useState(false);
  const [showMovieInfo, setShowMovieInfo] = useState(false);
  const addToCartMutation = useAddToCartForUser();
  const addToWishlistMutation = useAddToWishlistForUser();
  const isMovieInWishlistQuery = useIsMovieInWishlist(movie.id);
  const isMovieInCartQuery = useIsMovieInCart(movie.id);
  const { refetch: refetchCartTotal } = useCartTotal();

  const handleAddToCartClick = () => {
    if (!userToken) {
      navigate("/login");
      return;
    }
    const movieId = movie.id;
    addToCartMutation.mutate(movieId, {
      onSuccess: () => {
        setShowSuccessAlert(true);
        refetchCartTotal();
      },

      onError: (error: any) => {
        console.error("Error adding to cart:", error);
        if (error.response && error.response.status === 500) {
          setShowDangerAlert(true);
        } else {
          alert("An error occurred while adding to cart.");
        }
      },
    });
  };

  const handleAddToWishlistClick = () => {
    if (!userToken) {
      navigate("/login");
      return;
    }
    const movieId = movie.id;
    addToWishlistMutation.mutate(movieId, {
      onSuccess: () => {
        setShowSuccessAlertWishlist(true);
      },
    });
  };

  const handleMovieCardClick = () => {
    setShowMovieInfo(true);
  };

  return (
    <>
      {showSuccessAlertWishlist && (
        <SuccessAlert
          message="Movie successfully added to wishlist."
          onClose={() => setShowSuccessAlertWishlist(false)}
          width="18rem"
        />
      )}
      {showSuccessAlert && (
        <SuccessAlert
          message="Movie successfully added to cart."
          onClose={() => setShowSuccessAlert(false)}
          width="18rem"
        />
      )}
      {showDangerAlert && (
        <DangerAlert
          message="Movie is not available."
          onClose={() => setShowDangerAlert(false)}
        />
      )}
      <Card style={{ width: "18rem" }} bg="dark" text="light">
        <Card.Img
          variant="top"
          src={movie.smallImage}
          style={{ height: "18rem", objectFit: "cover", cursor: "pointer" }}
          onClick={handleMovieCardClick}
        />
        <Card.Body>
          <Card.Title as="h6">{movie.title}</Card.Title>
          <Card.Text as="h6">{movie.rentalPrice}KM</Card.Text>
          <Badge
            bg={movie.available ? "success" : "danger"}
            style={{ marginBottom: "8px" }}
          >
            {movie.available ? "Available" : "Unavailable"}
          </Badge>
          <Card.Text className="clamp-two-lines">{movie.description}</Card.Text>
          <div className="d-flex justify-content-between">
            <Button
              variant="primary"
              disabled={isMovieInCartQuery.data || !movie.available}
              onClick={handleAddToCartClick}
            >
              {isMovieInCartQuery.data ? "In cart" : "Add to cart"}
            </Button>
            <Button
              variant="success"
              onClick={handleAddToWishlistClick}
              disabled={isMovieInWishlistQuery.data}
            >
              {isMovieInWishlistQuery.data ? "In wishlist" : "Add to wishlist"}
            </Button>
          </div>
        </Card.Body>
      </Card>
      <MovieInfo
        show={showMovieInfo}
        handleClose={() => setShowMovieInfo(false)}
        movie={movie}
      />
    </>
  );
};

export default BasicExample;

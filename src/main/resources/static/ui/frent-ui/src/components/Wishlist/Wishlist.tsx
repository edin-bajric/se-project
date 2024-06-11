import React, { useEffect } from "react";
import { Offcanvas, ListGroup, Button, CloseButton } from "react-bootstrap";
import useWishlist from "../../hooks/useWishlist";
import Spinner from "../Spinner";
import Error from "../Error";
import useRemoveFromWishlistForUser from "../../hooks/useRemoveFromWishlist";
import useAddToCartForUser from "../../hooks/useAddToCart";
import useCartTotal from "../../hooks/useCartTotal";
import DangerAlert from "../DangerAlert";
import SuccessAlert from "../SuccessAlert";
import { useState } from "react";

type WishlistProps = {
  show: boolean;
  handleClose: () => void;
};

const Wishlist: React.FC<WishlistProps> = ({ show, handleClose }) => {
  const [showDangerAlert, setShowDangerAlert] = useState(false);
  const [showSuccessAlert, setShowSuccessAlert] = useState(false);
  const { data: movies, isLoading, isError, refetch } = useWishlist();
  const removeFromWishlistMutation = useRemoveFromWishlistForUser();
  const addToCartMutation = useAddToCartForUser();
  const { refetch: refetchCartTotal } = useCartTotal();

  const handleRemoveFromWishlistClick = (movieId: string) => {
    removeFromWishlistMutation.mutate(movieId);
  };

  const handleAddToCartClick = async (movieId: string) => {
    try {
      await addToCartMutation.mutateAsync(movieId);

      await removeFromWishlistMutation.mutateAsync(movieId);
      setShowSuccessAlert(true);
      refetchCartTotal();
    } catch (error) {
      console.error("Error adding to cart:", error);
      setShowDangerAlert(true);
    }
  };

  useEffect(()=>{
    if(show){
      refetch();
    }
  },[show]);

  return (
    <Offcanvas
      data-bs-theme="dark"
      show={show}
      onHide={handleClose}
      placement="end"
    >
      <Offcanvas.Header closeButton>
        <Offcanvas.Title>Wishlist</Offcanvas.Title>
      </Offcanvas.Header>
      <Offcanvas.Body>
        {isLoading && <Spinner />}
        {isError && <Error />}
        {!isLoading && !isError && (
          <ListGroup as="ol">
            {movies?.map((movie) => (
              <ListGroup.Item
                key={movie.id}
                as="li"
                className="d-flex justify-content-between align-items-start"
                variant="light"
              >
                <div className="ms-2 me-auto">
                  <div className="fw-bold">{movie.title}</div>
                  {movie.rentalPrice}KM
                </div>
                <Button
                  variant="primary"
                  style={{ marginRight: "16px" }}
                  onClick={() => handleAddToCartClick(movie.id)}
                  disabled={!movie.available}
                >
                  Add to cart
                </Button>
                <CloseButton
                  onClick={() => handleRemoveFromWishlistClick(movie.id)}
                />
              </ListGroup.Item>
            ))}
          </ListGroup>
        )}
      </Offcanvas.Body>
      {showSuccessAlert && (
        <SuccessAlert
          message="Movie successfully added to cart."
          onClose={() => setShowSuccessAlert(false)}
          width = "100%"
        />
      )}
      {showDangerAlert && (
        <DangerAlert
          message="Movie is not available."
          onClose={() => setShowDangerAlert(false)}
        />
      )}
    </Offcanvas>
  );
};

export default Wishlist;

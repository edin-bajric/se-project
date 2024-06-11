import { useState } from "react";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import Badge from "react-bootstrap/Badge";
import { RentalMovie } from "../../utils/types";
import "../../assets/css/MovieRentalCard.css";
import useReturnRentalForUser from "../../hooks/useReturnRentals";
import RentalInfo from "../RentalInfo";
import MoviePlayer from "../MoviePlayer";
import Modal from "react-bootstrap/Modal";

type Props = {
  rentalMovie: RentalMovie;
};

const BasicExample = ({ rentalMovie }: Props) => {
  const [isReturned, setIsReturned] = useState(rentalMovie.returned);
  const [showRentalInfo, setShowRentalInfo] = useState(false);
  const [showMoviePlayer, setShowMoviePlayer] = useState(false);
  const returnRentalMutation = useReturnRentalForUser();

  const handleReturnClick = () => {
    const rentalId = rentalMovie.id;

    returnRentalMutation.mutate(rentalId, {
      onSuccess: (data) => {
        rentalMovie.returnDate = data.returnDate;
        setIsReturned(true);
      },
    });
  };

  const handleRentalCardClick = () => {
    setShowRentalInfo(true);
  };

  const handleWatchClick = () => {
    setShowMoviePlayer(true);
  };

  const handleCloseMoviePlayer = () => {
    setShowMoviePlayer(false);
  };

  return (
    <>
      <Card style={{ width: "18rem" }} bg="dark" text="light">
        <Card.Img
          variant="top"
          src={rentalMovie.smallImage}
          style={{ height: "18rem", objectFit: "cover", cursor: "pointer" }}
          onClick={handleRentalCardClick}
        />
        <Card.Body>
          <Card.Title as="h6">{rentalMovie.title}</Card.Title>
          <Card.Text style={{ color: isReturned ? "gray" : "crimson" }} as="h5">
            {isReturned
              ? 
                returnRentalMutation.isLoading
                ? "Loading..."
                : "Returned on: " + rentalMovie.returnDate
              : "Valid until: " + rentalMovie.dueDate}
          </Card.Text>
          <Badge bg="secondary" style={{ marginBottom: "8px" }}>
            {isReturned ? "Returned" : "Rented"}
          </Badge>
          <Card.Text className="clamp-two-lines">
            {rentalMovie.description}
          </Card.Text>
          <div className="d-flex justify-content-between">
            <Button
              variant="primary"
              onClick={handleReturnClick}
              disabled={isReturned}
            >
              Return
            </Button>
            <Button
              variant="success"
              onClick={handleWatchClick}
              disabled={isReturned}
              style={{ display: isReturned ? "none" : "inline-block" }}
            >
              Watch
            </Button>
          </div>
        </Card.Body>
      </Card>
      <RentalInfo
        show={showRentalInfo}
        handleClose={() => setShowRentalInfo(false)}
        rentalMovie={rentalMovie}
        isReturned={isReturned}
      />
      <Modal
        show={showMoviePlayer}
        onHide={handleCloseMoviePlayer}
        size={"lg"}
        centered={true}
        data-bs-theme="dark"
      >
        <Modal.Header closeButton style={{ background: "#2b3035" }}>
          <Modal.Title style={{ color: "white" }}>
            {rentalMovie.title}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body style={{ height: "500px" }}>
          <MoviePlayer video={rentalMovie.video} />
        </Modal.Body>
      </Modal>
    </>
  );
};

export default BasicExample;

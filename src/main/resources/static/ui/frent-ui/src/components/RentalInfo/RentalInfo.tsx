import Offcanvas from "react-bootstrap/Offcanvas";
import Badge from "react-bootstrap/Badge";
import { RentalMovie, joinGenres } from "../../utils/types";

type MovieInfoProps = {
  show: boolean;
  handleClose: () => void;
  rentalMovie: RentalMovie;
  isReturned: boolean;
};

const MovieInfo: React.FC<MovieInfoProps> = ({
  show,
  handleClose,
  rentalMovie,
  isReturned,
}) => {
  const renderReturnDate = () => {
    if (rentalMovie.returnDate) {
      return <p>Returned on: {rentalMovie.returnDate.toString()}</p>;
    }
    return null;
  };
  const genres = joinGenres(rentalMovie.genre);

  return (
    <>
      <Offcanvas
        data-bs-theme="dark"
        show={show}
        onHide={handleClose}
        placement="end"
      >
        <Offcanvas.Header closeButton>
          <Offcanvas.Title>{rentalMovie.title}</Offcanvas.Title>
        </Offcanvas.Header>
        <Offcanvas.Body>
          <img
            src={rentalMovie.smallImage}
            alt={rentalMovie.title}
            style={{ width: "100%" }}
          />
          <Badge
            bg={rentalMovie.available ? "success" : "danger"}
            style={{
              marginBottom: "8px",
              marginTop: "8px",
              marginRight: "8px",
            }}
          >
            {rentalMovie.available ? "Available" : "Unavailable"}
          </Badge>
          <Badge bg="secondary" style={{ marginBottom: "8px" }}>
            {isReturned ? "Returned" : "Rented"}
          </Badge>
          <p>Rented on: {rentalMovie.rentalDate.toString()}</p>
          <p>Valid until: {rentalMovie.dueDate.toString()}</p>
          {renderReturnDate()}
          <p>{rentalMovie.description}</p>
          <p>Year: {rentalMovie.year}</p>
          <p>Director: {rentalMovie.director}</p>
          <p>Genre: {genres}</p>
          <p>Price: {rentalMovie.rentalPrice}KM</p>
        </Offcanvas.Body>
      </Offcanvas>
    </>
  );
};

export default MovieInfo;

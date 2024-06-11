import Offcanvas from "react-bootstrap/Offcanvas";
import Badge from "react-bootstrap/Badge";
import { Movie, joinGenres } from "../../utils/types";

type MovieInfoProps = {
  show: boolean;
  handleClose: () => void;
  movie: Movie;
};

const MovieInfo: React.FC<MovieInfoProps> = ({ show, handleClose, movie }) => {
  const genres = joinGenres(movie.genre);

  return (
    <>
      <Offcanvas
        data-bs-theme="dark"
        show={show}
        onHide={handleClose}
        placement="end"
      >
        <Offcanvas.Header closeButton>
          <Offcanvas.Title>{movie.title}</Offcanvas.Title>
        </Offcanvas.Header>
        <Offcanvas.Body>
          <img
            src={movie.smallImage}
            alt={movie.title}
            style={{ width: "100%" }}
          />
          <Badge
            bg={movie.available ? "success" : "danger"}
            style={{ marginBottom: "8px", marginTop: "8px" }}
          >
            {movie.available ? "Available" : "Unavailable"}
          </Badge>
          <p>{movie.description}</p>
          <p>Year: {movie.year}</p>
          <p>Director: {movie.director}</p>
          <p>Genre: {genres}</p>
          <p>Price: {movie.rentalPrice}KM</p>
        </Offcanvas.Body>
      </Offcanvas>
    </>
  );
};

export default MovieInfo;

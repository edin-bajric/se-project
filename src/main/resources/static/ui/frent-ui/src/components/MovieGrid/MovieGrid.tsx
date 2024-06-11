import { Row, Col } from "react-bootstrap";
import MovieCard from "../MovieCard";
import Spinner from "../Spinner";
import useMovies from "../../hooks/useMovies";
import Error from "../Error";

type MoviesGridProps = {
  page: number;
  size: number;
};

const MoviesGrid = ({ page, size }: MoviesGridProps) => {
  const { data: movies, error, isLoading, isError } = useMovies(page, size);

  return (
    <>
      {isLoading && <Spinner />}
      {error && <Error />}
      {!isLoading && !isError && (
        <Row
          xs={1}
          md={2}
          lg={3}
          xl={4}
          xxl={5}
          className="g-4"
          style={{ width: "100%", padding: "16px" }}
        >
          {movies?.map((movie) => (
            <Col key={movie.id}>
              <MovieCard movie={movie} />
            </Col>
          ))}
        </Row>
      )}
    </>
  );
};

export default MoviesGrid;

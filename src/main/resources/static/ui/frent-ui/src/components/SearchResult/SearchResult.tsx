import { Row, Col } from "react-bootstrap";
import MovieCard from "../MovieCard";
import Spinner from "../Spinner";
import useSearchMovies from "../../hooks/useSearchMovies";
import { useParams } from "react-router-dom";
import Error from "../Error";

type SearchResultsProps = {
  page: number;
  size: number;
};

const SearchResults = ({ page, size }: SearchResultsProps) => {
  const { keyword } = useParams<{ keyword?: string }>();
  const {
    data: movies,
    error,
    isLoading,
  } = useSearchMovies(keyword || "", page, size);

  return (
    <>
      {isLoading && <Spinner />}
      {error && <Error />}
      {!isLoading && movies && (
        <>
          <Row
            xs={1}
            md={2}
            lg={3}
            xl={4}
            xxl={5}
            className="g-4"
            style={{ width: "100%", paddingLeft: "16px" }}
          >
            {movies.map((movie: any) => (
              <Col key={movie.id}>
                <MovieCard movie={movie} />
              </Col>
            ))}
          </Row>
        </>
      )}
    </>
  );
};

export default SearchResults;

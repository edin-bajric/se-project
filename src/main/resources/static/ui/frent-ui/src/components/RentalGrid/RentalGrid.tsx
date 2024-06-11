import { useState, useEffect } from "react";
import { Row, Col } from "react-bootstrap";
import RentalCard from "../RentalCard";
import Spinner from "../Spinner";
import useRentals from "../../hooks/useRentals";
import Error from "../Error";
import useRentalsTotal from "../../hooks/useRentalsTotal";

const RentalGrid = () => {
  const { data: rentalsMovies, error, isLoading, isError } = useRentals();
  const { data: rentalsTotal } = useRentalsTotal();
  const [page, setPage] = useState(1);

  const handleScroll = () => {
    const bottom =
      Math.ceil(window.innerHeight + window.scrollY) >=
      document.documentElement.scrollHeight;
    if (bottom) {
      setPage((prevPage) => prevPage + 1);
    }
  };

  useEffect(() => {
    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <>
      {isLoading && <Spinner />}
      {error && <Error />}
      {!isLoading && !isError && (
        <div>
          <h4 style={{paddingLeft: "16px", paddingTop: "32px"}}>Total spent: {rentalsTotal}KM</h4>
        <Row
          xs={1}
          md={2}
          lg={3}
          xl={4}
          xxl={5}
          className="g-4"
          style={{ width: "100%", padding: "16px" }}
        >
          {rentalsMovies?.map((rentalMovie) => (
            <Col key={rentalMovie.id}>
              <RentalCard rentalMovie={rentalMovie} />
            </Col>
          ))}
        </Row>
        </div>
      )}
    </>
  );
};

export default RentalGrid;

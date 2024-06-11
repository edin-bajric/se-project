import React, { useState, useEffect } from "react";
import { Pagination, Form } from "react-bootstrap";
import MovieGrid from "../MovieGrid";
import useMovies from "../../hooks/useMovies";

const MovieGridWithPagination = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const { data: movies } = useMovies(currentPage, pageSize);

  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  const handleSizeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setPageSize(Number(e.target.value));
    setCurrentPage(1);
  };

  const hasNextPage = movies?.length === pageSize;

  useEffect(() => {
    window.scrollTo({ top: 0, behavior: "smooth" });
  }, [currentPage]);

  return (
    <>
      <Form.Group className="mt-3" style={{ padding: "16px" }}>
        <h4>Results per page: </h4>
        <Form.Select
          onChange={handleSizeChange}
          value={pageSize}
          style={{ width: "70px" }}
        >
          <option value="5">5</option>
          <option value="10">10</option>
          <option value="15">15</option>
        </Form.Select>
      </Form.Group>
      <MovieGrid page={currentPage} size={pageSize} />
      <div className="d-flex justify-content-center mt-3">
        <Pagination>
          <Pagination.Prev
            onClick={() =>
              handlePageChange(currentPage > 1 ? currentPage - 1 : 1)
            }
            disabled={currentPage === 1}
          />
          <Pagination.Item active>{currentPage}</Pagination.Item>
          <Pagination.Next
            onClick={() => handlePageChange(currentPage + 1)}
            disabled={!hasNextPage}
          />
        </Pagination>
      </div>
    </>
  );
};

export default MovieGridWithPagination;

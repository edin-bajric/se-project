import React, { useState, useEffect } from "react";
import { Pagination, Form } from "react-bootstrap";
import useSearchMovies from "../../hooks/useSearchMovies";
import { useParams } from "react-router-dom";
import SearchResult from "../SearchResult";

const SearchResults = () => {
  const { keyword, page, size } = useParams<{
    keyword?: string;
    page?: string;
    size?: string;
  }>();
  const [currentPage, setCurrentPage] = useState(Number(page) || 1);
  const [pageSize, setPageSize] = useState(Number(size) || 5);
  const { data: movies } = useSearchMovies(
    keyword || "",
    currentPage,
    pageSize
  );

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
      <Form.Group
        className="mt-3"
        style={{
          padding: "16px",
          display: movies?.length === 0 ? "none" : "block",
        }}
      >
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
      <h3 style={{ padding: "16px" }}>
        {movies?.length === 0
          ? `No results found for "${keyword}"`
          : `Search results for "${keyword}"`}
      </h3>
      <SearchResult page={currentPage} size={pageSize} />
      <div className="d-flex justify-content-center mt-3">
        <Pagination style={{ display: movies?.length === 0 ? "none" : "flex" }}>
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

export default SearchResults;

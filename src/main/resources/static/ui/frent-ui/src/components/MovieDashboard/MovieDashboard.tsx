import { useState } from "react";
import { Button, Modal, Badge } from "react-bootstrap";
import DataTable, { TableColumn } from "react-data-table-component";
import useAllMovies from "../../hooks/useAllMovies";
import { Movie } from "../../utils/types";
import AddMovie from "../AddMovie";
import useDeleteMovie from "../../hooks/useDeleteMovie";
import UpdateMovie from "../UpdateMovie";
import useSetMovieAvailable from "../../hooks/useSetMovieAvailable";
import useSetMovieUnavailable from "../../hooks/useSetMovieUnavailable";

const MovieDashboard = () => {
  const { data: movies = [], isLoading } = useAllMovies();
  const [showAddMovie, setShowAddMovie] = useState(false);
  const { mutate: deleteMovie } = useDeleteMovie();
  const [showConfirmation, setShowConfirmation] = useState(false);
  const [movieIdToDelete, setMovieIdToDelete] = useState("");
  const [showUpdateMovie, setShowUpdateMovie] = useState(false);
  const [selectedMovie, setSelectedMovie] = useState<Movie | null>(null);
  const { mutate: setMovieAvailable } = useSetMovieAvailable();
  const { mutate: setMovieUnavailable } = useSetMovieUnavailable();

  const handleAddMovieClick = () => {
    setShowAddMovie(true);
  };

  const handleCloseAddMovie = () => {
    setShowAddMovie(false);
  };

  const handleDeleteConfirmation = (movieId: any) => {
    setMovieIdToDelete(movieId);
    setShowConfirmation(true);
  };

  const handleCloseConfirmation = () => {
    setShowConfirmation(false);
    setMovieIdToDelete("");
  };

  const handleDeleteMovie = async () => {
    try {
      await deleteMovie(movieIdToDelete);
      handleCloseConfirmation();
    } catch (error) {
      console.error("Error deleting movie:", error);
    }
  };

  const handleUpdateMovieClick = (movie: Movie) => {
    setSelectedMovie(movie);
    setShowUpdateMovie(true);
  };

  const handleToggleAvailability = async (movie: Movie) => {
    try {
      if (movie.available) {
        await setMovieUnavailable(movie.id);
      } else {
        await setMovieAvailable(movie.id);
      }
    } catch (error) {
      console.error("Error toggling movie availability:", error);
    }
  };

  const deleteButtonColumn: TableColumn<Movie> = {
    name: "Delete",
    button: true,
    cell: (row: Movie) => (
      <>
        <Button
          variant="danger"
          onClick={() => handleDeleteConfirmation(row.id)}
        >
          Delete
        </Button>
      </>
    ),
  };

  const updateButtonColumn: TableColumn<Movie> = {
    name: "Update",
    button: true,
    cell: (row: Movie) => (
      <>
        <Button variant="warning" onClick={() => handleUpdateMovieClick(row)}>
          Update
        </Button>
      </>
    ),
  };

  const columns: TableColumn<Movie>[] = [
    deleteButtonColumn,
    updateButtonColumn,
    {
      name: "Title",
      selector: (row: Movie) => row.title,
      sortable: true,
    },
    {
      name: "Available",
      cell: (row: Movie) => (
        <Badge
          bg={row.available ? "success" : "danger"}
          onClick={() => handleToggleAvailability(row)}
          style={{ cursor: "pointer" }}
        >
          {row.available ? "Available" : "Unavailable"}
        </Badge>
      ),
      sortable: true,
    },
    {
      name: "Description",
      selector: (row: Movie) => row.description,
    },
    {
      name: "Small Image",
      cell: (row: Movie) => (
        <a href={row.smallImage}>
          <img
            src={row.smallImage}
            alt="small"
            style={{ width: 50, height: 50 }}
          />
        </a>
      ),
      center: true,
    },
    {
      name: "Big Image",
      cell: (row: Movie) => (
        <a href={row.bigImage}>
          <img
            src={row.bigImage}
            alt="big"
            style={{ width: 100, height: 50 }}
          />
        </a>
      ),
      center: true,
    },
    {
      name: "Director",
      selector: (row: Movie) => row.director,
      sortable: true,
    },
    {
      name: "Genre",
      selector: (row: Movie) => row.genre.join(", "),
      sortable: true,
    },
    {
      name: "Year",
      selector: (row: Movie) => row.year,
      sortable: true,
    },
    {
      name: "Rental Price",
      selector: (row: Movie) => row.rentalPrice,
      sortable: true,
    },
    {
      name: "Video",
      selector: (row: Movie) => row.video,
    },
  ];

  return (
    <>
      <Button
        onClick={handleAddMovieClick}
        style={{ margin: "16px" }}
        variant="primary"
      >
        Add Movie
      </Button>
      <DataTable
        columns={columns}
        data={movies}
        progressPending={isLoading}
        progressComponent={<h2>Loading...</h2>}
        noDataComponent={<h2>No movies found</h2>}
        pagination
        responsive
        striped
        highlightOnHover
      />
      <Modal show={showConfirmation} onHide={handleCloseConfirmation}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Deletion</Modal.Title>
        </Modal.Header>
        <Modal.Body>Are you sure you want to delete this movie?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseConfirmation}>
            Cancel
          </Button>
          <Button variant="danger" onClick={handleDeleteMovie}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>
      <AddMovie show={showAddMovie} handleClose={handleCloseAddMovie} />
      {selectedMovie && (
        <UpdateMovie
          show={showUpdateMovie}
          movie={selectedMovie}
          handleClose={() => setShowUpdateMovie(false)}
        />
      )}
    </>
  );
};

export default MovieDashboard;

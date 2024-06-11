import React, { useState, useEffect } from "react";
import { Offcanvas, Form, Button } from "react-bootstrap";
import useUpdateMovie from "../../hooks/useUpdateMovie";
import { Movie } from "../../utils/types";

const genres = [
  "ACTION",
  "ADVENTURE",
  "ANIMATION",
  "COMEDY",
  "CRIME",
  "DRAMA",
  "FANTASY",
  "HORROR",
  "MYSTERY",
  "SCIENCE_FICTION",
  "THRILLER",
  "WESTERN",
];

type UpdateMovieProps = {
  show: boolean;
  movie: Movie;
  handleClose: () => void;
};

const UpdateMovie: React.FC<UpdateMovieProps> = ({
  show,
  movie,
  handleClose,
}) => {
  const [formData, setFormData] = useState(movie);

  const { mutate: updateMovie } = useUpdateMovie();

  useEffect(() => {
    setFormData(movie);
  }, [movie]);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleGenreChange = (genre: string) => {
    const updatedGenres = formData.genre.includes(genre)
      ? formData.genre.filter((g) => g !== genre)
      : [...formData.genre, genre];
    setFormData({
      ...formData,
      genre: updatedGenres,
    });
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    updateMovie(formData);
    handleClose();
  };

  return (
    <Offcanvas
      show={show}
      onHide={handleClose}
      placement="end"
      data-bs-theme="dark"
    >
      <Offcanvas.Header closeButton>
        <Offcanvas.Title>Update Movie</Offcanvas.Title>
      </Offcanvas.Header>
      <Offcanvas.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3" controlId="title">
            <Form.Label>Title</Form.Label>
            <Form.Control
              type="text"
              name="title"
              value={formData.title}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="description">
            <Form.Label>Description</Form.Label>
            <Form.Control
              as="textarea"
              name="description"
              value={formData.description}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="smallImage">
            <Form.Label>Small Image</Form.Label>
            <Form.Control
              type="text"
              name="smallImage"
              value={formData.smallImage}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="bigImage">
            <Form.Label>Big Image</Form.Label>
            <Form.Control
              type="text"
              name="bigImage"
              value={formData.bigImage}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="director">
            <Form.Label>Director</Form.Label>
            <Form.Control
              type="text"
              name="director"
              value={formData.director}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="genre">
            <Form.Label>Genre</Form.Label>
            {genres.map((genre: string) => (
              <Form.Check
                key={genre}
                type="checkbox"
                label={genre}
                checked={formData.genre.includes(genre)}
                onChange={() => handleGenreChange(genre)}
              />
            ))}
          </Form.Group>
          <Form.Group className="mb-3" controlId="year">
            <Form.Label>Year</Form.Label>
            <Form.Control
              type="number"
              name="year"
              value={formData.year}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="available">
            <Form.Label>Available</Form.Label>
            <Form.Control
              as="select"
              name="available"
              value={formData.available.toString()}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  available: e.target.value === "true",
                })
              }
            >
              <option value="true">True</option>
              <option value="false">False</option>
            </Form.Control>
          </Form.Group>
          <Form.Group className="mb-3" controlId="rentalPrice">
            <Form.Label>Rental Price</Form.Label>
            <Form.Control
              type="number"
              name="rentalPrice"
              value={formData.rentalPrice}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="video">
            <Form.Label>Video</Form.Label>
            <Form.Control
              type="text"
              name="video"
              value={formData.video}
              onChange={handleChange}
            />
          </Form.Group>
          <Button variant="primary" type="submit">
            Update Movie
          </Button>
        </Form>
      </Offcanvas.Body>
    </Offcanvas>
  );
};

export default UpdateMovie;

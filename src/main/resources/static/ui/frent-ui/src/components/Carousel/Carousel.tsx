import Carousel from "react-bootstrap/Carousel";
import browseImage from "../../assets/img/browseImage.jpeg";
import rentImage from "../../assets/img/rentImage.jpg";
import enjoyImage from "../../assets/img/enjoyImage.jpg";
import "../../assets/css/Carousel.css";

function UncontrolledExample() {
  return (
    <Carousel className="carousel" pause={false} indicators={false}>
      <Carousel.Item interval={3000}>
        <img
          src={browseImage}
          className="carousel-img"
          alt="Browse Image"
        ></img>
        <Carousel.Caption>
          <h3 className="carousel-text">Browse</h3>
          <p className="carousel-text">Discover, Explore, Find Your Flick!</p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={3000}>
        <img src={rentImage} className="carousel-img" alt="Rent Image"></img>
        <Carousel.Caption>
          <h3 className="carousel-text">Rent</h3>
          <p className="carousel-text">Grab the Popcorn, Rent the Fun!</p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={3000}>
        <img src={enjoyImage} className="carousel-img" alt="Enjoy Image"></img>
        <Carousel.Caption>
          <h3 className="carousel-text">Enjoy</h3>
          <p className="carousel-text">Sit Back, Relax, and Enjoy the Show!</p>
        </Carousel.Caption>
      </Carousel.Item>
    </Carousel>
  );
}

export default UncontrolledExample;

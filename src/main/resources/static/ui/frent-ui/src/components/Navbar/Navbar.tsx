import { useState } from "react";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import Cart from "../Cart";
import Wishlist from "../Wishlist";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store";
import { logout } from "../../store/authSlice";
import { useEffect } from "react";
import { decodeJwtToken } from "../../utils/decoder";

const NavScrollExample = () => {
  const { userToken } = useSelector((state: RootState) => state.auth);
  const dispatch = useDispatch();
  const [showCart, setShowCart] = useState(false);
  const [showWishlist, setShowWishlist] = useState(false);
  const [decodedToken, setDecodedToken] = useState<any>(null);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [page, setPage] = useState(1);
  const [size] = useState(5);
  const navigate = useNavigate();

  const handleSearch = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    navigate(`/search/${searchKeyword}/${page}/${size}`);
    setSearchKeyword("");
    setPage(1);
  };

  useEffect(() => {
    if (userToken) {
      const decodedToken = decodeJwtToken(userToken);
      setDecodedToken(decodedToken);
    }
  }, [userToken]);

  const handleCartClick = () => {
    setShowCart(true);
  };

  const handleCloseCart = () => {
    setShowCart(false);
  };

  const handleWishlistClick = () => {
    setShowWishlist(true);
  };

  const handleCloseWishlist = () => {
    setShowWishlist(false);
  };

  return (
    <>
      <Navbar
        expand="lg"
        className="bg-body-tertiary"
        bg="primary"
        data-bs-theme="dark"
        sticky="top"
      >
        <Container fluid>
          <Navbar.Brand as={Link} to="/home">
            Frent
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="navbarScroll" />
          <Navbar.Collapse id="navbarScroll">
            <Nav
              className="me-auto my-2 my-lg-0"
              style={{ maxHeight: "100px" }}
              navbarScroll
            >
              <Nav.Link as={Link} to="/movies">
                Movies
              </Nav.Link>
              <Nav.Link as={Link} to="/rentals">
                Rentals
              </Nav.Link>
              {!userToken ? (
                <Nav.Link as={Link} to={"/login"}>
                  Cart
                </Nav.Link>
              ) : (
                <Nav.Link onClick={handleCartClick}>Cart</Nav.Link>
              )}
              {!userToken ? (
                <Nav.Link as={Link} to={"/login"}>
                  Wishlist
                </Nav.Link>
              ) : (
                <Nav.Link onClick={handleWishlistClick}>Wishlist</Nav.Link>
              )}
              <NavDropdown
                title={userToken ? decodedToken?.sub : "Account"}
                id="navbarScrollingDropdown"
              >
                {!userToken ? (
                  <>
                    <NavDropdown.Item as={Link} to="/register">
                      Register
                    </NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/login">
                      Login
                    </NavDropdown.Item>
                  </>
                ) : (
                  <>
                    {decodedToken?.iss === "admin" && (
                      <NavDropdown.Item as={Link} to="/dashboard">
                        Dashboard
                      </NavDropdown.Item>
                    )}
                    <NavDropdown.Item onClick={() => dispatch(logout())} as={Link} to={"/login"}>
                      Logout
                    </NavDropdown.Item>
                  </>
                )}
              </NavDropdown>
            </Nav>
            <Form className="d-flex" onSubmit={handleSearch}>
              <Form.Control
                type="search"
                placeholder="Search"
                className="me-2"
                aria-label="Search"
                value={searchKeyword}
                onChange={(e) => setSearchKeyword(e.target.value)}
              />
              <Button variant="outline-primary" type="submit">
                Search
              </Button>
            </Form>
          </Navbar.Collapse>
        </Container>
      </Navbar>
      <Cart show={showCart} handleClose={handleCloseCart} />
      <Wishlist show={showWishlist} handleClose={handleCloseWishlist} />
    </>
  );
};

export default NavScrollExample;

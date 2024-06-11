import Spinner from "react-bootstrap/Spinner";

function BasicExample() {
  return (
    <div
      className="d-flex justify-content-center align-items-center"
      style={{ height: "100vh" }}
    >
      <Spinner
        animation="grow"
        role="status"
        variant="primary"
        size="sm"
        style={{ height: "10rem", width: "10rem" }}
      >
        <span className="visually-hidden">Loading...</span>
      </Spinner>
    </div>
  );
}

export default BasicExample;

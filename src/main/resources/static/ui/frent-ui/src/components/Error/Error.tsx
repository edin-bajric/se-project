import { Link } from "react-router-dom";

function BasicExample() {
  return (
    <div className="d-flex align-items-center justify-content-center vh-100">
      <div className="text-center">
        <h1 className="display-1 fw-bold">Error</h1>
        <p className="fs-3">
          {" "}
          <span className="text-danger">Oops!</span> Something went wrong.
        </p>
        <p className="lead">Please try again, or</p>
        <Link to="/home" className="btn btn-primary">
          Go Home
        </Link>
      </div>
    </div>
  );
}

export default BasicExample;

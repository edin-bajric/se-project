import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import "../../assets/css/SingInAndRegister.css";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../store";
import { registerUser } from "../../store/authSlice";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import LoadingButton from "../LoadingButton";

export type RegisterFormData = {
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  password: string;
};

const schema = yup
  .object({
    firstName: yup.string().required("First name is required."),
    lastName: yup.string().required("Last name is required."),
    email: yup.string().email("Invalid email.").required("Email is required."),
    username: yup
      .string()
      .min(4, "Username must be at least 4 characters.")
      .max(16, "Username must be at most 16 characters.")
      .required(),
    password: yup
      .string()
      .min(8, "Password must be at least 8 characters.")
      .required(),
  })
  .required();

function BasicExample() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<RegisterFormData>({
    resolver: yupResolver(schema),
  });

  const { loading, userToken, error, success } = useSelector(
    (state: RootState) => state.auth
  );

  const dispatch = useDispatch<AppDispatch>();

  const navigate = useNavigate();

  useEffect(() => {
    if (success) navigate("/login");
    if (userToken) navigate("/home");
  }, [navigate, userToken, success]);

  const onSubmit = (data: RegisterFormData) => {
    dispatch(registerUser(data));
  };

  return (
    <div className="full-page-container">
      <div className="centered-form-container">
        <h1 className="title">Register</h1>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <div className="mb-3 d-flex">
            <Form.Control
              type="text"
              placeholder="First name"
              className="me-2"
              {...register("firstName")}
            />
            {errors.firstName && (
              <p className="text-danger">{errors.firstName.message}</p>
            )}
            <Form.Control
              type="text"
              placeholder="Last name"
              {...register("lastName")}
            />
            {errors.lastName && (
              <p className="text-danger">{errors.lastName.message}</p>
            )}
          </div>
          <Form.Group className="mb-3">
            <Form.Control
              type="text"
              placeholder="Username"
              {...register("username")}
            />
            {errors.username && (
              <p className="text-danger">{errors.username.message}</p>
            )}
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Control
              type="email"
              placeholder="Email"
              {...register("email")}
            />
            {errors.email && (
              <p className="text-danger">{errors.email.message}</p>
            )}
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Control
              type="password"
              placeholder="Password"
              {...register("password")}
            />
            {errors.password && (
              <p className="text-danger">{errors.password.message}</p>
            )}
          </Form.Group>
          <Button variant="primary" type="submit" disabled={loading}>
            {loading ? <LoadingButton /> : "Register"}
          </Button>
        </Form>
        {error && <span className="text-danger">{error}</span>}
      </div>
    </div>
  );
}

export default BasicExample;

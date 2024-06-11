import { useState } from "react";
import { Tabs, Tab, Container } from "react-bootstrap";
import MovieDashboard from "../MovieDashboard";
import UserDashboard from "../UserDashboard";

const Dashboard = () => {
  const [key, setKey] = useState("movies");

  return (
    <Container style={{ paddingTop: "16px" }}>
      <Tabs
        id="dashboard-tabs"
        activeKey={key}
        onSelect={(k) => setKey(k || "movies")}
        className="mb-3"
      >
        <Tab eventKey="movies" title="Movies">
          <MovieDashboard />
        </Tab>
        <Tab eventKey="users" title="Users">
          <UserDashboard />
        </Tab>
      </Tabs>
    </Container>
  );
};

export default Dashboard;

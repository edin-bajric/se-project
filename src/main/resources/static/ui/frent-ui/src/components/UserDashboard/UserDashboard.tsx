import { useState } from "react";
import { Button, Modal, Badge } from "react-bootstrap";
import DataTable, { TableColumn } from "react-data-table-component";
import useUsers from "../../hooks/useUsers";
import useDeleteUser from "../../hooks/useDeleteUser";
import useSuspendUser from "../../hooks/useSuspendUser";
import useUnsuspendUser from "../../hooks/useUnsuspendUser";
import UserTotalSpent from "../UserTotalSpent";
import RentalHistoryModal from "../RentalHistoryModal";
import { User } from "../../utils/types";
import useSendDueDateWarnings from "../../hooks/useSendDueDateWarnings";

const UserDashboard = () => {
  const { data: users = [], isLoading } = useUsers();
  const { mutate: deleteUser } = useDeleteUser();
  const [showConfirmation, setShowConfirmation] = useState(false);
  const [userIdToDelete, setUserIdToDelete] = useState("");
  const { mutate: suspendUser } = useSuspendUser();
  const { mutate: unsuspendUser } = useUnsuspendUser();
  const [showRentalHistory, setShowRentalHistory] = useState(false);
  const [selectedUserId, setSelectedUserId] = useState<string | null>(null);
  const sendDueDateWarningsMutation = useSendDueDateWarnings();

  const handleSendDueDateWarnings = () => {
    sendDueDateWarningsMutation.mutate();
  };

  const handleDeleteConfirmation = (userId: string) => {
    setUserIdToDelete(userId);
    setShowConfirmation(true);
  };

  const handleCloseConfirmation = () => {
    setShowConfirmation(false);
    setUserIdToDelete("");
  };

  const handleDeleteUser = async () => {
    try {
      await deleteUser(userIdToDelete);
      handleCloseConfirmation();
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };

  const handleToggleSuspendUser = async (user: User) => {
    try {
      if (user.isSuspended) {
        await unsuspendUser(user);
      } else {
        await suspendUser(user);
      }
    } catch (error) {
      console.error("Error toggling user suspension:", error);
    }
  };

  const handleShowRentalHistory = (userId: string) => {
    setSelectedUserId(userId);
    setShowRentalHistory(true);
  };

  const handleCloseRentalHistory = () => {
    setShowRentalHistory(false);
    setSelectedUserId(null);
  };

  const deleteButtonColumn: TableColumn<User> = {
    name: "Delete",
    button: true,
    cell: (row: User) => (
      <Button variant="danger" onClick={() => handleDeleteConfirmation(row.id)}>
        Delete
      </Button>
    ),
  };

  const suspendToggleColumn: TableColumn<User> = {
    name: "Suspend/Unsuspend",
    button: true,
    width: "110px",
    cell: (row: User) => (
      <Button
        variant={row.isSuspended ? "success" : "warning"}
        onClick={() => handleToggleSuspendUser(row)}
      >
        {row.isSuspended ? "Unsuspend" : "Suspend"}
      </Button>
    ),
  };

  const totalSpentColumn: TableColumn<User> = {
    name: "Total Spent",
    cell: (row: User) => <UserTotalSpent userId={row.id} />,
    sortable: true,
  };

  const rentalHistoryButtonColumn: TableColumn<User> = {
    name: "Rental History",
    button: true,
    width: "130px",
    cell: (row: User) => (
      <Button variant="info" onClick={() => handleShowRentalHistory(row.id)}>
        View Rentals
      </Button>
    ),
  };

  const columns: TableColumn<User>[] = [
    deleteButtonColumn,
    suspendToggleColumn,
    rentalHistoryButtonColumn,
    {
      name: "Email",
      selector: (row: User) => row.email,
    },
    {
      name: "Username",
      selector: (row: User) => row.username,
      sortable: true,
    },
    {
      name: "User Type",
      selector: (row: User) => row.userType,
      sortable: true,
    },
    {
      name: "Name",
      selector: (row: User) => row.name,
    },
    {
      name: "Creation Date",
      selector: (row: User) => row.creationDate.toString(),
      sortable: true,
    },
    {
      name: "Status",
      cell: (row: User) => (
        <Badge bg={row.isSuspended ? "danger" : "success"}>
          {row.isSuspended ? "Suspended" : "Active"}
        </Badge>
      ),
      sortable: true,
    },
    totalSpentColumn,
  ];

  return (
    <>
      <Button
        onClick={handleSendDueDateWarnings}
        style={{ margin: "16px" }}
        variant="primary"
      >
        Send Warnings
      </Button>
      <DataTable
        columns={columns}
        data={users}
        progressPending={isLoading}
        progressComponent={<h2>Loading...</h2>}
        noDataComponent={<h2>No users found</h2>}
        pagination
        responsive
        striped
        highlightOnHover
      />
      <Modal show={showConfirmation} onHide={handleCloseConfirmation}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Deletion</Modal.Title>
        </Modal.Header>
        <Modal.Body>Are you sure you want to delete this user?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseConfirmation}>
            Cancel
          </Button>
          <Button variant="danger" onClick={handleDeleteUser}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>
      {selectedUserId && (
        <RentalHistoryModal
          show={showRentalHistory}
          onHide={handleCloseRentalHistory}
          userId={selectedUserId}
        />
      )}
    </>
  );
};

export default UserDashboard;

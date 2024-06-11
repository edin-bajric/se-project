import React from 'react';
import { Modal, Button, Badge } from 'react-bootstrap';
import DataTable, { TableColumn } from 'react-data-table-component';
import useGetAllRentalsByUserId from '../../hooks/useGetAllRentalsByUserId';
import { RentalMovie } from '../../utils/types';

interface RentalHistoryModalProps {
  show: boolean;
  onHide: () => void;
  userId: string;
}

const RentalHistoryModal: React.FC<RentalHistoryModalProps> = ({ show, onHide, userId }) => {
  const { data: rentals = [], isLoading } = useGetAllRentalsByUserId(userId);

  const columns: TableColumn<RentalMovie>[] = [
    { name: 'Title', selector: (row) => row.title, wrap: true, sortable: true },
    { name: 'Rental Date', selector: (row) => new Date(row.rentalDate).toLocaleDateString(), sortable: true },
    { name: 'Due Date', selector: (row) => new Date(row.dueDate).toLocaleDateString(), sortable: true },
    { name: 'Return Date', selector: (row) => row.returnDate ? new Date(row.returnDate).toLocaleDateString() : 'Not Returned', sortable: true },
    { name: 'Rental Price', selector: (row) => `${row.rentalPrice.toFixed(2)}KM`, sortable: true },
    { name: 'Returned', cell: (row: RentalMovie) => (
      <Badge bg={row.returned ? "success" : "danger"}>
        {row.returned ? "Returned" : "Rented"}
      </Badge>
    ), sortable: true },
  ];

  return (
    <Modal show={show} onHide={onHide} size='lg'>
      <Modal.Header closeButton>
        <Modal.Title>Rental History</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {isLoading ? (
          <h2>Loading...</h2>
        ) : (
          <DataTable
            columns={columns}
            data={rentals}
            noDataComponent={<h2>No rentals found</h2>}
            pagination
            responsive
            striped
            highlightOnHover
          />
        )}
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onHide}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default RentalHistoryModal;

import React from 'react';
import useRentalsTotalByUser from '../../hooks/useRentalsTotalByUser';

interface UserTotalSpentProps {
  userId: string;
}

const UserTotalSpent: React.FC<UserTotalSpentProps> = ({ userId }) => {
  const { data: totalSpent, isLoading } = useRentalsTotalByUser(userId);

  if (isLoading) return <span>Loading...</span>;
  return <span>{totalSpent}KM</span>;
};

export default UserTotalSpent;

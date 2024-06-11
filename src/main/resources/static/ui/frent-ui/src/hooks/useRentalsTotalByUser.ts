import { useQuery } from 'react-query';
import { RentalService } from '../services';

const useRentalsTotalByUser = (id: string) => {
  return useQuery(['rentalsTotal', id], async () => {
    const rentalsTotal = await RentalService.getTotalSpentById(id);
    return rentalsTotal;
  });
};

export default useRentalsTotalByUser;

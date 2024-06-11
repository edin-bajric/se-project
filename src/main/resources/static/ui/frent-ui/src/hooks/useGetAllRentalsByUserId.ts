import { useQuery } from 'react-query';
import { RentalService } from '../services';

const useGetAllRentalsByUserId = (id: string) => {
  return useQuery(['rentalsMovies', id], async () => {
    const rentalsWithMovies = await RentalService.getRentalsForUserById(id);
    return rentalsWithMovies.reverse();
  });
};

export default useGetAllRentalsByUserId;
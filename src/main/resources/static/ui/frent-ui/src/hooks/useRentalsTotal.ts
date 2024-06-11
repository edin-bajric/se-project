import { useQuery } from "react-query";
import { RentalService } from "../services";

const useRentalsTotal = () => {
    return useQuery("rentalsTotal", async () => {
        const rentalsTotal = await RentalService.getTotalSpent();

        return rentalsTotal;
    });
};

export default useRentalsTotal;
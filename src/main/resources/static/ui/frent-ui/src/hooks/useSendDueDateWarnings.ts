import { useMutation, useQueryClient } from "react-query";
import { RentalService } from "../services";

const useSendDueDateWarnings = () => {
  const queryClient = useQueryClient();

  return useMutation(() => RentalService.sendDueDateWarnings(), {
    onSuccess: () => {
      queryClient.invalidateQueries("rentals");
    },
  });
};

export default useSendDueDateWarnings;

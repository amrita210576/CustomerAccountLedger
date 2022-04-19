package Service;

public interface BorrowerService {
    public void getBalance(String[] balanceCommand);

    /**
     * save the borrowing information.
     */
    public void saveLoan(String[] loanCommand);

    /**
     * update the payment information.
     */
    public void updatePayment(String[] paymentCommand);
}

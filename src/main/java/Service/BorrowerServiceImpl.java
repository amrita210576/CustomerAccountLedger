package Service;

import Model.Borrower;
import Model.BorrowingAmountDetails;
import Model.LumpSumPaymentDetails;

import java.util.*;
import java.util.stream.Collectors;

public class BorrowerServiceImpl implements BorrowerService {

    Map<String, Borrower> borrowersMap = new HashMap<>();

    @Override
    public void getBalance(String[] balanceCommand) {
        String borrowersName = balanceCommand[2];
        String bankName = balanceCommand[1];
        Integer emiNo = convertToInteger(balanceCommand[3]);
        if (emiNo == null) {
            return;
        }

        if (borrowersMap.containsKey(borrowersName)) {
            BorrowingAmountDetails borrowingAmountDetails = borrowersMap.get(borrowersName)
                .getDetails()
                .get(bankName);
            if (borrowingAmountDetails == null) {
                System.err.println("borrowing Amount is null");
                return;
            }

            double totalAmountPaid = emiNo * borrowingAmountDetails.getEmi();

            //add if any lumpSum was paid till this emi
            String totalAmountWithRemainingEmis =
                calculateLumpSumAndRemainingMonths(borrowingAmountDetails, emiNo, totalAmountPaid);

            printOutput(bankName, borrowersName, totalAmountWithRemainingEmis);
        } else {
            System.err.println(borrowersName + " borrower doesn't exist " + " for bank " + bankName);
        }
    }

    private void printOutput(String bankName, String borrowersName, String totalAmountWithRemainingEmis) {
        System.out.println(bankName + " " + borrowersName + " " + totalAmountWithRemainingEmis);
    }

    private String calculateLumpSumAndRemainingMonths(BorrowingAmountDetails borrowingAmountDetails,
                                                      Integer emiNo, double totalAmountPaid) {
        List<LumpSumPaymentDetails> lumpSumPaymentDetails
            = borrowingAmountDetails.getLumpSumPaymentDetailsList();

        if (lumpSumPaymentDetails == null || lumpSumPaymentDetails.isEmpty()) {
            return  new String((int) totalAmountPaid + " "
                + (borrowingAmountDetails.getTotalEmis() - emiNo));
        } else {
            return adjustLumpSums(borrowingAmountDetails, lumpSumPaymentDetails,
                    emiNo, totalAmountPaid);
        }
    }

    private String adjustLumpSums(BorrowingAmountDetails borrowingAmountDetails,
                                  List<LumpSumPaymentDetails> lumpSumPaymentDetailsList,
                                  Integer emiNo, double totalAmountPaid) {

        List<LumpSumPaymentDetails> listOfLumpSumsPaids = lumpSumPaymentDetailsList.stream()
                .filter(l -> l.getEmiNumberLumpSumPaidWith() <= emiNo)
                .collect(Collectors.toList());
        List<Double> listOfLumpSumsPaid;
        if (listOfLumpSumsPaids == null) {
            return new String(totalAmountPaid + " "
                + (borrowingAmountDetails.getTotalEmis() - emiNo));
        } else {
            listOfLumpSumsPaid = listOfLumpSumsPaids.stream().map(LumpSumPaymentDetails::getAmount)
                    .collect(Collectors.toList());
        }

        StringBuilder output = new StringBuilder();
        for (Double lumpSum : listOfLumpSumsPaid) {
            totalAmountPaid = totalAmountPaid + lumpSum;
            if (totalAmountPaid > borrowingAmountDetails.getTotalAmount()) {
                totalAmountPaid = (int) borrowingAmountDetails.getTotalAmount();
            }
        }

        output.append((int) totalAmountPaid);

        double restAmount = (int) Math.ceil(borrowingAmountDetails.getTotalAmount() - totalAmountPaid);
        int restMonthForEmi;
        if (restAmount == 0 || restAmount < 0) {
            restMonthForEmi = 0;
        } else {
            restMonthForEmi = (int) Math.ceil(restAmount / borrowingAmountDetails.getEmi());
        }
        output.append(" " + restMonthForEmi);
        return String.valueOf(output);
    }

    @Override
    public void saveLoan(String[] loanCommandParameters) {
        Double principalAmount = convertToDouble(loanCommandParameters[3]);
        Integer loanNumberOfYears = convertToInteger(loanCommandParameters[4]);
        Double rate = convertToDouble(loanCommandParameters[5]);
        if (principalAmount == null || loanCommandParameters == null || rate == null) {
            return;
        }
        BorrowingAmountDetails borrowingAmountDetails = new BorrowingAmountDetails(
            loanCommandParameters[1], principalAmount, loanNumberOfYears, rate);

        String borrowersName = loanCommandParameters[2];

        if (borrowersMap.containsKey(borrowersName)) {
            borrowersMap.get(borrowersName).getDetails()
                    .put(borrowingAmountDetails.getBankName(), borrowingAmountDetails);
        } else {
            Borrower borrower = new Borrower(loanCommandParameters[2],
                    new HashMap<String, BorrowingAmountDetails>() {
                        {
                            put(borrowingAmountDetails.getBankName(), borrowingAmountDetails);
                        }
                    });
            borrowersMap.put(borrowersName, borrower);
        }
    }

    @Override
    public void updatePayment(String[] paymentCommand) {
        Double amount = convertToDouble(paymentCommand[3]);
        Integer emiNumberLumpSumPaidInMonth = convertToInteger(paymentCommand[4]);

        if (amount == null || emiNumberLumpSumPaidInMonth == null) {
            return;
        }
        LumpSumPaymentDetails lumpSumPaymentDetails = new LumpSumPaymentDetails(
                amount, emiNumberLumpSumPaidInMonth);

        String borrowersName = paymentCommand[2];
        String bankName = paymentCommand[1];

        if (borrowersMap.containsKey(borrowersName)) {
            List<LumpSumPaymentDetails> exisitingLumpSumList = borrowersMap.get(borrowersName)
                .getDetails().get(bankName)
                .getLumpSumPaymentDetailsList();
            if (exisitingLumpSumList == null) {
                exisitingLumpSumList = new ArrayList() {
                    {
                        add(lumpSumPaymentDetails);
                    }
                };
            } else {
                exisitingLumpSumList.add(lumpSumPaymentDetails);
            }
            borrowersMap.get(borrowersName).getDetails().get(bankName)
                .setLumpSumPaymentDetailsList(exisitingLumpSumList);
        } else {
            System.err.println("Invalid Payment for borrower. " + borrowersName +
                " has not borrowed any loan from " + bankName);
        }
    }

    /**
     * Helper class to convert the string to Integer
     * @param input string
     * @return Integer
     */
    public Integer convertToInteger(String input) {
        Integer output = null;
        try {
            output = Integer.valueOf(input);
            if (output < 0) {
                System.err.println("Invalid negative input : " + input);
                output = null;
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: " + input);
        }
        return output;
    }

    /**
     * Helper class to convert the String to Double
     * @param input string
     * @return Double
     */
    public Double convertToDouble(String input) {
        Double output = null;
        try {
            output = Double.valueOf(input);
            if (output < 0 ) {
                System.err.println("Invalid negative input : " + input);
                output = null;
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: " + input);
        }
        return output;
    }
}

package Model;

import java.util.List;
import java.util.Map;

public class Borrower {
    String borrowerName;
    Map<String,BorrowingAmountDetails> details;

    public Borrower(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public Borrower(String borrowerName, Map<String, BorrowingAmountDetails> details) {
        this.borrowerName = borrowerName;
        this.details = details;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public Map<String, BorrowingAmountDetails> getDetails() {
        return details;
    }

    public void setDetails(Map<String, BorrowingAmountDetails> details) {
        this.details = details;
    }
}

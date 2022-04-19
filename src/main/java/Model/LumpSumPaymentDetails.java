package Model;

public class LumpSumPaymentDetails {
    Double amount;
    Integer emiNumberLumpSumPaidWith;

    public LumpSumPaymentDetails(Double amount, Integer emiNumberLumpSumPaidWith) {
        this.amount = amount;
        this.emiNumberLumpSumPaidWith = emiNumberLumpSumPaidWith;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getEmiNumberLumpSumPaidWith() {
        return emiNumberLumpSumPaidWith;
    }

    public void setEmiNumberLumpSumPaidWith(Integer emiNumberLumpSumPaidWith) {
        this.emiNumberLumpSumPaidWith = emiNumberLumpSumPaidWith;
    }
}

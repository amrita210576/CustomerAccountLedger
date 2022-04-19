package Model;

import java.util.List;

public class BorrowingAmountDetails {
    String bankName;
    Double principalAmount;
    double rate;
    Integer loanNumberOfYears;
    double totalAmount;
    Integer emi;
    Integer totalEmis;

    List<LumpSumPaymentDetails> lumpSumPaymentDetailsList;

    public BorrowingAmountDetails(String bankName, Double principalAmount,
                                  Integer rate, Integer loanNumberOfYears,
                                  Double totalAmount,
                                  List<LumpSumPaymentDetails> lumpSumPaymentDetailsList) {
        this.bankName = bankName;
        this.principalAmount = principalAmount;
        this.rate = rate;
        this.loanNumberOfYears = loanNumberOfYears;
        this.totalAmount = totalAmount;
        this.lumpSumPaymentDetailsList = lumpSumPaymentDetailsList;
    }

    public BorrowingAmountDetails(String bankName, Double principalAmount,
                                  Integer loanNumberOfYears, double rate) {
        this.bankName = bankName;
        this.principalAmount = principalAmount;
        this.loanNumberOfYears = loanNumberOfYears;
        this.rate = rate;
        this.totalAmount = principalAmount +
            Math.ceil(principalAmount * loanNumberOfYears  * (rate/100));
        this.totalEmis = loanNumberOfYears * 12;
        this.emi = (int) Math.ceil(totalAmount / totalEmis);
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(Double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Integer getLoanNumberOfYears() {
        return loanNumberOfYears;
    }

    public void setLoanNumberOfYears(Integer loanNumberOfYears) {
        this.loanNumberOfYears = loanNumberOfYears;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<LumpSumPaymentDetails> getLumpSumPaymentDetailsList() {
        return lumpSumPaymentDetailsList;
    }

    public void setLumpSumPaymentDetailsList(List<LumpSumPaymentDetails> lumpSumPaymentDetailsList) {
        this.lumpSumPaymentDetailsList = lumpSumPaymentDetailsList;
    }

    public Integer getEmi() {
        return emi;
    }

    public void setEmi(Integer emi) {
        this.emi = emi;
    }

    public Integer getTotalEmis() {
        return totalEmis;
    }

    public void setTotalEmis(Integer totalEmis) {
        this.totalEmis = totalEmis;
    }
}

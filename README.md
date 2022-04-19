This project a marketplace for banks to lend money to borrowers and receive payments for the loans.
The interest for the loan is calculated by I = P*N*R
where P is the principal amount, N is the number of years and R is the rate of interest.
The total amount to repay will be A = P + I

It takes 3 types of Input commands:
LOAN
The LOAN command receives a Bank name, Borrower name, Principal Amount, No of Years of Loan period and the Rate of Interest along with it.
Format - LOAN (String)BANK_NAME (String)BORROWER_NAME
(double)PRINCIPAL (Integer)NO_OF_YEARS (double)RATE_OF_INTEREST

PAYMENT
The PAYMENT command receives a Bank name, Borrower name, Lump sum amount and EMI number along with it. The EMI number indicates that the lump sum payment is done after that EMI.
Format - PAYMENT (String)BANK_NAME (String)BORROWER_NAME (Integer)LUMP_SUM_AMOUNT (Integer)EMI_NO

BALANCE
The BALANCE command receives a Bank name, Borrower name and a EMI number along with it. It prints the total amount paid by the borrower, including all the Lump Sum amounts paid including that EMI number, and the no of EMIs remaining.
Input format - BALANCE (String)BANK_NAME (String)BORROWER_NAME (Integer)EMI_NO

If the integer parameters are provided as negative or as strings, user will get error.


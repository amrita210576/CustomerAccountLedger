package handler;

import Service.BorrowerService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TextInputHandlerImpl implements InputHandler {
    BorrowerService borrowerService;

    public TextInputHandlerImpl(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @Override
    public void readInput(String path) throws IOException {
        try {
            BufferedReader input = new BufferedReader(new FileReader(path));
            String line;
            while ((line = input.readLine()) != null) {
                //read line nd find commandType
                String commandType = line.split(" ")[0];
                switch (commandType) {
                    case "LOAN":
                        //save loan
                        //System.out.println("LOAN command " + line);
                        String[] loanCommandParameters = line.split(" ");
                        if (loanCommandParameters.length != 6) {
                            System.err.println("invalid loan command : " +  line);
                        } else {
                            borrowerService.saveLoan(loanCommandParameters);
                        }
                        break;
                    case "PAYMENT":
                        //update payment
                        //System.out.println("PAYMENT command " + line);
                        String[] paymentCommandParameters = line.split(" ");
                        if (paymentCommandParameters.length != 5) {
                            System.err.println("Invalid payment command : " + line);
                        } else {
                            borrowerService.updatePayment(paymentCommandParameters);
                        }
                        break;
                    case "BALANCE":
                        //find out balance and print the output
                        //System.out.println("BALANCE command " + line);
                        String[] balanceCommandParameters = line.split(" ");
                        if (balanceCommandParameters.length != 4) {
                            System.err.println("Invalid balance command : " + line);
                        } else {
                            borrowerService.getBalance(balanceCommandParameters);
                        }
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("No such file exists : " + path + " " + e.getMessage());
            throw e;
        }
    }
}

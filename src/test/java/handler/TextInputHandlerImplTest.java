package handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import Service.BorrowerService;
import Service.BorrowerServiceImpl;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TextInputHandlerImplTest {

    InputHandler inputHandler;
    BorrowerService service;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        service = new BorrowerServiceImpl();
        inputHandler = new TextInputHandlerImpl(service);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(err));

    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void readValidFileWithOneBorrowers() throws IOException {
        String path = "src/test/resources/sampleWithOneUser.txt";
        inputHandler.readInput(path);
        assertEquals(new String("IDIDI Dale 1326 9"), outContent.toString().trim());
    }

    @Test
    public void readInvalidValid() throws IOException {
        String path = "src/test/resources/noSample.txt";
       assertThrows(IOException.class, () -> inputHandler.readInput(path));
    }

    @Test
    public void readInvalidValidCommand() throws IOException {
        String path = "src/test/resources/sampleInvalidCommands.txt";
        inputHandler.readInput(path);

        String expectOutput = new String("invalid loan command : LOAN IDIDI Dale 5000 1 6 1\n"
            + "Invalid payment command : PAYMENT IDIDI Dale 1000\n"
            + "Invalid input: exp");
        assertEquals(expectOutput, err.toString().trim());
    }

    @Test
    public void readBalCommandWithNoLoan() throws IOException {
        String path = "src/test/resources/sampleBalWithNoBank.txt";
        inputHandler.readInput(path);

        String expectOutput = new String("Dale borrower doesn't exist  for bank IDIDI");
        assertEquals(expectOutput, err.toString().trim());
    }

    @Test
    public void readPaymentCommandWithNoLoan() throws IOException {
        String path = "src/test/resources/samplePaymentWithNoBank.txt";
        inputHandler.readInput(path);

        String expectOutput = new String("Invalid Payment for borrower. Dale has not borrowed any"
            + " loan from IDIDI\n"
            + "Dale borrower doesn't exist  for bank IDIDI");
        assertEquals(expectOutput, err.toString().trim());
    }

    @Test
    public void commandWithInvalidArg() throws IOException {
        String path = "src/test/resources/sampleWithOneUserWithNegativeMonths.txt";
        inputHandler.readInput(path);

        String expectOutput = new String("Invalid negative input : -5000\n"
            + "Invalid negative input : -1\n"
            + "Invalid negative input : -6\n"
            + "Invalid negative input : -3");
        assertEquals(expectOutput, err.toString().trim());
    }

    @Test
    public void readFileWithMultipleBorrowers() throws IOException {
        String path = "src/test/resources/sampleWithMulipleUsers.txt";
        inputHandler.readInput(path);
        String expectOutput = new String("IDIDI Dale 1326 9\n"
            + "IDIDI Dale 3652 4\n"
            + "IDIDI Dale 5300 0\n"
            + "UON Dale 15856 3\n"
            + "UON Shelly 15856 3\n"
            + "UON Shelly 16594 2\n"
            + "MBI Harry 9044 10");
        assertEquals(expectOutput, outContent.toString().trim());
    }

    @Test
    public void testWhenUserHasNoMoreEmis() throws IOException {
        String path = "src/test/resources/sampleSingleUserWithMultipleCommands.txt";
        inputHandler.readInput(path);
        String expectOutput = new String("IDIDI Dale 1326 9\n"
            + "IDIDI Dale 5300 0");
        assertEquals(expectOutput, outContent.toString().trim());
    }
}
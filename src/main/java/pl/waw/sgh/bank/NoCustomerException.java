package pl.waw.sgh.bank;

public class NoCustomerException extends Exception {
    NoCustomerException(int custId) {
        super("The customer with " + custId + " does not exist.");
    }
}

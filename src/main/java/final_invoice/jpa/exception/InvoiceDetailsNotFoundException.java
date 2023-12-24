package final_invoice.jpa.exception;

public class InvoiceDetailsNotFoundException extends Exception{
    public InvoiceDetailsNotFoundException(String msg) {
        super(msg);
    }
}

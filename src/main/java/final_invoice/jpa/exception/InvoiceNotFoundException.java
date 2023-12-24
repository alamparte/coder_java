package final_invoice.jpa.exception;

public class InvoiceNotFoundException extends Exception{
    public InvoiceNotFoundException(String msg) {
        super(msg);
    }
}

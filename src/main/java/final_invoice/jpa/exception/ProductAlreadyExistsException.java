package final_invoice.jpa.exception;

public class ProductAlreadyExistsException extends  Exception{
    public ProductAlreadyExistsException(String msg) {
        super(msg);
    }
}

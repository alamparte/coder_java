package final_invoice.jpa.exception;

public class ClientAlreadyRegisteredException extends Exception{

    public ClientAlreadyRegisteredException(String msg) {
        super(msg);
    }
}

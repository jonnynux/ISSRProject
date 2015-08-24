package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "ExpirationDateNotValidException")
public class ExpirationDateNotValidException extends Exception {

    private FaultBean faultBean;

    public ExpirationDateNotValidException() {
        super("Data di scadenza non valida");
    }

    public ExpirationDateNotValidException(String message) {
        super(message);
    }

    public ExpirationDateNotValidException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public ExpirationDateNotValidException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

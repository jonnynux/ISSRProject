package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "QuantityNotValidException")
public class QuantityNotValidException extends Exception {

    private FaultBean faultBean;

    public QuantityNotValidException() {
        super("Quantità non valida");
    }

    public QuantityNotValidException(String message) {
        super(message);
    }

    public QuantityNotValidException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public QuantityNotValidException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

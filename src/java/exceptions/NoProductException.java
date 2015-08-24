package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "NoProductException")
public class NoProductException extends Exception {

    private FaultBean faultBean;

    public NoProductException() {
        super("Non sono stati trovati prodotti che soddisfano la richiesta");
    }

    public NoProductException(String message) {
        super(message);
    }

    public NoProductException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public NoProductException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

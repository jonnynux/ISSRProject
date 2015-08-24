package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "RequiredFieldEmptyException")
public class RequiredFieldEmptyException extends Exception {

    private FaultBean faultBean;

    public RequiredFieldEmptyException() {
        super("Campo richiesto mancante");
    }

    public RequiredFieldEmptyException(String message) {
        super(message);
    }

    public RequiredFieldEmptyException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public RequiredFieldEmptyException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

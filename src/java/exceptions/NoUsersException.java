package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "NoUsersException")
public class NoUsersException extends Exception {

    private FaultBean faultBean;

    public NoUsersException() {
        super("Non sono stati trovati utenti");
    }

    public NoUsersException(String message) {
        super(message);
    }

    public NoUsersException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public NoUsersException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

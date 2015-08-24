package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "NoRolesException")
public class NoRolesException extends Exception {

    private FaultBean faultBean;

    public NoRolesException() {
        super("Non sono stati trovati ruoli");
    }

    public NoRolesException(String message) {
        super(message);
    }

    public NoRolesException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public NoRolesException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

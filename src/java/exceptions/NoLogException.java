package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "NoLogException")
public class NoLogException extends Exception {

    private FaultBean faultBean;

    public NoLogException() {
        super("Non sono stati trovati log");
    }

    public NoLogException(String message) {
        super(message);
    }

    public NoLogException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public NoLogException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

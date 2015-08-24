package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "EmailNotValidException")
public class EmailNotValidException extends Exception {

    private FaultBean faultBean;

    public EmailNotValidException() {
        super("Email non valida");
    }

    public EmailNotValidException(String message) {
        super(message);
    }

    public EmailNotValidException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public EmailNotValidException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

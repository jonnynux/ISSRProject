package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "LoginError")
public class LoginErrorException extends Exception {

    private FaultBean faultBean;

    public LoginErrorException() {
        super("Errore Accesso");
    }

    public LoginErrorException(String message) {
        super(message);
    }

    public LoginErrorException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public LoginErrorException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

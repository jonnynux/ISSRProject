package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "LoginNotValidException")
public class LoginNotValidException extends Exception {

    private FaultBean faultBean;

    public LoginNotValidException() {
        super("Username non valido: ammesse solo numeri (0-9) o caratteri standard (a-z)");
    }

    public LoginNotValidException(String message) {
        super(message);
    }

    public LoginNotValidException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public LoginNotValidException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "RoleNotValidException")
public class RoleNotValidException extends Exception {

    private FaultBean faultBean;

    public RoleNotValidException() {
        super("Ruolo non permesso o non valido");
    }

    public RoleNotValidException(String message) {
        super(message);
    }

    public RoleNotValidException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public RoleNotValidException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

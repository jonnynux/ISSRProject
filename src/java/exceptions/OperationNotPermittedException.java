package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "OperationNotPermitted")
public class OperationNotPermittedException extends Exception {

    private FaultBean faultBean;

    public OperationNotPermittedException() {
        super("Operazione non permessa dall'utente attuale");
    }

    public OperationNotPermittedException(String message) {
        super(message);
    }

    public OperationNotPermittedException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public OperationNotPermittedException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

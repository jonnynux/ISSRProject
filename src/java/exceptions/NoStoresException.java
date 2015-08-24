package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "NoStoresToView")
public class NoStoresException extends Exception {

    private FaultBean faultBean;

    public NoStoresException() {
        super("Non ci sono punti vendita da visualizzare");
    }

    public NoStoresException(String message) {
        super(message);
    }

    public NoStoresException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public NoStoresException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

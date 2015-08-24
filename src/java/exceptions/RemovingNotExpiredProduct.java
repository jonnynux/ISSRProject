package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "RemovingNotExpiredProduct")
public class RemovingNotExpiredProduct extends Exception {

    private FaultBean faultBean;

    public RemovingNotExpiredProduct() {
        super("Rimozione di prodotto non scaduto");
    }

    public RemovingNotExpiredProduct(String message) {
        super(message);
    }

    public RemovingNotExpiredProduct(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public RemovingNotExpiredProduct(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

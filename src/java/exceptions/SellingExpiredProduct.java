package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "SellingExpiredProduct")
public class SellingExpiredProduct extends Exception {

    private FaultBean faultBean;

    public SellingExpiredProduct() {
        super("Vendita di prodotto scaduto");
    }

    public SellingExpiredProduct(String message) {
        super(message);
    }

    public SellingExpiredProduct(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public SellingExpiredProduct(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

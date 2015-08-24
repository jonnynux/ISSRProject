package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "ProductTypeAlreadyPresent")
public class ProductTypeAlreadyPresentException extends Exception {

    private FaultBean faultBean;

    public ProductTypeAlreadyPresentException() {
        super("Il tipo di prodotto è già presente");
    }

    public ProductTypeAlreadyPresentException(String message) {
        super(message);
    }

    public ProductTypeAlreadyPresentException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public ProductTypeAlreadyPresentException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

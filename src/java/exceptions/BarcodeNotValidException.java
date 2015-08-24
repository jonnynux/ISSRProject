package exceptions;

import javax.xml.ws.WebFault;
import webservices.FaultBean;

/**
 *
 * @author jonny
 */
@WebFault(name = "BarcodeNotValidException")
public class BarcodeNotValidException extends Exception {

    private FaultBean faultBean;

    public BarcodeNotValidException() {
        super("Codice a barre non valido o già presente: ammesse da 8 a 14 cifre numeriche");
    }

    public BarcodeNotValidException(String message) {
        super(message);
    }

    public BarcodeNotValidException(String message, FaultBean faultInfo) {
        super(message);
        this.faultBean = faultInfo;
    }

    public BarcodeNotValidException(String message, FaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultInfo;
    }

    public FaultBean getFaultInfo() {
        return this.faultBean;
    }
}

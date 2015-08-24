package webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Class for sending webservice's exceptions
 *
 * @author jonny
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FaultBean", propOrder = {"message"})
public class FaultBean {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

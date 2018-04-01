package polyevent.communication;

import java.io.Serializable;

/**
 * A messaging class to transport responses
 * into the application
 *
 * This class can only carry {@link Serializable} objects
 * as it needs to be transferred on the network
 *
 */
public class Message implements Serializable {

    private Serializable transmittedObject;
    private int status;
    private String statusText;

    public Message() {
        // default constructor for JPA instantiation
    }

    public void setTransmittedObject(Serializable transmittedObject) {
        this.transmittedObject = transmittedObject;
    }

    public Message withTransmittedObject(Serializable transmittedObject) {
        setTransmittedObject(transmittedObject);
        return this;
    }

    public Serializable getTransmittedObject() {
        return transmittedObject;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Message withStatus(int status) {
        setStatus(status);
        return this;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Message withStatusText(String status) {
        setStatusText(status);
        return this;
    }

    public String getMessage() {
        if (transmittedObject != null)
            return transmittedObject.toString();
        return "";
    }

    public boolean isOk() {
        return status == 200;
    }

    public boolean isNotOk() {
        return !isOk();
    }
}

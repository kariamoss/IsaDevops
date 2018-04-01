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
    private int statusText;

    public Message() {
        // default constructor for JPA instantiation
    }

    public void setTransmittedObject(Serializable transmittedObject) {
        this.transmittedObject = transmittedObject;
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

    public int getStatusText() {
        return statusText;
    }

    public void setStatusText(int statusText) {
        this.statusText = statusText;
    }

    public String getMessage() {
        return transmittedObject.toString();
    }
}

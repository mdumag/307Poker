package mqtt;

/**
 * The base class for all poker events.
 * @author Matthew Dumag
 * @version 1.0
 */
public class BaseEvent {
    public String type;
    public String roomId;

    public BaseEvent(String type, String roomId) {
        this.type = type;
        this.roomId = roomId;
    }
}

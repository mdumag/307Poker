package mqtt;

/**
 * Fired when a room is created.
 * @author Matthew
 * @version 1.0
 */
public class CreateRoomEvent extends BaseEvent {
    public String creatorId;

    public CreateRoomEvent(String creatorId, String roomId) {
        super("CreateRoom", roomId);
        this.creatorId = creatorId;
    }
}

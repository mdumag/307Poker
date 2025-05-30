package mqtt;

/**
 * Fired to reveal all cards in the room. 
 * @author Matthew
 * @version 1.0
 */
public class RevealCardsEvent extends BaseEvent {
    public RevealCardsEvent(String roomId) {
        super("RevealCards", roomId);
    }
}

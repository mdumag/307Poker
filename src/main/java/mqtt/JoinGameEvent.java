package mqtt;

/**
 * Fired when a player joins a game room.
 * @author Matthew Dumag
 * @version 1.0
 */
public class JoinGameEvent extends BaseEvent {
    public String playerId;

    public JoinGameEvent(String playerId, String roomId) {
        super("JoinGame", roomId);
        this.playerId = playerId;
    }
}

package mqtt;

/**
 * Fired when a player submits an estimate. 
 * Contains the estimate value. 
 * @author Matthew
 * @version 1.0
 */
public class EstimateSubmittedEvent extends BaseEvent {
    public String playerId;
    public int estimate;

    public EstimateSubmittedEvent(String playerId, String roomId, int estimate) {
        super("EstimateSubmitted", roomId);
        this.playerId = playerId;
        this.estimate = estimate;
    }
}

package mqtt;

/**
 * Fired to broadcast the estimation results. 
 * Contains a simple results summary. 
 * @author Matthew
 * @version 1.0
 */
public class ResultsBroadcastEvent extends BaseEvent {
    public String results;

    public ResultsBroadcastEvent(String roomId, String results) {
        super("ResultsBroadcast", roomId);
        this.results = results;
    }
}

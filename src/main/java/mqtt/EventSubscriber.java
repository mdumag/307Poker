package mqtt;

import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.*;

/**
 * Subscribes to poker event topics and dispatches received JSON to onEventReceived().
 * Extend this class and implement onEventReceived() to handle events.
 * @author Matthew Dumag
 * @version 1.0
 */
public abstract class EventSubscriber implements MqttCallback {
    protected final Gson gson = new Gson();
    private final MqttClientWrapper mqtt;

    public EventSubscriber(MqttClientWrapper mqtt, String roomId) throws MqttException {
        this.mqtt = mqtt;
        mqtt.setCallback(this);
        mqtt.subscribe("planitpoker/" + roomId + "/events", 2);
    }

    @Override
    public void connectionLost(Throwable cause) {
        // TODO: handle reconnection logic
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Not needed for subscriber
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String json = new String(message.getPayload());
        onEventReceived(json);
    }

    /** Handle each incoming event JSON */
    protected abstract void onEventReceived(String json);
}

package mqtt;

import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Publishes poker events as JSON messages over MQTT.
 * Uses a Gson instance to serialize event objects.
 * @author Matthew Dumag
 * @version 1.0
 */
public class EventPublisher {
    private final MqttClientWrapper mqtt;
    private final Gson gson = new Gson();
    private final int qos = 2;

    public EventPublisher(MqttClientWrapper mqtt) {
        this.mqtt = mqtt;
    }

    public <T> void publishEvent(String roomId, T event) throws MqttException {
        String topic = "planitpoker/" + roomId + "/events";
        String json   = gson.toJson(event);
        mqtt.publish(topic, json.getBytes(), qos);
    }
}

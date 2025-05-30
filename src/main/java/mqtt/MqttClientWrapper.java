package mqtt;

import org.eclipse.paho.client.mqttv3.*;

/**
 * Handles connecting to an MQTT broker, publishing, and subscribing.
 * Provides publish() and subscribe() convenience methods.
 * @author Matthew Dumag
 * @version 1.0
 */
public class MqttClientWrapper {
    private final MqttClient client;

    public MqttClientWrapper(String brokerUrl, String clientId) throws MqttException {
        this.client = new MqttClient(brokerUrl, clientId);
    }

    public void connect() throws MqttException {
        client.connect();
    }

    public void disconnect() throws MqttException {
        client.disconnect();
    }

    public void setCallback(MqttCallback callback) {
        client.setCallback(callback);
    }

    public void subscribe(String topic, int qos) throws MqttException {
        client.subscribe(topic, qos);
    }

    public void publish(String topic, byte[] payload, int qos) throws MqttException {
        MqttMessage msg = new MqttMessage(payload);
        msg.setQos(qos);
        client.publish(topic, msg);
    }
}

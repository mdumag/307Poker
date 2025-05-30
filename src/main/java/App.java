import mqtt.MqttClientWrapper;
import mqtt.EventPublisher;
import mqtt.EventSubscriber;
import mqtt.CreateRoomEvent;
import mqtt.JoinGameEvent;
import mqtt.EstimateSubmittedEvent;
import mqtt.RevealCardsEvent;
import mqtt.ResultsBroadcastEvent;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Simple demo: connects to an MQTT broker, subscribes to "room123",
 * publishes all five poker events in sequence, logs received events,
 * then disconnects. Demonstrates the distributed event flow.
 * @author Matthew Dumag
 * @version 1.0
 */
public class App {
    public static void main(String[] args) {
        try {
            // 1. Connect to broker
            MqttClientWrapper mqtt = new MqttClientWrapper(
                    "tcp://broker.hivemq.com:1883",
                    "solo-" + System.currentTimeMillis()
            );
            mqtt.connect();
            System.out.println("✅ Connected to broker");

            // 2. Subscribe to the room topic
            new EventSubscriber(mqtt, "room123") {
                @Override
                protected void onEventReceived(String json) {
                    System.out.println("<<< Received: " + json);
                }
            };

            // 3. Create publisher
            EventPublisher publisher = new EventPublisher(mqtt);

            // 4. Publish CreateRoomEvent
            System.out.println("✅ Publishing CreateRoomEvent");
            publisher.publishEvent("room123", new CreateRoomEvent("playerA", "room123"));
            Thread.sleep(500);

            // 5. Publish JoinGameEvent
            System.out.println("✅ Publishing JoinGameEvent");
            publisher.publishEvent("room123", new JoinGameEvent("playerA", "room123"));
            Thread.sleep(500);

            // 6. Publish EstimateSubmittedEvent
            System.out.println("✅ Publishing EstimateSubmittedEvent");
            publisher.publishEvent("room123", new EstimateSubmittedEvent("playerA", "room123", 5));
            Thread.sleep(500);

            // 7. Publish RevealCardsEvent
            System.out.println("✅ Publishing RevealCardsEvent");
            publisher.publishEvent("room123", new RevealCardsEvent("room123"));
            Thread.sleep(500);

            // 8. Publish ResultsBroadcastEvent
            System.out.println("✅ Publishing ResultsBroadcastEvent");
            publisher.publishEvent("room123", new ResultsBroadcastEvent("room123", "playerA:5"));
            Thread.sleep(1000);

            // 9. Disconnect
            mqtt.disconnect();
            System.out.println("✅ Disconnected");
        } catch (MqttException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

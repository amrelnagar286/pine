package test.server;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttPublishSample2 {
	
    public static void main(String[] args) throws Exception {

        String topic        = "鄰家的派豆龍";
        String broker       = "tcp://localhost:1991";
        String clientId     = "JavaSample_" + java.util.UUID.randomUUID();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect();
            System.out.println("Connected");
            
            sampleClient.setCallback(new MqttCallback(){

				@Override
				public void connectionLost(Throwable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println( message );
				}
            	
            });
            
            sampleClient.subscribe( topic );
            
            Thread.sleep( 1000*120 );
            
            sampleClient.unsubscribe( topic );
            
            sampleClient.disconnect();
        } catch(MqttException me) {
            me.printStackTrace();
        }
        
    }
    
}

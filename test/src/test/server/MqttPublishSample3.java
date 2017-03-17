package test.server;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.netsteadfast.pine.base.model.BaseMessageProcess;

public class MqttPublishSample3 {
	
    public static void main(String[] args) throws Exception {

        String topic        = "鄰家的派豆龍";
        String content      = "";
        int qos             = 2;
        String broker       = "tcp://localhost:1991";
        String clientId     = "JavaSample_" + java.util.UUID.randomUUID();
        MemoryPersistence persistence = new MemoryPersistence();
        
        content = BaseMessageProcess.build()
        	.deviceId("PC-001")
        	.eventId("TEST01")
        	.name("鄰家的派豆龍TEST")
        	.scriptId("SHOW")
        	.scriptType("groovy")
        	.value("鄰家的派豆龍真他媽的感人阿!!!")
        	.message("")
        	.toJson();
        
        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);
            
            MqttMessage message = new MqttMessage( content.getBytes() );
            message.setQos(qos);            	
            sampleClient.publish(topic, message);
            
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
        
    }	

}

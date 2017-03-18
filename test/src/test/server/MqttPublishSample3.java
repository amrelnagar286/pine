package test.server;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.netsteadfast.pine.client.ClientUtils;

public class MqttPublishSample3 {
	
    public static void main(String[] args) throws Exception {

        String topic        = "鄰家的派豆龍";
        String content      = "";
        int qos             = 2;
        String broker       = "tcp://localhost:1991";
        String clientId     = "CLIENT003_" + java.util.UUID.randomUUID();
        
        
        try {
        	
        	ClientUtils.add(clientId, broker, qos, "", "", topic, content);
        	
        	ClientUtils.publish(clientId, topic, "EVENT001", "Print-test", "P001", "groovy", "鄰家的派豆龍真他媽的感人阿!!!", "try on my PC");
        	ClientUtils.publish(clientId, topic+"的假期", "EVENT002", "hello-world", "P002", "bsh", "鄰家的派豆龍~羅德里斯的假期!!! 感人上映.", "try on my PC");
        	
        	ClientUtils.close(clientId);
        	ClientUtils.remove(clientId);
        	
            System.exit(0);
        } catch(MqttException me) {
            me.printStackTrace();
        }
        
    }	

}

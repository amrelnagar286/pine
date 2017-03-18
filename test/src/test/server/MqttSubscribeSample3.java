package test.server;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.netsteadfast.pine.base.BaseMqttCallback;
import com.netsteadfast.pine.client.ClientUtils;

public class MqttSubscribeSample3 {
	
    public static void main(String[] args) throws Exception {

        String topic        = "鄰家的派豆龍";
        String content      = "";
        int qos             = 2;
        String broker       = "tcp://localhost:1991";
        String clientId     = "C003-client";
        
        
        try {
        	
        	ClientUtils.add(clientId, broker, qos, "", "", topic, content, new BaseMqttCallback());
        	
        	ClientUtils.subscribeById( clientId );
        	
        	Thread.sleep( 1000*20 );
        	
        	ClientUtils.unsubscribeById( clientId );
        	
        	ClientUtils.close(clientId);
        	ClientUtils.remove(clientId);
        	
            System.exit(0);
        } catch(MqttException me) {
            me.printStackTrace();
        }
        
    }		

}

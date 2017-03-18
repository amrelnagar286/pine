package test.server;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.netsteadfast.base.model.ScriptTypeCode;
import com.netsteadfast.pine.base.BaseMqttCallback;
import com.netsteadfast.pine.base.model.BaseMessageContent;
import com.netsteadfast.pine.base.model.BaseMessageProcess;
import com.netsteadfast.pine.client.ClientUtils;
import com.netsteadfast.pine.util.DataProcessUtils;

public class MqttPublishSample3 {
	
    public static void main(String[] args) throws Exception {

        String topic        = "鄰家的派豆龍";
        String content      = "";
        int qos             = 2;
        String broker       = "tcp://localhost:1991";
        String clientId     = "C003";
        
        
        try {
        	
        	ClientUtils.add(clientId, broker, qos, "", "", topic, content);
        	
        	//ClientUtils.publish(clientId, topic, "EVENT001", "Print-test", "P001", ScriptTypeCode.GROOVY, "鄰家的派豆龍真他媽的感人阿!!!", "try on my PC");
        	
    		BaseMessageContent messageContent = BaseMessageProcess.build()
    				.deviceId( clientId )
                	.eventId( "EVENT001" )
                	.name( "Print-test" )
                	.scriptId( "P001" )
                	.scriptType( ScriptTypeCode.GROOVY )
                	.value( "鄰家的派豆龍真他媽的感人阿!!!" )
                	.sysMessage( "try on my PC" )
                	.getContent();
    		DataProcessUtils.processPublish(messageContent);
    		
    		ClientUtils.publish(clientId, topic, messageContent);
    		
        	ClientUtils.publish(clientId, topic+"的假期", "EVENT002", "hello-world", "P002", ScriptTypeCode.BSH, "鄰家的派豆龍~羅德里斯的假期!!! 感人上映.", "try on my PC");
        	
        	ClientUtils.close(clientId);
        	ClientUtils.remove(clientId);
        	
            System.exit(0);
        } catch(MqttException me) {
            me.printStackTrace();
        }
        
    }	

}

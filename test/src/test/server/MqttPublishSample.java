package test.server;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublishSample {
	
    public static void main(String[] args) throws Exception {

        String topic        = "鄰家的派豆龍";
        String content      = 
        		"小孩：“求求你，派豆龙帮帮我吧。小静她，我的妹妹迷路了，现在肯定一个人独自哭泣了！拜托了，派豆龙。”" +
        		"派豆龙：“啊咧？你打电话报警了吗？”" +
        		"小孩：“拜托了，派豆龙。”" +
        		"派豆龙：“我的电话已经停机了。”" +
        		"小孩：“拜托了，派豆龙。”" +
        		"派豆龙：“我说你们啊，一遇到什么事就‘派豆龙’、‘派豆龙’的叫啊。上次乱按我家门铃的就是你吧，叔叔我可全都知道，说话啊，混蛋！”" +
        		"小孩：“你闭嘴，派豆龙！”" +
        		"派豆龙：”你这个小兔崽子，想怎么地啊？”";
        int qos             = 2;
        String broker       = "tcp://localhost:1991";
        String clientId     = "JavaSample_" + java.util.UUID.randomUUID();
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);
            
            //MqttMessage message = new MqttMessage(content.getBytes());
            //message.setQos(qos);
            
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

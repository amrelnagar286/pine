# Pine
Pine demo project, a test IoT project use MQTT ( moquette https://github.com/andsel/moquette )

<b>Demo server</b>
```JAVA
public class TestBrokerServer3 {
	
	public TestBrokerServer3() {
		
	}
	
	public void testServer() throws Exception {
		
		System.out.println("begin....");
		ServerUtils.add("BS003", "/home/git2/pine/test/resource/conf1/moquette.conf", new BrokerServerInterceptHandler());
		ServerUtils.startAll();
		System.out.println("completed....");
		
	}
	
	public static void main(String args[]) throws Exception {
		
		TestBrokerServer3 bs3 = new TestBrokerServer3();
		bs3.testServer();
		
	}
	
}
```

<br>
<br>

<b>Demo publish</b>
```JAVA
    public static void main(String[] args) throws Exception {

        String topic        = "鄰家的派豆龍";
        String content      = "";
        int qos             = 2;
        String broker       = "tcp://localhost:1991";
        String clientId     = "C003";
        
        
        try {
        	
        	ClientUtils.add(clientId, broker, qos, "", "", topic, content);

    		BaseMessageContent messageContent = BaseMessageProcess.build()
    		      .deviceId( clientId )
    		      .eventId( "EVENT001" )
    		      .name( "Print-test" )
    		      .scriptId( "P001" )
    		      .scriptType( ScriptTypeCode.GROOVY )
    		      .value( "鄰家的派豆龍真他媽的感人阿!!!" )
    		      .sysMessage( "try on my PC" )
    		      .getContent();
    		DataProcessUtils.processPublish(messageContent); // use process expression mode
    		ClientUtils.publish(clientId, topic, messageContent);
    		
        	ClientUtils.publish(clientId, topic+"的假期", "EVENT002", "hello-world", "P002", ScriptTypeCode.BSH, "鄰家的派豆龍~羅德里斯的假期!!! 感人上映.", "try on my PC");
        	
        	ClientUtils.close(clientId);
        	ClientUtils.remove(clientId);
        	
            System.exit(0);
        } catch(MqttException me) {
            me.printStackTrace();
        }
        
    }	
```
<b>Demo publish script</b>
<br>
/var/pine/device/C003/<b>publish/P001.groovy</b>
```
println "test process data for publish";
messageContent.value = messageContent.value + "初音是雙馬尾公主!!";
System.out.println( messageContent.value );
```

<br>
<br>

<b>Demo subscribe</b>
```JAVA
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
```
<b>Demo subscribe script</b>
<br>
/var/pine/device/C003/<b>subscribe/P001.groovy</b>
```
println "test";
System.out.println( messageContent.value );
```

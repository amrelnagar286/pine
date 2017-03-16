package test.server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptAcknowledgedMessage;
import io.moquette.interception.messages.InterceptConnectMessage;
import io.moquette.interception.messages.InterceptConnectionLostMessage;
import io.moquette.interception.messages.InterceptDisconnectMessage;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.interception.messages.InterceptSubscribeMessage;
import io.moquette.interception.messages.InterceptUnsubscribeMessage;
import io.moquette.server.Server;

public class TestBrokerServer2 {
	
	private List<Server> servers = new ArrayList<Server>();
	
	public TestBrokerServer2() {
		
	}
	
	public void testServer() throws Exception {
		
		System.out.println("===============================================");
		List<String> configs = new ArrayList<String>();
		configs.add("/home/git2/pine/test/resource/conf1/moquette.conf");
		configs.add("/home/git2/pine/test/resource/conf2/moquette.conf");
		for (String config : configs) {
			File configFile = new File(config);
			if (!configFile.exists()) {
				throw new Exception("Config file not found.");
			}
			Server server = new Server();
			servers.add(server);
			server.startServer(configFile);
			System.out.println( "load config: " + config );
			
			InterceptHandler ih = new TestInterceptHandler();
			server.addInterceptHandler(ih);
			
		}
		System.out.println("===============================================");
		
		
	}
	
	public static void main(String args[]) throws Exception {
		
		TestBrokerServer2 bs2 = new TestBrokerServer2();
		bs2.testServer();
		
	}
	
	private class TestInterceptHandler implements InterceptHandler {

		@Override
		public void onConnect(InterceptConnectMessage connectMessage) {
			System.out.println("connect clientId: " + connectMessage.getClientID() );
		}

		@Override
		public void onConnectionLost(InterceptConnectionLostMessage connectionLostMessage) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDisconnect(InterceptDisconnectMessage disconnectMessage) {
			System.out.println("disconnect clientId: " + disconnectMessage.getClientID() );
		}

		@Override
		public void onMessageAcknowledged(InterceptAcknowledgedMessage acknowledgedMessage) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPublish(InterceptPublishMessage publishMessage) {
			System.out.println("topic: " + publishMessage.getTopicName() );
			byte[] content = publishMessage.getPayload().array();
			if (content != null) {
				System.out.println("content: " + new String(content) );
			}
		}

		@Override
		public void onSubscribe(InterceptSubscribeMessage subscribeMessage) {
			System.out.println("subscribe clientID: " + subscribeMessage.getClientID() );
			System.out.println("subscribe topicFilter" + subscribeMessage.getTopicFilter() );
		}

		@Override
		public void onUnsubscribe(InterceptUnsubscribeMessage unsubscribeMessage) {
			System.out.println("unsubscribe clientID: " + unsubscribeMessage.getClientID() );
			System.out.println("unsubscribe clientID: " + unsubscribeMessage.getTopicFilter() );
		}
		
	}

}

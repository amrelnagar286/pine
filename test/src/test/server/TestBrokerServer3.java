package test.server;

import com.netsteadfast.pine.server.ServerUtils;

import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptAcknowledgedMessage;
import io.moquette.interception.messages.InterceptConnectMessage;
import io.moquette.interception.messages.InterceptConnectionLostMessage;
import io.moquette.interception.messages.InterceptDisconnectMessage;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.interception.messages.InterceptSubscribeMessage;
import io.moquette.interception.messages.InterceptUnsubscribeMessage;

public class TestBrokerServer3 {
	
	public TestBrokerServer3() {
		
	}
	
	public void testServer() throws Exception {
		
		System.out.println("begin....");
		ServerUtils.add("BS003", "/home/git2/pine/test/resource/conf1/moquette.conf", new TestInterceptHandler());
		ServerUtils.startAll();
		System.out.println("completed....");
		
	}
	
	public static void main(String args[]) throws Exception {
		
		TestBrokerServer3 bs3 = new TestBrokerServer3();
		bs3.testServer();
		
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

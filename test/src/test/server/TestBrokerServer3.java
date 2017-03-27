package test.server;

import com.netsteadfast.pine.server.BrokerServerInterceptHandler;
import com.netsteadfast.pine.server.ServerUtils;

public class TestBrokerServer3 {
	
	public TestBrokerServer3() {
		
	}
	
	public void testServer() throws Exception {
		
		System.out.println("begin....");
		ServerUtils.add("BS003", "/home/git2/pine/test/resource/conf1/moquette.conf", new BrokerServerInterceptHandler("BK001"));
		ServerUtils.startAll();
		System.out.println("completed....");
		
	}
	
	public static void main(String args[]) throws Exception {
		
		TestBrokerServer3 bs3 = new TestBrokerServer3();
		bs3.testServer();
		
	}
	
}

package test.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestBrokerServer {
	
	private List<BrokerServerCallableData> bsList = new ArrayList<BrokerServerCallableData>();
	
	public TestBrokerServer() {
		
	}
	
	public void testServer() throws Exception {
		
		System.out.println("===============================================");
		List<String> configs = new ArrayList<String>();
		configs.add("/home/git2/pine/test/resource/conf1/moquette.conf");
		configs.add("/home/git2/pine/test/resource/conf2/moquette.conf");
		ExecutorService bsCalculationPool = Executors.newFixedThreadPool( 1 );			
		for (String config : configs) {
			BrokerServerCallableData bsData = new BrokerServerCallableData();
			bsData = bsCalculationPool.submit( new BrokerServerCallable(config, true) ).get();
			bsList.add(bsData);
			System.out.println( "load config: " + config );
			//bsData.start(); // BrokerServerCallable(config, false)
		}
		System.out.println("===============================================");
		
	}
	
	public static void main(String args[]) throws Exception {
		
		TestBrokerServer bs = new TestBrokerServer();
		bs.testServer();
		
	}

}

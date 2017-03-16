package test.server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
		}
		System.out.println("===============================================");
		
	}
	
	public static void main(String args[]) throws Exception {
		
		TestBrokerServer2 bs2 = new TestBrokerServer2();
		bs2.testServer();
		
	}

}

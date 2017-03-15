package test.server;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

public class BrokerServerCallable implements Callable<BrokerServerCallableData> {
	
	private BrokerServerCallableData brokerServerCallableData;
	private String configFileRealPath = "";
	
	public BrokerServerCallable(String configFileRealPath) {
		super();
		this.configFileRealPath = configFileRealPath;
	}

	@Override
	public BrokerServerCallableData call() throws Exception {
		File configFile = new File(this.configFileRealPath);
		if (!configFile.exists()) {
			throw new Exception("Config file not found!");
		}
		this.brokerServerCallableData = new BrokerServerCallableData();
		this.brokerServerCallableData.setConfigFile(configFile);
		try {
			this.brokerServerCallableData.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.brokerServerCallableData;
	}

}

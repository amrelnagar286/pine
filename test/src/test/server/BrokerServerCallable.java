package test.server;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

public class BrokerServerCallable implements Callable<BrokerServerCallableData> {
	
	private BrokerServerCallableData brokerServerCallableData;
	private String configFileRealPath = "";
	private boolean startOnCall = false;
	private String exceptionMessage = "";
	
	public BrokerServerCallable(String configFileRealPath, boolean startOnCall) {
		super();
		this.configFileRealPath = configFileRealPath;
		this.startOnCall = startOnCall;
	}

	@Override
	public BrokerServerCallableData call() throws Exception {
		File configFile = new File(this.configFileRealPath);
		if (!configFile.exists()) {
			throw new Exception("Config file not found!");
		}
		this.brokerServerCallableData = new BrokerServerCallableData();
		this.brokerServerCallableData.setConfigFile(configFile);
		if (this.startOnCall) {
			try {
				this.brokerServerCallableData.start();
			} catch (IOException e) {
				e.printStackTrace();
				this.exceptionMessage = e.getMessage().toString();
			}			
		}
		return this.brokerServerCallableData;
	}

	public String getConfigFileRealPath() {
		return configFileRealPath;
	}

	public void setConfigFileRealPath(String configFileRealPath) {
		this.configFileRealPath = configFileRealPath;
	}

	public boolean isStartOnCall() {
		return startOnCall;
	}

	public void setStartOnCall(boolean startOnCall) {
		this.startOnCall = startOnCall;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

}

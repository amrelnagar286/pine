package test.server;

import java.io.File;
import java.io.IOException;

import io.moquette.server.Server;

public class BrokerServerCallableData implements java.io.Serializable {
	private static final long serialVersionUID = -7175662009622059494L;
	
	private Server server;
	private File configFile;
	
	public BrokerServerCallableData() {
		this.server = new Server();
	}
	
	public void start() throws IOException {
		this.server.startServer( this.configFile );
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}

}

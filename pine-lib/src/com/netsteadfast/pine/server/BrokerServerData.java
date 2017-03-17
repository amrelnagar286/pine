/* 
 * Copyright 2012-2017 Pine of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.pine.server;

import java.io.File;
import java.io.IOException;

import io.moquette.interception.InterceptHandler;
import io.moquette.server.Server;

public class BrokerServerData {
	
	private Server server;
	private File configFile;
	private InterceptHandler interceptHandler;
	private boolean start = false;
	
	public BrokerServerData() {
		this.server = new Server();
	}
	
	public void start() throws IOException {
		this.server.startServer( this.configFile );
		if (this.interceptHandler != null) {
			this.server.addInterceptHandler(this.interceptHandler);
		}
		this.start = true;
	}
	
	public void stop() throws Exception {
		this.server.stopServer();
		this.start = false;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public InterceptHandler getInterceptHandler() {
		return interceptHandler;
	}

	public void setInterceptHandler(InterceptHandler interceptHandler) {
		this.interceptHandler = interceptHandler;
	}

	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}

	public boolean isStart() {
		return start;
	}
	
}

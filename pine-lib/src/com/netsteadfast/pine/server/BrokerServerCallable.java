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

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
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ServerUtils {
	
	private static Map<String, BrokerServerData> brokerServers = new LinkedHashMap<String, BrokerServerData>();
	
	public synchronized static void add(String id, String configFileFullPath) throws Exception {
		if (StringUtils.isBlank(id) || StringUtils.isBlank(configFileFullPath)) {
			throw new IllegalArgumentException("args id or configFileFullPath blank");
		}
		File configFile = new File(configFileFullPath);
		if (!configFile.exists()) {
			throw new Exception("Config file not found!");
		}
		BrokerServerData bsData = new BrokerServerData();
		bsData.setConfigFile(configFile);
		brokerServers.put(id, bsData);
	}
	
	public synchronized static void start(String id) throws Exception {
		if (StringUtils.isBlank(id)) {
			throw new IllegalArgumentException("id blank");
		}
		BrokerServerData bsData = brokerServers.get(id);
		if (bsData.isStart()) {
			return;
		}
		bsData.start();
	}
	
	public synchronized static void stop(String id) throws Exception {
		BrokerServerData bsData = brokerServers.get(id);
		if (null == bsData) {
			return;
		}
		if (!bsData.isStart()) {
			return;
		}
		bsData.getServer().stopServer();
	}
	
	public synchronized static void remove(String id) throws Exception {
		if (StringUtils.isBlank(id)) {
			throw new IllegalArgumentException("id blank");
		}
		stop(id);
		brokerServers.remove(id);
	}
	
	public synchronized static void stopAll() {
		for (Map.Entry<String, BrokerServerData> entry : brokerServers.entrySet()) {
			try {
				stop( entry.getKey() );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized static void startAll() {
		for (Map.Entry<String, BrokerServerData> entry : brokerServers.entrySet()) {
			try {
				start( entry.getKey() );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	public synchronized static void removeAll() {
		for (Map.Entry<String, BrokerServerData> entry : brokerServers.entrySet()) {
			try {
				remove( entry.getKey() );
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
	
}

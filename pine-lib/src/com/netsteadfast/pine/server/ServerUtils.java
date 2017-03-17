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
	
	private static Map<String, BrokerServerCallableData> brokerServers = new LinkedHashMap<String, BrokerServerCallableData>();
	
	public static void add(String id, String configFileFullPath) throws Exception {
		if (StringUtils.isBlank(id) || StringUtils.isBlank(configFileFullPath)) {
			throw new IllegalArgumentException("args id or configFileFullPath blank");
		}
		File configFile = new File(configFileFullPath);
		if (!configFile.exists()) {
			throw new Exception("Config file not found!");
		}
		BrokerServerCallableData bsData = new BrokerServerCallableData();
		bsData.setConfigFile(configFile);
		brokerServers.put(id, bsData);
	}
	
	public static void stop(String id) throws Exception {
		BrokerServerCallableData bsData = brokerServers.get(id);
		if (null == bsData) {
			return;
		}
		bsData.getServer().stopServer();
	}
	
	public static void remove(String id) throws Exception {
		if (StringUtils.isBlank(id)) {
			return;
		}
		stop(id);
		brokerServers.remove(id);
	}
	
}

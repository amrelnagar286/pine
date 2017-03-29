/* 
 * Copyright 2012-2017 netsteadfast of copyright Chen Xin Nien
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
package com.netsteadfast.vo;

import com.netsteadfast.base.model.BaseValueObj;

public class BrokerVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -6533583997292214747L;
	
	private String oid;
	private String id;
	private String name;
	private String bkUsername;
	private String bkPassword;
	private String bkPort;
	private String bkWebsocketPort;
	private String description;
	
	// for page show need
	private String start = "";
	private String found = "";
	
	private long logConnect = 0;
	private long logConnectionLost = 0;
	private long logDisconnect = 0;
	private long logMessageAcknowledged = 0;
	private long logPublish = 0;
	private long logSubscribe = 0;
	private long logUnsubscribe = 0;
	
	public BrokerVO() {
		
	}
	
	public BrokerVO(String oid, String id, String name, String description) {
		super();
		this.oid = oid;
		this.id = id;
		this.name = name;
		this.description = description;
	}	

	public BrokerVO(String oid, String id, String name, String bkUsername, String bkPassword, String bkPort,
			String bkWebsocketPort, String description) {
		super();
		this.oid = oid;
		this.id = id;
		this.name = name;
		this.bkUsername = bkUsername;
		this.bkPassword = bkPassword;
		this.bkPort = bkPort;
		this.bkWebsocketPort = bkWebsocketPort;
		this.description = description;
	}

	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBkUsername() {
		return bkUsername;
	}

	public void setBkUsername(String bkUsername) {
		this.bkUsername = bkUsername;
	}

	public String getBkPassword() {
		return bkPassword;
	}

	public void setBkPassword(String bkPassword) {
		this.bkPassword = bkPassword;
	}

	public String getBkPort() {
		return bkPort;
	}

	public void setBkPort(String bkPort) {
		this.bkPort = bkPort;
	}

	public String getBkWebsocketPort() {
		return bkWebsocketPort;
	}

	public void setBkWebsocketPort(String bkWebsocketPort) {
		this.bkWebsocketPort = bkWebsocketPort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getFound() {
		return found;
	}

	public void setFound(String found) {
		this.found = found;
	}

	public long getLogConnect() {
		return logConnect;
	}

	public void setLogConnect(long logConnect) {
		this.logConnect = logConnect;
	}

	public long getLogConnectionLost() {
		return logConnectionLost;
	}

	public void setLogConnectionLost(long logConnectionLost) {
		this.logConnectionLost = logConnectionLost;
	}

	public long getLogDisconnect() {
		return logDisconnect;
	}

	public void setLogDisconnect(long logDisconnect) {
		this.logDisconnect = logDisconnect;
	}

	public long getLogMessageAcknowledged() {
		return logMessageAcknowledged;
	}

	public void setLogMessageAcknowledged(long logMessageAcknowledged) {
		this.logMessageAcknowledged = logMessageAcknowledged;
	}

	public long getLogPublish() {
		return logPublish;
	}

	public void setLogPublish(long logPublish) {
		this.logPublish = logPublish;
	}

	public long getLogSubscribe() {
		return logSubscribe;
	}

	public void setLogSubscribe(long logSubscribe) {
		this.logSubscribe = logSubscribe;
	}

	public long getLogUnsubscribe() {
		return logUnsubscribe;
	}

	public void setLogUnsubscribe(long logUnsubscribe) {
		this.logUnsubscribe = logUnsubscribe;
	}
	
}

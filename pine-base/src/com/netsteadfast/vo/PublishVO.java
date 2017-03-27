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

public class PublishVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 3578883516268036795L;
	
	private String oid;
	private String clientId;
	private String name;
	private String topic;
	private String qos;
	private String bkBrokerAddr;
	private String bkUsername;
	private String bkPassword;
	private String content;
	private String contentExpr;
	private String eventId;
	private String scriptType;
	private String scriptId;
	private String intervalSec;
	private String firstOnStart;
	private String description;
	
	// for page show need
	private String run = "";
	private String found = "";
	
	public PublishVO() {
		
	}
	
	public PublishVO(String oid, String clientId, String name, String topic, String qos, String bkBrokerAddr, 
			String intervalSec, String firstOnStart, String description) {
		super();
		this.oid = oid;
		this.clientId = clientId;
		this.name = name;
		this.topic = topic;
		this.qos = qos;
		this.bkBrokerAddr = bkBrokerAddr;
		this.intervalSec = intervalSec;
		this.firstOnStart = firstOnStart;
		this.description = description;
	}	
	
	public PublishVO(String oid, String clientId, String name, String topic, String qos, String bkBrokerAddr,
			String bkUsername, String bkPassword, String content, String contentExpr, String eventId, String scriptType,
			String scriptId, String intervalSec, String firstOnStart, String description) {
		super();
		this.oid = oid;
		this.clientId = clientId;
		this.name = name;
		this.topic = topic;
		this.qos = qos;
		this.bkBrokerAddr = bkBrokerAddr;
		this.bkUsername = bkUsername;
		this.bkPassword = bkPassword;
		this.content = content;
		this.contentExpr = contentExpr;
		this.eventId = eventId;
		this.scriptType = scriptType;
		this.scriptId = scriptId;
		this.intervalSec = intervalSec;
		this.firstOnStart = firstOnStart;
		this.description = description;
	}

	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getQos() {
		return qos;
	}

	public void setQos(String qos) {
		this.qos = qos;
	}

	public String getBkBrokerAddr() {
		return bkBrokerAddr;
	}

	public void setBkBrokerAddr(String bkBrokerAddr) {
		this.bkBrokerAddr = bkBrokerAddr;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentExpr() {
		return contentExpr;
	}

	public void setContentExpr(String contentExpr) {
		this.contentExpr = contentExpr;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getScriptType() {
		return scriptType;
	}

	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}

	public String getScriptId() {
		return scriptId;
	}

	public void setScriptId(String scriptId) {
		this.scriptId = scriptId;
	}

	public String getIntervalSec() {
		return intervalSec;
	}

	public void setIntervalSec(String intervalSec) {
		this.intervalSec = intervalSec;
	}

	public String getFirstOnStart() {
		return firstOnStart;
	}

	public void setFirstOnStart(String firstOnStart) {
		this.firstOnStart = firstOnStart;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

	public String getFound() {
		return found;
	}

	public void setFound(String found) {
		this.found = found;
	}
	
}

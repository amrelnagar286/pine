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
package com.netsteadfast.pine.base.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.base.Constants;

public class BaseMessageProcess implements java.io.Serializable {
	private static final long serialVersionUID = 7369798742403969516L;
	private BaseMessageContent content = null;
	
	public BaseMessageProcess() {
		this.content = new BaseMessageContent();
	}
	
	public static BaseMessageProcess build() {
		return new BaseMessageProcess();
	}
	
	public BaseMessageContent getContent() {
		return this.content;
	}
	
	public String toJson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();		
		return objectMapper.writeValueAsString(this.content);
	}
	
	public byte[] toJsonAsByte() throws UnsupportedEncodingException, JsonProcessingException {
		return this.toJson().getBytes(Constants.BASE_ENCODING);
	}
	
	public BaseMessageContent readValue2MessageContentFromJson(String jsonStr) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		this.content = mapper.readValue(jsonStr, BaseMessageContent.class);
		return this.content;
	}
	
	public BaseMessageProcess eventId(String eventId) {
		this.content.setEventId(eventId);
		return this;
	}
	
	public BaseMessageProcess deviceId(String deviceId) {
		this.content.setDeviceId(deviceId);
		return this;
	}
	
	public BaseMessageProcess name(String name) {
		this.content.setName(name);
		return this;
	}
	
	public BaseMessageProcess scriptType(String scriptType) {
		this.content.setScriptType(scriptType);
		return this;
	}
	
	public BaseMessageProcess scriptId(String scriptId) {
		this.content.setScriptId(scriptId);
		return this;
	}
	
	public BaseMessageProcess value(String value) {
		this.content.setValue(value);
		return this;
	}
	
	public BaseMessageProcess message(String message) {
		this.content.setMessage(message);
		return this;
	}

}

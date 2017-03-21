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
package com.netsteadfast.pine.client;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import com.netsteadfast.pine.base.model.BaseMessageContent;
import com.netsteadfast.pine.base.model.BaseMessageProcess;

public class ClientUtils {
	
	private static Map<String, ClientData> clients = new LinkedHashMap<String, ClientData>();
	
	public static String randomClientId() {
		return java.util.UUID.randomUUID().toString();
	}
	
	public synchronized static void add(String clientId, String broker, int qos, String username, String password, String topic, String content) throws MqttException {
		if (StringUtils.isBlank(clientId)) {
			throw new IllegalArgumentException("clientId blank");
		}		
		ClientData data = new ClientData();
		data.setClientId(clientId);
		data.setBroker(broker);
		data.setQos(qos);
		data.setTopic(topic);
		data.setContent(content);
		MqttClient mqttClient = new MqttClient(broker, clientId);
		data.setMqttClient(mqttClient);
		clients.put(clientId, data);
	}

	public synchronized static void add(String clientId, String broker, int qos, String username, String password, String topic, String content, MqttCallback callBack) throws MqttException {
		add(clientId, broker, qos, username, password, topic, content);
		clients.get(clientId).setCallBack(callBack);
		clients.get(clientId).getMqttClient().setCallback(callBack);
	}	
	
	public synchronized static void remove(String clientId) throws MqttException, Exception {
		close(clientId);
		clients.remove(clientId);
	}
	
	public synchronized static void removeAll() throws MqttException, Exception {
		for (Map.Entry<String, ClientData> entry : clients.entrySet()) {
			remove(entry.getKey());
		}
	}	
	
	public static ClientData get(String clientId) {
		return clients.get(clientId);
	}
	
	public synchronized static boolean connect(String clientId) throws Exception, MqttSecurityException, MqttException {
		if (clients.get(clientId).getMqttClient().isConnected()) {
			return true;
		}
        MqttConnectOptions connOpts = null;
        if (!StringUtils.isBlank(clients.get(clientId).getUsername()) && !StringUtils.isBlank(clients.get(clientId).getPassword())) {
        	connOpts = new MqttConnectOptions();
        	connOpts.setCleanSession(true);
        	connOpts.setUserName( clients.get(clientId).getUsername() );
        	connOpts.setPassword( clients.get(clientId).getPassword().toCharArray() );
        }		
        if (null != connOpts) {
        	clients.get(clientId).getMqttClient().connect(connOpts);
        } else {
        	clients.get(clientId).getMqttClient().connect();
        }
		return true;
	}
	
	public synchronized static boolean disconnect(String clientId) throws Exception, MqttSecurityException, MqttException {
		if (!clients.get(clientId).getMqttClient().isConnected()) {
			return true;
		}
		clients.get(clientId).getMqttClient().disconnect();
		return true;
	}
	
	public synchronized static void close(String clientId) throws Exception, MqttException {
		disconnect(clientId);
		clients.get(clientId).getMqttClient().close();
	}
	
	public synchronized static void publish(String clientId, String topic, String content) throws Exception, MqttException {
		connect(clientId);
		MqttMessage mqttMessage = new MqttMessage( content.getBytes() );
		mqttMessage.setQos( clients.get(clientId).getQos() );
		clients.get(clientId).getMqttClient().publish(topic, mqttMessage);		
	}
	
	public synchronized static void publish(String clientId, String topic, String eventId, String name, String scriptId, String scriptType, String contentString, String sysMessage) throws Exception, MqttException {
		String messageJsonContent = BaseMessageProcess.build()
				.deviceId( clientId )
            	.eventId( eventId )
            	.name( name )
            	.scriptId( scriptId )
            	.scriptType( scriptType )
            	.value( contentString )
            	.sysMessage( sysMessage )
            	.toJson();	
		System.out.println("messageJsonContent="+messageJsonContent); // for TEST now
		publish(clientId, topic, messageJsonContent);
	}
	
	public synchronized static void publish(String clientId, String topic, BaseMessageContent content) throws Exception, MqttException {
		String messageJsonContent = BaseMessageProcess.toJsonFrom(content);
		System.out.println("messageJsonContent="+messageJsonContent); // for TEST now
		publish(clientId, topic, messageJsonContent);
	}
	
	public synchronized static void subscribeById(String clientId) throws Exception, MqttException {
		connect(clientId);
		clients.get(clientId).getMqttClient().subscribe( clients.get(clientId).getTopic() );
	}	
	
	public synchronized static void unsubscribeById(String clientId) throws Exception, MqttException {
		clients.get(clientId).getMqttClient().unsubscribe( clients.get(clientId).getTopic() );
	}		
	
	public synchronized static void subscribe(String clientId, String topic) throws Exception, MqttException {
		connect(clientId);
		clients.get(clientId).getMqttClient().subscribe( topic );
	}	
	
	public synchronized static void unsubscribe(String clientId, String topic) throws Exception, MqttException {
		clients.get(clientId).getMqttClient().unsubscribe( topic );
	}		
	
}

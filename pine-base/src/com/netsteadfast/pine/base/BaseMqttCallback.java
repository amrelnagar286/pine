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
package com.netsteadfast.pine.base;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.netsteadfast.base.model.ScriptTypeCode;
import com.netsteadfast.pine.base.model.BaseMessageContent;
import com.netsteadfast.pine.base.model.BaseMessageProcess;
import com.netsteadfast.pine.util.DataProcessUtils;

public class BaseMqttCallback implements MqttCallback {

	@Override
	public void connectionLost(Throwable throwable) {
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		BaseMessageContent messageContent = BaseMessageProcess.build().readValue2MessageContentFromJson(message.toString());
		//System.out.println("messageArrived message=" + message.toString() ); // for TEST now
		if (!StringUtils.isBlank(messageContent.getScriptId()) && ScriptTypeCode.isTypeCode(messageContent.getScriptType())) {
			DataProcessUtils.processSubscribe(messageContent);
		}
	}
	
}

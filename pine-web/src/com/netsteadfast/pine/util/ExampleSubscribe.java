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
package com.netsteadfast.pine.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.netsteadfast.pine.base.model.BaseMessageContent;
import com.netsteadfast.pine.base.model.BaseMessageProcess;
import com.netsteadfast.pine.client.ClientUtils;

public class ExampleSubscribe implements MqttCallback {
	private static final String _id = java.util.UUID.randomUUID().toString();
	private static final String S01 = "S01-" + _id;
	private static final String S02 = "S02-" + _id;
	private static int temp = 0;
	private static int power = 0;
	private static String err = "";
	
	public static int getTemp() {
		return temp;
	}
	public static int getPower() {
		return power;
	}
	
	public static void setTemp(int temp) {
		ExampleSubscribe.temp = temp;
	}
	public static void setPower(int power) {
		ExampleSubscribe.power = power;
	}

	public static String getErr() {
		return err;
	}
	
	public static void start() {
		
		try {
			ClientUtils.add(S01, "tcp://127.0.0.1:1991", 2, "", "", "", "", new ExampleSubscribe());
			ClientUtils.add(S02, "tcp://127.0.0.1:1991", 2, "", "", "", "", new ExampleSubscribe());
			
			ClientUtils.subscribe(S01, "power");
			ClientUtils.subscribe(S02, "temperature");
			
		} catch (MqttException e) {
			e.printStackTrace();
			err = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			err = e.getMessage();
		}		
	}
	
	public static void stop() {
		try {
			ClientUtils.unsubscribe(S01, "power");
			ClientUtils.close(S01);
			ClientUtils.remove(S01);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ClientUtils.unsubscribe(S02, "temperature");
			ClientUtils.close(S02);
			ClientUtils.remove(S02);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable throwable) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println( "messageArrived=" + message.toString() );
		BaseMessageContent messageContent = BaseMessageProcess.build().readValue2MessageContentFromJson(message.toString());
		if ( "power".equals(topic) ) {
			ExampleSubscribe.setPower( NumberUtils.toInt(messageContent.getValue(), 0) );
		}
		if ( "temperature".equals(topic) ) {
			ExampleSubscribe.setTemp( NumberUtils.toInt(messageContent.getValue(), 0) );
		}		
	}
	
}

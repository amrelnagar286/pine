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

import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptAcknowledgedMessage;
import io.moquette.interception.messages.InterceptConnectMessage;
import io.moquette.interception.messages.InterceptConnectionLostMessage;
import io.moquette.interception.messages.InterceptDisconnectMessage;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.interception.messages.InterceptSubscribeMessage;
import io.moquette.interception.messages.InterceptUnsubscribeMessage;

public class BrokerServerInterceptHandler implements InterceptHandler {

	@Override
	public void onConnect(InterceptConnectMessage connectMessage) {
		// log to database
	}

	@Override
	public void onConnectionLost(InterceptConnectionLostMessage connectionLostMessage) {
		
	}

	@Override
	public void onDisconnect(InterceptDisconnectMessage disconnectMessage) {
		// log to database
	}

	@Override
	public void onMessageAcknowledged(InterceptAcknowledgedMessage acknowledgedMessage) {
		
	}

	@Override
	public void onPublish(InterceptPublishMessage publishMessage) {
		// log to database
	}

	@Override
	public void onSubscribe(InterceptSubscribeMessage subscribeMessage) {
		// log to database
	}

	@Override
	public void onUnsubscribe(InterceptUnsubscribeMessage unsubscribeMessage) {
		// log to database
	}

}

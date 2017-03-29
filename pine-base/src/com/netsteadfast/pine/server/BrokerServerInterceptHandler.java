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
package com.netsteadfast.pine.server;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.netsteadfast.pine.base.LogEventType;
import com.netsteadfast.pine.base.PineConfig;
import com.netsteadfast.util.DataUtils;

import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptAcknowledgedMessage;
import io.moquette.interception.messages.InterceptConnectMessage;
import io.moquette.interception.messages.InterceptConnectionLostMessage;
import io.moquette.interception.messages.InterceptDisconnectMessage;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.interception.messages.InterceptSubscribeMessage;
import io.moquette.interception.messages.InterceptUnsubscribeMessage;

public class BrokerServerInterceptHandler implements InterceptHandler {
	protected Logger logger=Logger.getLogger(BrokerServerInterceptHandler.class);
	private String brokerId = "";
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public BrokerServerInterceptHandler(String brokerId) {
		this.brokerId = brokerId;
	}

	@Override
	public void onConnect(InterceptConnectMessage connectMessage) {
		this.log2Db( LogEventType.CONNECT, connectMessage.getClientID(), null, null );
	}

	@Override
	public void onConnectionLost(InterceptConnectionLostMessage connectionLostMessage) {
		this.log2Db( LogEventType.CONNECTION_LOST, connectionLostMessage.getClientID(), null, null );
	}

	@Override
	public void onDisconnect(InterceptDisconnectMessage disconnectMessage) {
		this.log2Db( LogEventType.DISCONNECT, disconnectMessage.getClientID(), null, null );
	}

	@Override
	public void onMessageAcknowledged(InterceptAcknowledgedMessage acknowledgedMessage) {
		this.log2Db( LogEventType.MESSAGE_ACKNOWLEDGED, acknowledgedMessage.getMsg().getClientID(), acknowledgedMessage.getMsg().getTopic(), new String(acknowledgedMessage.getMsg().getMessage().array()) );
	}

	@Override
	public void onPublish(InterceptPublishMessage publishMessage) {
		this.log2Db( LogEventType.PUBLISH, publishMessage.getClientID(), publishMessage.getTopicName(), new String(publishMessage.getPayload().array()) );
	}

	@Override
	public void onSubscribe(InterceptSubscribeMessage subscribeMessage) {
		this.log2Db( LogEventType.SUBSCRIBE, subscribeMessage.getClientID(), subscribeMessage.getTopicFilter(), null );
	}

	@Override
	public void onUnsubscribe(InterceptUnsubscribeMessage unsubscribeMessage) {
		this.log2Db( LogEventType.UNSUBSCRIBE, unsubscribeMessage.getClientID(), unsubscribeMessage.getTopicFilter(), null );
	}

	public String getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}
	
	private void log2Db(String type, String clientId, String topic, String msg) {
		if (!PineConfig.getEnableLog()) {
			return;
		}
		if ( this.jdbcTemplate == null ) {
			try {
				this.jdbcTemplate = DataUtils.getJdbcTemplate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ( this.jdbcTemplate == null ) {
			logger.error("jdbcTemplate null");
			logger.warn(this.getBrokerId() + ", " + type + ", " + clientId + ", " + topic + ", " + msg);
			return;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("oid", java.util.UUID.randomUUID().toString());
		paramMap.put("brokerId", this.getBrokerId());
		paramMap.put("eventType", type);
		paramMap.put("clientId", ( StringUtils.isBlank(clientId) ? "unknown" : clientId ) );
		paramMap.put("topic", (StringUtils.defaultString(topic).length() > 150 ? topic.substring(0, 150) : topic) );
		paramMap.put("msg", (StringUtils.defaultString(msg).length() > 4000 ? msg.substring(0, 4000) : msg) );
		paramMap.put("cuserid", "sys");
		paramMap.put("cdate", new Date());
		
		try {
			this.jdbcTemplate.update(
					"insert into pi_event_log(OID, BROKER_ID, EVENT_TYPE, CLIENT_ID, TOPIC, MSG, CUSERID, CDATE) values(:oid, :brokerId, :eventType, :clientId, :topic, :msg, :cuserid, :cdate)", 
					paramMap);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paramMap.clear();
		paramMap = null;
	}
	
}

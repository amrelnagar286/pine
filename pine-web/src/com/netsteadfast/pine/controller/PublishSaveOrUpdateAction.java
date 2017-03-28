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
package com.netsteadfast.pine.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.netsteadfast.base.exception.ControllerException;
import com.netsteadfast.base.exception.ServiceException;
import com.netsteadfast.base.model.DefaultResult;
import com.netsteadfast.base.model.ScriptTypeCode;
import com.netsteadfast.base.model.YesNo;
import com.netsteadfast.pine.base.PineConstants;
import com.netsteadfast.pine.model.DefaultRestJsonResultObj;
import com.netsteadfast.pine.service.IPublishService;
import com.netsteadfast.po.PiPublish;
import com.netsteadfast.util.SimpleUtils;
import com.netsteadfast.vo.PublishVO;

@RestController
@EnableWebMvc
public class PublishSaveOrUpdateAction {
	
	private IPublishService<PublishVO, PiPublish, String> publishService;
	
	public IPublishService<PublishVO, PiPublish, String> getPublishService() {
		return publishService;
	}
	
	@Autowired
	@Resource(name="pine.service.PublishService")
	@Required	
	public void setPublishService(IPublishService<PublishVO, PiPublish, String> publishService) {
		this.publishService = publishService;
	}
	
	private PublishVO getRequestParam(HttpServletRequest request) throws ControllerException, Exception {
		String clientId = StringUtils.defaultString( request.getParameter("clientId") );
		String name = StringUtils.defaultString( request.getParameter("name") );
		String topic = StringUtils.defaultString( request.getParameter("topic") );
		String qos = StringUtils.defaultString( request.getParameter("qos") );
		String bkBrokerAddr = StringUtils.defaultString( request.getParameter("bkBrokerAddr") );
		String username = StringUtils.defaultString( request.getParameter("username") );
		String password = StringUtils.defaultString( request.getParameter("password") );
		String content = StringUtils.defaultString( request.getParameter("content") );
		String contentExpr = StringUtils.defaultString( request.getParameter("contentExpr") );
		String eventId = StringUtils.defaultString( request.getParameter("eventId") );
		String scriptType = StringUtils.defaultString( request.getParameter("scriptType") );
		String scriptId = StringUtils.defaultString( request.getParameter("scriptId") );
		String intervalSec = StringUtils.defaultString( request.getParameter("intervalSec") );
		String firstOnStart = StringUtils.defaultString( request.getParameter("firstOnStart") );
		String description = StringUtils.defaultString( request.getParameter("description") ).trim();
		if (!SimpleUtils.checkBeTrueOf_azAZ09(clientId)) {
			throw new ControllerException("Id incorrect!");
		}
		if (StringUtils.isBlank(name)) {
			throw new ControllerException("Name incorrect!");
		}
		if (StringUtils.isBlank(topic)) {
			throw new ControllerException("Topic incorrect!");
		}
		if (!NumberUtils.isNumber(qos)) {
			throw new ControllerException("QoS incorrect!");
		}
		if (StringUtils.isBlank(bkBrokerAddr)) {
			throw new ControllerException("Broker incorrect!");
		}
		if (!bkBrokerAddr.startsWith("tcp://")) {
			throw new ControllerException("Broker incorrect!");
		}		
		if (StringUtils.isBlank(content) && StringUtils.isBlank(contentExpr)) {
			throw new ControllerException("Content or Expression must input one!");
		}
		if (!NumberUtils.isNumber(intervalSec)) {
			throw new ControllerException("Interval incorrect!");
		}
		int interval = Integer.parseInt(intervalSec);
		if ((interval<60 || interval>86400) && interval!=0) {
			throw new ControllerException("Interval(sec) incorrect, value 60 ~ 86400!");
		}
		if (0 == interval) {
			firstOnStart = YesNo.YES;
		}
		if (!YesNo.YES.equals(firstOnStart) && !YesNo.NO.equals(firstOnStart)) {
			throw new ControllerException("First on start incorrect!");
		}		
		if (!StringUtils.isBlank(eventId) && !StringUtils.isBlank(scriptType) && !StringUtils.isBlank(scriptId)) { // 給 subscribe 需求的欄位, 三項都有輸入
			if (!SimpleUtils.checkBeTrueOf_azAZ09(eventId)) {
				throw new ControllerException("Event Id incorrect!");
			}
			if (!SimpleUtils.checkBeTrueOf_azAZ09(scriptId)) {
				throw new ControllerException("Script Id incorrect!");
			}			
			if (!ScriptTypeCode.isTypeCode(scriptType)) {
				throw new ControllerException("Script type incorrect!");
			}
		} else { // 給 subscribe 需求的欄位, 如果不輸入, 就是 script-id / event-id 個都要為空白
			scriptId = "";
			eventId = "";
			scriptType = "";
		}
		PublishVO publish = new PublishVO();
		publish.setClientId(clientId);
		publish.setName(name);
		publish.setTopic(topic);
		publish.setQos(qos);
		publish.setBkBrokerAddr(bkBrokerAddr);
		publish.setBkUsername(username);
		publish.setBkPassword(password);
		publish.setContent(content);
		publish.setContentExpr(contentExpr);
		publish.setEventId(eventId);
		publish.setScriptType(scriptType);
		publish.setScriptId(scriptId);
		publish.setIntervalSec(intervalSec);
		publish.setFirstOnStart(firstOnStart);
		publish.setDescription(description);
		return publish;
	}
	
	private void savePublishData(HttpServletRequest request, DefaultRestJsonResultObj<String> result) throws ControllerException, ServiceException, Exception {
		PublishVO publish = getRequestParam(request);
		if ( this.publishService.countByParams(null) > PineConstants.MAX_PUBLISH_JOB_SIZE ) {
			throw new ServiceException("Publish job limit is " + PineConstants.MAX_BROKER_SIZE);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("topic", publish.getTopic());
		if ( this.publishService.countByParams(paramMap) > 0 ) {
			throw new ControllerException("Same topic in other publish, please change!");
		}
		DefaultResult<PublishVO> pResult = this.publishService.saveObject(publish);
		if (pResult.getValue() == null) {
			throw new ServiceException( pResult.getSystemMessage().getValue() );
		}
		publish = pResult.getValue();
		result.setValue( publish.getOid() );
		result.setMessage( pResult.getSystemMessage().getValue() );
		result.setSuccess( YesNo.YES );
	}
	
	@RequestMapping(value = "publishSaveJson.do", produces = "application/json")
	public @ResponseBody DefaultRestJsonResultObj<String> save(HttpServletRequest request) {
		DefaultRestJsonResultObj<String> result = DefaultRestJsonResultObj.build();
		try {
			this.savePublishData(request, result);
		} catch (ControllerException ce) {
			result.setMessage( ce.getMessage().toString() );
		} catch (ServiceException se) {
			result.setMessage( se.getMessage().toString() );
		} catch (Exception e) {
			result.setMessage( e.getMessage().toString() );
		}
		return result;
	}
	
	
}

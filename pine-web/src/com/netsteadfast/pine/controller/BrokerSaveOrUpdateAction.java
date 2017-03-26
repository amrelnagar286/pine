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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.netsteadfast.base.SysMessageUtil;
import com.netsteadfast.base.SysMsgConstants;
import com.netsteadfast.base.exception.ControllerException;
import com.netsteadfast.base.exception.ServiceException;
import com.netsteadfast.base.model.DefaultResult;
import com.netsteadfast.base.model.YesNo;
import com.netsteadfast.pine.model.DefaultRestJsonResultObj;
import com.netsteadfast.pine.service.IBrokerService;
import com.netsteadfast.pine.util.BrokerUtils;
import com.netsteadfast.po.PiBroker;
import com.netsteadfast.util.SimpleUtils;
import com.netsteadfast.vo.BrokerVO;

@RestController
@EnableWebMvc
public class BrokerSaveOrUpdateAction {
	
	private IBrokerService<BrokerVO, PiBroker, String> brokerService;
	
	public IBrokerService<BrokerVO, PiBroker, String> getBrokerService() {
		return brokerService;
	}

	@Autowired
	@Resource(name="pine.service.BrokerService")
	@Required		
	public void setBrokerService(IBrokerService<BrokerVO, PiBroker, String> brokerService) {
		this.brokerService = brokerService;
	}
	
	private BrokerVO getRequestParam(HttpServletRequest request) throws ControllerException, Exception {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String port = request.getParameter("port");
		String websocketPort = request.getParameter("websocketPort");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String description = StringUtils.defaultString(request.getParameter("description")).trim();
		if (!SimpleUtils.checkBeTrueOf_azAZ09(id)) {
			throw new ControllerException("Id incorrect!");
		}
		if (StringUtils.isBlank(name)) {
			throw new ControllerException("Name incorrect!");
		}
		if (!NumberUtils.isNumber(port) || NumberUtils.toInt(port) < 1 || NumberUtils.toInt(port) > 65535) {
			throw new ControllerException("Port incorrect!");
		}
		if (!NumberUtils.isNumber(websocketPort) || NumberUtils.toInt(websocketPort) < 1 || NumberUtils.toInt(websocketPort) > 65535) {
			throw new ControllerException("web socket port incorrect!");
		}		
		if (!StringUtils.isBlank(username) && !SimpleUtils.checkBeTrueOf_azAZ09(username)) {
			throw new ControllerException("Username incorrect!");
		}
		if (!StringUtils.isBlank(password) && !SimpleUtils.checkBeTrueOf_azAZ09(password)) {
			throw new ControllerException("Password incorrect!");
		}		
		if (description.length() > 500) {
			description = description.substring(0, 500);
		}		
		BrokerVO broker = new BrokerVO();
		broker.setId(id);
		broker.setName(name);
		broker.setBkPort(port);
		broker.setBkWebsocketPort(websocketPort);
		broker.setBkUsername(username);
		broker.setBkPassword(password);
		broker.setDescription(description);
		return broker;
	}
	
	private void saveBrokerData(HttpServletRequest request, DefaultRestJsonResultObj<String> result) throws ControllerException, ServiceException, Exception {
		BrokerVO broker = this.getRequestParam(request);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bkPort", broker.getBkPort());
		if ( this.brokerService.countByParams(paramMap) > 0 ) {
			throw new ControllerException("Same port number found in other broker, please change!");
		}
		paramMap.clear();
		paramMap.put("bkWebsocketPort", broker.getBkWebsocketPort());
		if ( this.brokerService.countByParams(paramMap) > 0 ) {
			throw new ControllerException("Same web socket port number found in other broker, please change!");
		}		
		DefaultResult<BrokerVO> bResult = this.brokerService.saveObject(broker);
		if (bResult.getValue() == null) {
			throw new ServiceException( bResult.getSystemMessage().getValue() );
		}
		broker = bResult.getValue();
		result.setValue( broker.getOid() );
		result.setMessage( bResult.getSystemMessage().getValue() );
		result.setSuccess( YesNo.YES );
	}
	
	@RequestMapping(value = "startBrokerJson.do", produces = "application/json")
	public @ResponseBody DefaultRestJsonResultObj<String> startBroker(@RequestParam(name = "oid") String oid) {
		DefaultRestJsonResultObj<String> result = DefaultRestJsonResultObj.build();
		try {
			BrokerVO broker = BrokerUtils.getBroker(oid);
			BrokerUtils.stop( broker );
			BrokerUtils.start( broker );
			result.setValue( oid );
			result.setSuccess( YesNo.YES );
			result.setMessage( SysMessageUtil.get(SysMsgConstants.UPDATE_SUCCESS) );
		} catch (Exception e) {
			result.setMessage( e.getMessage().toString() );
		}
		return result;
	}	
	
	@RequestMapping(value = "stopBrokerJson.do", produces = "application/json")
	public @ResponseBody DefaultRestJsonResultObj<String> stopBroker(@RequestParam(name = "oid") String oid) {
		DefaultRestJsonResultObj<String> result = DefaultRestJsonResultObj.build();
		try {
			BrokerUtils.stop( BrokerUtils.getBroker(oid) );
			result.setValue( oid );
			result.setSuccess( YesNo.YES );
			result.setMessage( SysMessageUtil.get(SysMsgConstants.UPDATE_SUCCESS) );
		} catch (Exception e) {
			result.setMessage( e.getMessage().toString() );
		}
		return result;
	}
	
	@RequestMapping(value = "deleteBrokerJson.do", produces = "application/json")
	public @ResponseBody DefaultRestJsonResultObj<String> delete(@RequestParam(name = "oid") String oid) {
		DefaultRestJsonResultObj<String> result = DefaultRestJsonResultObj.build();
		try {
			BrokerVO broker = BrokerUtils.getBroker(oid);
			if (BrokerUtils.isWork( broker.getId() )) {
				throw new ServiceException("In service, cannot be delete!");
			}
			this.brokerService.hibernateSessionClear();
			BrokerUtils.stop( broker );
			DefaultResult<Boolean> bResult = this.brokerService.deleteObject(broker);
			if (bResult.getValue() != null && bResult.getValue()) {
				result.setValue( oid );
				result.setSuccess( YesNo.YES );				
			}
			result.setMessage( bResult.getSystemMessage().getValue() );
		} catch (ServiceException se) {
			result.setMessage( se.getMessage().toString() );
		} catch (Exception e) {
			result.setMessage( e.getMessage().toString() );
		}
		return result;
	}	
	
	@RequestMapping(value = "saveBrokerJson.do", produces = "application/json")
	public @ResponseBody DefaultRestJsonResultObj<String> save(HttpServletRequest request) {
		DefaultRestJsonResultObj<String> result = DefaultRestJsonResultObj.build();
		try {
			this.saveBrokerData(request, result);
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

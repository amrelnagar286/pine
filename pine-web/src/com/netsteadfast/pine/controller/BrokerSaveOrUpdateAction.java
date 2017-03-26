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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.netsteadfast.base.SysMessageUtil;
import com.netsteadfast.base.SysMsgConstants;
import com.netsteadfast.base.model.YesNo;
import com.netsteadfast.pine.model.DefaultRestJsonResultObj;
import com.netsteadfast.pine.util.BrokerUtils;

@RestController
@EnableWebMvc
public class BrokerSaveOrUpdateAction {
	
	@RequestMapping(value = "startBrokerJson.do", produces = "application/json")
	public @ResponseBody DefaultRestJsonResultObj<String> startBroker(@RequestParam(name = "oid") String oid) {
		DefaultRestJsonResultObj<String> result = DefaultRestJsonResultObj.build();
		try {
			BrokerUtils.start( BrokerUtils.getBroker(oid) );
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

}

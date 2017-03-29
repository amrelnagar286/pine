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

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.netsteadfast.base.Constants;
import com.netsteadfast.base.model.DefaultResult;
import com.netsteadfast.base.model.YesNo;
import com.netsteadfast.pine.model.DefaultRestJsonResultObj;
import com.netsteadfast.pine.service.IEventLogService;
import com.netsteadfast.po.PiEventLog;
import com.netsteadfast.vo.EventLogVO;

@Controller
@EnableWebMvc
public class LogAction {
	
	private IEventLogService<EventLogVO, PiEventLog, String> eventLogService;

	public IEventLogService<EventLogVO, PiEventLog, String> getEventLogService() {
		return eventLogService;
	}

	@Autowired
	@Resource(name="pine.service.EventLogService")
	@Required	
	public void setEventLogService(IEventLogService<EventLogVO, PiEventLog, String> eventLogService) {
		this.eventLogService = eventLogService;
	}
	
	@RequestMapping(value = "logList.do")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView();
		mv.addObject(Constants.PAGE_MESSAGE, "");
		List<PiEventLog> eventLogList = null;
		try {
			DefaultResult<List<PiEventLog>> result = this.eventLogService.findLastLogResult();
			if (result.getValue() != null) {
				eventLogList = result.getValue();
			} else {
				mv.addObject(Constants.PAGE_MESSAGE, result.getSystemMessage().getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject(Constants.PAGE_MESSAGE, e.getMessage().toString());
		}
		mv.setViewName("log/logList");
		mv.addObject("eventLogList", eventLogList);		
		return mv;
	}
	
	@RequestMapping(value = "logDeleteJson.do", produces = "application/json")
	public @ResponseBody DefaultRestJsonResultObj<String> logDelete() {
		DefaultRestJsonResultObj<String> result = DefaultRestJsonResultObj.build();
		try {
			DefaultResult<Boolean> logResult = this.eventLogService.deleteAllLog();
			if (logResult.getValue() != null && logResult.getValue()) {
				result.setSuccess( YesNo.YES );
				result.setValue( YesNo.YES );				
			}
			result.setMessage( logResult.getSystemMessage().getValue() );
		} catch (Exception e) {
			result.setMessage( e.getMessage().toString() );
		}
		return result;
	}		

}

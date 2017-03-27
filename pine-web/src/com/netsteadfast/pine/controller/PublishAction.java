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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.netsteadfast.base.Constants;
import com.netsteadfast.base.model.DefaultResult;
import com.netsteadfast.pine.service.IPublishService;
import com.netsteadfast.pine.util.PublishUtils;
import com.netsteadfast.po.PiPublish;
import com.netsteadfast.vo.PublishVO;

@Controller
public class PublishAction {
	
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
	
	@RequestMapping(value = "publishList.do")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView();
		mv.addObject(Constants.PAGE_MESSAGE, "");
		List<PublishVO> publishList = null;
		try {
			DefaultResult<List<PublishVO>> result = this.publishService.findSimpleResult();
			if ( result.getValue() != null ) {
				publishList = result.getValue();
				PublishUtils.checkStatus(publishList);
			} else {
				mv.addObject(Constants.PAGE_MESSAGE, result.getSystemMessage().getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject(Constants.PAGE_MESSAGE, e.getMessage().toString());
		}
		mv.setViewName("publish/publishList");
		mv.addObject("publishList", publishList);
		return mv;
	}	
	
	@RequestMapping(value = "publishCreate.do")
	public String create() {
		return "publish/publishCreate";
	}	
	
	@RequestMapping(value = "publishEdit.do")
	public ModelAndView edit(@RequestParam(name="oid") String oid) {
		String viewPage = "system/searchNoData";
		ModelAndView mv = new ModelAndView();
		mv.addObject(Constants.PAGE_MESSAGE, "");		
		PublishVO publish = null;
		try {
			publish = new PublishVO();
			publish.setOid(oid);
			DefaultResult<PublishVO> result = this.publishService.findObjectByOid(publish);
			if (result.getValue() != null) {
				publish = result.getValue();
				viewPage = "publish/publishEdit";
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject(Constants.PAGE_MESSAGE, e.getMessage().toString());
		}
		mv.setViewName( viewPage );
		mv.addObject("publish", publish);
		return mv;		
	}	

}

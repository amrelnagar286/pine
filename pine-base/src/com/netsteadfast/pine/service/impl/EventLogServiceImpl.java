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
package com.netsteadfast.pine.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.base.SysMessageUtil;
import com.netsteadfast.base.SysMsgConstants;
import com.netsteadfast.base.dao.IBaseDAO;
import com.netsteadfast.base.exception.ServiceException;
import com.netsteadfast.base.model.DefaultResult;
import com.netsteadfast.base.model.SystemMessage;
import com.netsteadfast.base.service.BaseService;
import com.netsteadfast.pine.dao.IEventLogDAO;
import com.netsteadfast.pine.service.IEventLogService;
import com.netsteadfast.po.PiEventLog;
import com.netsteadfast.vo.EventLogVO;

@Service("pine.service.EventLogService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class EventLogServiceImpl extends BaseService<EventLogVO, PiEventLog, String> implements IEventLogService<EventLogVO, PiEventLog, String> {
	protected Logger logger=Logger.getLogger(EventLogServiceImpl.class);
	
	private IEventLogDAO<PiEventLog, String> eventLogDAO;
	
	public EventLogServiceImpl() {
		super();
	}

	public IEventLogDAO<PiEventLog, String> getEventLogDAO() {
		return eventLogDAO;
	}

	@Autowired
	@Resource(name="pine.dao.EventLogDAO")
	@Required		
	public void setEventLogDAO(IEventLogDAO<PiEventLog, String> eventLogDAO) {
		this.eventLogDAO = eventLogDAO;
	}

	@Override
	protected IBaseDAO<PiEventLog, String> getBaseDataAccessObject() {
		return eventLogDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}

	@Override
	public DefaultResult<List<PiEventLog>> findLastLogResult() throws ServiceException, Exception {
		DefaultResult<List<PiEventLog>> result = new DefaultResult<List<PiEventLog>>();
		List<PiEventLog> searchList = this.eventLogDAO.findLastLogList();
		if (searchList != null && searchList.size() > 0) {
			result.setValue(searchList);
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(SysMsgConstants.SEARCH_NO_DATA)) );
		}
		return result;
	}

	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )		
	@Override
	public DefaultResult<Boolean> deleteAllLog() throws ServiceException, Exception {
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();
		result.setValue( Boolean.FALSE );
		int c = this.eventLogDAO.deleteAll();
		if ( c > 0 ) {
			result.setValue( Boolean.TRUE );
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(SysMsgConstants.DELETE_SUCCESS)) );
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(SysMsgConstants.DELETE_FAIL)) );
		}
		return result;
	}
	
}

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
import com.netsteadfast.pine.dao.IBrokerDAO;
import com.netsteadfast.pine.service.IBrokerService;
import com.netsteadfast.po.PiBroker;
import com.netsteadfast.vo.BrokerVO;

@Service("pine.service.BrokerService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class BrokerServiceImpl extends BaseService<BrokerVO, PiBroker, String> implements IBrokerService<BrokerVO, PiBroker, String> {
	protected Logger logger=Logger.getLogger(BrokerServiceImpl.class);
	private IBrokerDAO<PiBroker, String> brokerDAO;
	
	public BrokerServiceImpl() {
		super();
	}
	
	public IBrokerDAO<PiBroker, String> getBrokerDAO() {
		return brokerDAO;
	}

	@Autowired
	@Resource(name="pine.dao.BrokerDAO")
	@Required		
	public void setBrokerDAO(IBrokerDAO<PiBroker, String> brokerDAO) {
		this.brokerDAO = brokerDAO;
	}

	@Override
	protected IBaseDAO<PiBroker, String> getBaseDataAccessObject() {
		return brokerDAO;
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
	public DefaultResult<List<BrokerVO>> findSimpleResult() throws ServiceException, Exception {
		DefaultResult<List<BrokerVO>> result = new DefaultResult<List<BrokerVO>>();
		List<BrokerVO> searchList = this.brokerDAO.findSimpleList();
		if (searchList != null && searchList.size() > 0) {
			result.setValue(searchList);
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(SysMsgConstants.SEARCH_NO_DATA)) );
		}
		return result;
	}
	
}

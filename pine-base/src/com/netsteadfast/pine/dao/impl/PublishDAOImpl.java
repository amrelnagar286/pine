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
package com.netsteadfast.pine.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.netsteadfast.base.dao.BaseDAO;
import com.netsteadfast.pine.base.PineConstants;
import com.netsteadfast.pine.dao.IPublishDAO;
import com.netsteadfast.po.PiPublish;
import com.netsteadfast.vo.PublishVO;

@Repository("pine.dao.PublishDAO")
public class PublishDAOImpl extends BaseDAO<PiPublish, String> implements IPublishDAO<PiPublish, String> {

	@SuppressWarnings("unchecked")
	@Override
	public List<PublishVO> findSimpleList() throws Exception {
		return this.getCurrentSession().createQuery(
				"SELECT new com.netsteadfast.vo.PublishVO(a.oid, a.clientId, a.name, a.topic, a.qos, a.bkBrokerAddr, a.intervalSec, a.firstOnStart, a.description) FROM PiPublish a ORDER BY a.clientId, a.name ASC ")
				.setMaxResults( PineConstants.MAX_PUBLISH_JOB_SIZE )
				.list();
	}

}

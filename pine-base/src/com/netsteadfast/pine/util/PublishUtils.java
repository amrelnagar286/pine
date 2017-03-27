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
package com.netsteadfast.pine.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.netsteadfast.base.AppContext;
import com.netsteadfast.base.SysMessageUtil;
import com.netsteadfast.base.SysMsgConstants;
import com.netsteadfast.base.exception.ServiceException;
import com.netsteadfast.base.model.DefaultResult;
import com.netsteadfast.pine.client.ClientUtils;
import com.netsteadfast.pine.service.IPublishService;
import com.netsteadfast.po.PiPublish;
import com.netsteadfast.vo.PublishVO;

public class PublishUtils {
	protected static Logger logger = Logger.getLogger(PublishUtils.class);
	private static Map<String, PubHandlerCallable> pubHandlerMap = new HashMap<String, PubHandlerCallable>();
	
	@SuppressWarnings("unchecked")
	public static PublishVO getPublish(String oid) throws ServiceException, Exception {
		if (StringUtils.isBlank(oid)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}		
		IPublishService<PublishVO, PiPublish, String> publishService = (IPublishService<PublishVO, PiPublish, String>)
				AppContext.getBean("pine.service.PublishService");
		PublishVO publish = new PublishVO();
		publish.setOid(oid);
		DefaultResult<PublishVO> result = publishService.findObjectByOid(publish);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		publish = result.getValue();
		return publish;
	}
	
	@SuppressWarnings("unchecked")
	public static PublishVO getPublishById(String clientId) throws ServiceException, Exception {
		if (StringUtils.isBlank(clientId)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}		
		IPublishService<PublishVO, PiPublish, String> publishService = (IPublishService<PublishVO, PiPublish, String>)
				AppContext.getBean("pine.service.PublishService");
		PublishVO publish = new PublishVO();
		publish.setClientId(clientId);
		DefaultResult<PublishVO> result = publishService.findByUK(publish);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		publish = result.getValue();
		return publish;
	}	
	
	public static boolean isFound(String id) {
		if (ClientUtils.get(id) == null) {
			return false;
		}
		return true;
	}	
	
	public static boolean isRun(String id) {
		if ( pubHandlerMap.get(id) != null && pubHandlerMap.get(id).isRun() ) {
			return true;
		}
		return false;
	}
	
	public static void pub(String oid) throws ServiceException, Exception {
		PublishVO publish = PublishUtils.getPublish(oid);
		pub( publish );
	}	
	
	public static void pubById(String clientId) throws ServiceException, Exception {
		PublishVO publish = PublishUtils.getPublishById(clientId);
		pub( publish );		
	}	
	
	public static void pub(PublishVO publish) throws ServiceException, Exception {
		if (null == publish) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_INCORRECT));
		}
		clear( publish );
		ExecutorService executorService = Executors.newFixedThreadPool( 1 );
		PubHandlerCallable pubHandler = null;
		executorService.submit( (pubHandler = new PubHandlerCallable(publish)) );
		pubHandlerMap.put(publish.getClientId(), pubHandler);
	}
	
	public static void stop(String oid) throws ServiceException, Exception {
		PublishVO publish = PublishUtils.getPublish(oid);
		stop( publish );
	}	
	
	public static void stopById(String clientId) throws ServiceException, Exception {
		PublishVO publish = PublishUtils.getPublishById(clientId);
		stop( publish );		
	}	
	
	public static void stop(PublishVO publish) throws ServiceException, Exception {
		if (null == publish) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_INCORRECT));
		}
		if ( pubHandlerMap.get(publish.getClientId()) == null ) {
			return;
		}
		PubHandlerCallable pubHandler = pubHandlerMap.get(publish.getClientId());
		pubHandler.setRun(false);
		ClientUtils.disconnect( publish.getClientId() );
	}
	
	public static void clear(String oid) throws ServiceException, Exception {
		PublishVO publish = PublishUtils.getPublish(oid);
		clear( publish );
	}	
	
	public static void clearById(String clientId) throws ServiceException, Exception {
		PublishVO publish = PublishUtils.getPublishById(clientId);
		clear( publish );		
	}	
	
	public static void clear(PublishVO publish) throws ServiceException, Exception {
		if (null == publish) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_INCORRECT));
		}
		if ( pubHandlerMap.get(publish.getClientId()) == null ) {
			return;
		}
		stop( publish );
		pubHandlerMap.remove( publish.getClientId() );
		ClientUtils.remove( publish.getClientId() );
	}
	
	@SuppressWarnings("unchecked")
	public static void startAll() throws ServiceException, Exception {
		IPublishService<PublishVO, PiPublish, String> publishService = (IPublishService<PublishVO, PiPublish, String>)
				AppContext.getBean("pine.service.PublishService");
		List<PublishVO> pubList = publishService.findListVOByParams( null );
		if (null == pubList || pubList.size() < 1) {
			return;
		}
		for (PublishVO publish : pubList) {
			pub(publish);
		}
	}
	
	public static void stopAll() throws Exception {
		for (Map.Entry<String, PubHandlerCallable> entry : pubHandlerMap.entrySet()) {
			clear( entry.getValue().getData() );
		}
	}
	
}

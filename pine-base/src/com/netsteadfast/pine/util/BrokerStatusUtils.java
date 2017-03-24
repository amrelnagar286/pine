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

import java.util.List;

import com.netsteadfast.base.model.YesNo;
import com.netsteadfast.pine.server.ServerUtils;
import com.netsteadfast.vo.BrokerVO;

public class BrokerStatusUtils {
	
	public static void checkStatus(List<BrokerVO> brokers) {
		if (null == brokers) {
			return;
		}
		for (BrokerVO broker : brokers) {
			broker.setFound(YesNo.NO);
			broker.setStart(YesNo.NO);
			if (isFound(broker.getId())) {
				broker.setFound(YesNo.YES);
			}
			if (isWork(broker.getId())) {
				broker.setStart(YesNo.YES);
			}
		}
	}
	
	public static boolean isFound(String id) {
		if (ServerUtils.get(id) == null) {
			return false;
		}
		return true;
	}	
	
	public static boolean isWork(String id) {
		if (ServerUtils.get(id) == null) {
			return false;
		}
		return ServerUtils.get(id).isStart();
	}

}

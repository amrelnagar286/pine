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
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;

import com.netsteadfast.base.SysMessageUtil;
import com.netsteadfast.base.SysMsgConstants;
import com.netsteadfast.base.model.ScriptTypeCode;
import com.netsteadfast.base.model.YesNo;
import com.netsteadfast.pine.client.ClientData;
import com.netsteadfast.pine.client.ClientUtils;
import com.netsteadfast.util.ScriptExpressionUtils;
import com.netsteadfast.vo.PublishVO;

public class PubHandlerCallable implements Callable<PublishVO> {
	private PublishVO data;
	private boolean run = true;
	
	public PubHandlerCallable(PublishVO publish) {
		this.data = publish;
	}
	
	public PublishVO getData() {
		return data;
	}

	public void setData(PublishVO data) {
		this.data = data;
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}
	
	private Object getValueFromExpr() throws Exception {
		if (!StringUtils.isBlank(this.data.getContentExpr())) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("publish", this.data);
			paramMap.put("value", "");
			try {
				ScriptExpressionUtils.execute(ScriptTypeCode.GROOVY, this.data.getContentExpr(), paramMap, paramMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return StringUtils.defaultString( (String)paramMap.get("value") );
		}		
		return "";
	}

	private void action() throws Exception {
		if (null == this.data) {
			throw new Exception(SysMessageUtil.get(SysMsgConstants.PARAMS_INCORRECT));
		}
		long intervalSecMs = Integer.parseInt( this.data.getIntervalSec() ) * 1000;
		String content = "";
		if (!StringUtils.isBlank(this.data.getContent())) {
			content = this.data.getContent();
		}
		if (!StringUtils.isBlank(this.data.getContentExpr())) {
			content = String.valueOf( this.getValueFromExpr() );
		}
		ClientData client = ClientUtils.get(this.data.getClientId());
		if (null == client) {
			ClientUtils.add(this.data.getClientId(), this.data.getBkBrokerAddr(), Integer.parseInt(this.data.getQos()), this.data.getBkUsername(), this.data.getBkPassword(), this.data.getTopic(), "");
			client = ClientUtils.get(this.data.getClientId());
		}
		
		if ( intervalSecMs < 60000 ) { // 小於 60 秒的, FIRST_ON_START 一定是 'Y'
			try {
				ClientUtils.publish(this.data.getClientId(), this.data.getTopic(), this.data.getEventId(), this.data.getName(), this.data.getScriptId(), this.data.getScriptType(), content, "");
				ClientUtils.disconnect(this.data.getClientId());				
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.run = false;
			return;
		}
		
		if (YesNo.YES.equals(this.data.getFirstOnStart())) {
			try {
				ClientUtils.publish(this.data.getClientId(), this.data.getTopic(), this.data.getEventId(), this.data.getName(), this.data.getScriptId(), this.data.getScriptType(), content, "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		while (true) {
			Thread.sleep(intervalSecMs);
			try {
				if (!StringUtils.isBlank(this.data.getContentExpr())) {
					content = String.valueOf( this.getValueFromExpr() );
				}
				if (this.run) {
					ClientUtils.publish(this.data.getClientId(), this.data.getTopic(), this.data.getEventId(), this.data.getName(), this.data.getScriptId(), this.data.getScriptType(), content, "");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public PublishVO call() throws Exception {
		this.action();
		return this.data;
	}	
	
}

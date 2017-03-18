/* 
 * Copyright 2012-2017 Pine of copyright Chen Xin Nien
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.netsteadfast.base.Constants;
import com.netsteadfast.pine.base.PineConfig;
import com.netsteadfast.pine.base.model.BaseMessageContent;
import com.netsteadfast.util.ScriptExpressionUtils;

public class DataProcessUtils {
	
	public static String getScriptFileFullPath(BaseMessageContent messageContent) {
		String baseDir = PineConfig.getScriptBaseDir();
		return baseDir + "/" + messageContent.getDeviceId() + "/" + messageContent.getScriptId() + "." + messageContent.getScriptType().toLowerCase();
	}
	
	public static String readScriptContent(String fileFullPath) {
		File file = new File(fileFullPath);
		String content = "";
		try {
			content = FileUtils.readFileToString( file, Constants.BASE_ENCODING );
		} catch (IOException e) {
			e.printStackTrace();
		}
		file = null;
		return content;
	}
	
	public static void process(BaseMessageContent messageContent) throws Exception {
		String scriptFile = getScriptFileFullPath(messageContent);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("messageContent", messageContent);
		ScriptExpressionUtils.execute(messageContent.getScriptType(), readScriptContent(scriptFile), paramMap, paramMap);
	}
	
}

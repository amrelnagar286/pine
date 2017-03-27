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

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.netsteadfast.base.AppContext;
import com.netsteadfast.base.Constants;
import com.netsteadfast.base.SysMessageUtil;
import com.netsteadfast.base.SysMsgConstants;
import com.netsteadfast.base.exception.ServiceException;
import com.netsteadfast.base.model.DefaultResult;
import com.netsteadfast.base.model.YesNo;
import com.netsteadfast.pine.base.PineConfig;
import com.netsteadfast.pine.server.BrokerServerInterceptHandler;
import com.netsteadfast.pine.server.ServerUtils;
import com.netsteadfast.pine.service.IBrokerService;
import com.netsteadfast.po.PiBroker;
import com.netsteadfast.vo.BrokerVO;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class BrokerUtils {
	protected static Logger logger = Logger.getLogger(BrokerUtils.class);
	private static Map<String, String> resource = new HashMap<String, String>();
	private static final String conf_hazelcast = "hazelcast";
	private static final String conf_moquette_log = "moquette-log";
	private static final String conf_moquettet = "moquette";
	private static final String conf_password_file = "password_file";
	
	static {
		loadResources();
	}
	
	private static void loadResources() {
		try {
			resource.put( conf_hazelcast, IOUtils.toString(BrokerUtils.class.getClassLoader().getResource("META-INF/resource/hazelcast.xml").openStream(), Constants.BASE_ENCODING) );
			resource.put( conf_moquette_log, IOUtils.toString(BrokerUtils.class.getClassLoader().getResource("META-INF/resource/moquette-log.properties").openStream(), Constants.BASE_ENCODING) );
			resource.put( conf_moquettet, IOUtils.toString(BrokerUtils.class.getClassLoader().getResource("META-INF/resource/moquette.conf.ftl").openStream(), Constants.BASE_ENCODING) );
			resource.put( conf_password_file, IOUtils.toString(BrokerUtils.class.getClassLoader().getResource("META-INF/resource/password_file.conf.ftl").openStream(), Constants.BASE_ENCODING) );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getResourceProcessResult(String templateResource, Map<String, Object> paramMap) throws Exception {
		StringTemplateLoader templateLoader = new StringTemplateLoader();
		templateLoader.putTemplate("resourceTemplate", templateResource );
		Configuration cfg = new Configuration( Configuration.VERSION_2_3_21 );
		cfg.setTemplateLoader(templateLoader);
		Template template = cfg.getTemplate("resourceTemplate", Constants.BASE_ENCODING);
		Writer out = new StringWriter();
		template.process(paramMap, out);
		return out.toString();
	}	
	
	private static String getResourceConf_hazelcast() {
		return resource.get(conf_hazelcast);
	}
	
	private static String getResourceConf_moquette_log() {
		return resource.get(conf_moquette_log);
	}
	
	private static String getResourceConf_moquettet() {
		return resource.get(conf_moquettet);
	}
	
	private static String getResourceConf_password_file() {
		return resource.get(conf_password_file);
	}		
	
	private static String getResourceConf_password_file(String username, String password) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("password", password);
		return getResourceProcessResult(getResourceConf_password_file(), paramMap);
	}
	
	private static String getResourceConf_moquettet(String port, String websocketPort) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("port", port);
		paramMap.put("websocketPort", websocketPort);
		return getResourceProcessResult(getResourceConf_moquettet(), paramMap);		
	}	
	
	@SuppressWarnings("unchecked")
	public static BrokerVO getBroker(String oid) throws ServiceException, Exception {
		if (StringUtils.isBlank(oid)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		IBrokerService<BrokerVO, PiBroker, String> brokerService = (IBrokerService<BrokerVO, PiBroker, String>) 
				AppContext.getBean("pine.service.BrokerService");
		BrokerVO broker = new BrokerVO();
		broker.setOid(oid);
		DefaultResult<BrokerVO> result = brokerService.findObjectByOid(broker);
		if (result.getValue() == null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		broker = result.getValue();
		return broker;
	}	
	
	@SuppressWarnings("unchecked")
	public static BrokerVO getBrokerById(String id) throws ServiceException, Exception {
		if (StringUtils.isBlank(id)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		IBrokerService<BrokerVO, PiBroker, String> brokerService = (IBrokerService<BrokerVO, PiBroker, String>) 
				AppContext.getBean("pine.service.BrokerService");
		BrokerVO broker = new BrokerVO();
		broker.setId(id);
		DefaultResult<BrokerVO> result = brokerService.findByUK(broker);
		if (result.getValue() == null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		broker = result.getValue();
		return broker;
	}
	
	public static void stopById(String id) throws Exception {
		ServerUtils.stop( id );
	}
	
	public static void stop(BrokerVO broker) throws Exception {
		ServerUtils.stop( broker.getId() );
	}
	
	public static void stopAndRemoveById(String id) throws Exception {
		ServerUtils.stop( id );
		ServerUtils.remove( id );
	}
	
	public static void stopAndRemove(BrokerVO broker) throws Exception {
		ServerUtils.stop( broker.getId() );
		ServerUtils.remove( broker.getId() );
	}	
	
	public static void startById(String id) throws ServiceException, Exception {
		BrokerVO broker = getBrokerById(id);
		start( broker );
	}
	
	public static void start(BrokerVO broker) throws Exception {
		if (null == broker || StringUtils.isBlank(broker.getId())) {
			throw new IllegalArgumentException("id blank");
		}
		if (ServerUtils.get(broker.getId()) != null && ServerUtils.get(broker.getId()).isStart()) {
			logger.warn( "Broker : " + broker.getId() + " already in service." );
			return;
		}
		String configFullPath = writeConfig(broker);
		ServerUtils.remove(broker.getId());
		ServerUtils.add(broker.getId(), configFullPath, new BrokerServerInterceptHandler());
		ServerUtils.start(broker.getId());
	}
	
	public static String writeConfigById(String id) throws ServiceException, Exception {
		if (StringUtils.isBlank(id)) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		return writeConfig( getBrokerById(id) );
	}
	
	public static void stopAll() {
		ServerUtils.stopAll();
	}
	
	public static void stopAndRemoveAll() {
		ServerUtils.stopAll();
		ServerUtils.removeAll();
	}	
	
	@SuppressWarnings("unchecked")
	public static void startAll() throws ServiceException, Exception {
		IBrokerService<BrokerVO, PiBroker, String> brokerService = (IBrokerService<BrokerVO, PiBroker, String>) 
				AppContext.getBean("pine.service.BrokerService");
		List<BrokerVO> brokers = brokerService.findListVOByParams( null );
		if (brokers == null) {
			return;
		}
		StringBuilder err = new StringBuilder();
		for (BrokerVO broker : brokers) {
			try {
				start( broker );
			} catch (Exception e) {
				if (e.getMessage() != null) {
					err.append( e.getMessage().toString() ).append("\n");
				} else {
					err.append( e.toString() ).append("\n");
				}
			}
		}
		if (err.length() > 0) {
			throw new Exception( err.toString() );
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> writeConfigAll() throws ServiceException, Exception {
		IBrokerService<BrokerVO, PiBroker, String> brokerService = (IBrokerService<BrokerVO, PiBroker, String>) 
				AppContext.getBean("pine.service.BrokerService");
		List<BrokerVO> brokers = brokerService.findListVOByParams( null );
		if (brokers == null) {
			return null;
		}
		List<String> configsPath = new ArrayList<String>();
		for (BrokerVO broker : brokers) {
			configsPath.add( writeConfig(broker) );
		}
		return configsPath;
	}	
	
	public static String writeConfig(BrokerVO broker) throws Exception {
		if (null == broker) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK));
		}
		logger.info("config file : " + broker.getId() + " - " + broker.getName());
		if (ServerUtils.get(broker.getId()) != null && ServerUtils.get(broker.getId()).isStart()) {
			throw new Exception("Broker " + broker.getId() + " in service, cannot write config.");
		}
		String configFullPath = "";
		String username = StringUtils.defaultString(broker.getBkUsername()).trim();
		String password = StringUtils.defaultString(broker.getBkPassword()).trim();
		if (StringUtils.isBlank(username)) {
			username = "tester";
		}
		if (StringUtils.isBlank(password)) {
			password = "tester";
		}
		String newResourceConfForMoquettet = getResourceConf_moquettet(broker.getBkPort(), broker.getBkWebsocketPort());
		String newResourceConfForPasswordFile = getResourceConf_password_file( username, DigestUtils.sha256Hex(password) );
		
		String fullPathStr = PineConfig.getServerConfigDir() + "/" + broker.getId();
		File fullPath = new File(fullPathStr);
		File newFile_conf_hazelcast = new File(fullPathStr + "/hazelcast.xml");
		File newFile_conf_moquette_log = new File(fullPathStr + "/moquette-log.properties");
		File newFile_conf_moquettet = new File(fullPathStr + "/moquette.conf");
		File newFile_conf_conf_password_file = new File(fullPathStr + "/password_file.conf");
		try {
			logger.warn("config path : " + fullPath.getAbsolutePath());
			if (fullPath.exists()) {
				FileUtils.forceDelete(fullPath);
			}
			FileUtils.forceMkdir(fullPath);
			FileUtils.writeStringToFile(newFile_conf_hazelcast, getResourceConf_hazelcast(), Constants.BASE_ENCODING);
			FileUtils.writeStringToFile(newFile_conf_moquette_log, getResourceConf_moquette_log(), Constants.BASE_ENCODING);
			FileUtils.writeStringToFile(newFile_conf_moquettet, newResourceConfForMoquettet, Constants.BASE_ENCODING);
			FileUtils.writeStringToFile(newFile_conf_conf_password_file, newResourceConfForPasswordFile, Constants.BASE_ENCODING);
			logger.info("success config file : " + broker.getId() + " - " + broker.getName());
			configFullPath = newFile_conf_moquettet.getAbsolutePath();
		} catch (Exception e) {
			throw e;
		} finally {
			fullPath = null;
			newFile_conf_hazelcast = null;
			newFile_conf_moquette_log = null;
			newFile_conf_moquettet = null;
			newFile_conf_conf_password_file = null;
		}
		return configFullPath;
	}
	
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

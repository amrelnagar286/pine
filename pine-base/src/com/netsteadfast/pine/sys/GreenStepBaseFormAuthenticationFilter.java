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
package com.netsteadfast.pine.sys;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.netsteadfast.base.Constants;
import com.netsteadfast.base.model.YesNo;
import com.netsteadfast.vo.AccountVO;

public class GreenStepBaseFormAuthenticationFilter extends FormAuthenticationFilter {
	protected static Logger logger = Logger.getLogger(GreenStepBaseFormAuthenticationFilter.class);
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	
	public GreenStepBaseFormAuthenticationFilter() {
		super();
	}
	
	protected String getCaptcha(ServletRequest request) {		
        return WebUtils.getCleanParam(request, this.getCaptchaParam());
    }
	
	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}
	
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = StringUtils.defaultString(this.getUsername(request));
		String password = StringUtils.defaultString(this.getPassword(request));
		String captcha = StringUtils.defaultString(this.getCaptcha(request));
		boolean rememberMe = false;
		String host = StringUtils.defaultString(getHost(request));
		char pwd[] = null;
		try {
			ShiroLoginSupport loginSupport = new ShiroLoginSupport();
			pwd = loginSupport.getAccountService().tranPassword(password).toCharArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new GreenStepBaseUsernamePasswordToken(
				username, 
				pwd, 
				rememberMe, 
				host, 
				captcha);
	}
	
	protected void doCaptchaValidate(HttpServletRequest request, GreenStepBaseUsernamePasswordToken token) {
		// pine 不需要
	}
	
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		GreenStepBaseUsernamePasswordToken token = 
				(GreenStepBaseUsernamePasswordToken) this.createToken(request, response);
		try {
			this.doCaptchaValidate((HttpServletRequest)request, token);
			
			ShiroLoginSupport loginSupport = new ShiroLoginSupport();
			AccountVO account = loginSupport.queryUserValidate(token.getUsername());
			
			Subject subject = this.getSubject(request, response);
			subject.login(token);
				
			// set session
			this.setUserSession((HttpServletRequest)request, (HttpServletResponse)response, account);
			return this.onLoginSuccess(token, subject, request, response);			
		} catch (AuthenticationException e) {
			// clear session	
			logger.warn( e.getMessage().toString() );			
			this.getSubject(request, response).logout();
			((HttpServletRequest)request).getSession().removeAttribute( Constants.SESS_ACCOUNT );
			return this.onLoginFailure(token, e, request, response);
		}		
	} 
	
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, 
			ServletRequest request, ServletResponse response) throws Exception {
		
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        if (!this.isAjaxRequest(httpServletRequest)) {
        	httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getSuccessUrl());
        } else {
    		response.setCharacterEncoding( Constants.BASE_ENCODING );
    		response.setContentType("application/json");
    		response.getWriter().write(Constants.NO_AUTHZ_JSON_DATA);
        }
		return false;
	}
	
	private void setUserSession(HttpServletRequest request, HttpServletResponse response, AccountVO account) throws Exception {		
		request.getSession().setAttribute("account", account.getAccount());
		String httpSessionId = request.getSession().getId();
		if (StringUtils.isBlank(httpSessionId)) {
			httpSessionId = "NULL";
		}
	}
	
    protected boolean isAjaxRequest(HttpServletRequest request) {
    	return "XMLHttpRequest".equalsIgnoreCase( request.getHeader("X-Requested-With") );
    }
    
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {	
    	if (isAjaxRequest((HttpServletRequest)request)) {
    		response.setCharacterEncoding( Constants.BASE_ENCODING );
    		response.setContentType("application/json");
    		response.getWriter().write(Constants.NO_LOGIN_JSON_DATA);
    		return;
    	}
    	if (YesNo.YES.equals( request.getParameter( Constants.PAGE_CHANGE_URL_PARAM ) )) {
    		WebUtils.issueRedirect(request, response, "/pages/system/login_again.jsp");
    		return;
    	}
    	WebUtils.issueRedirect(request, response, getLoginUrl());
    }	
    
}

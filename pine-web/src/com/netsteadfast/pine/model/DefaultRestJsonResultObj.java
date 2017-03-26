package com.netsteadfast.pine.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.netsteadfast.base.model.YesNo;

public class DefaultRestJsonResultObj<T> implements java.io.Serializable {
	private static final long serialVersionUID = 3754125684960027639L;
	
	private T value = null;
	private String success = YesNo.NO;
	private String message = "";
	private String login = YesNo.NO;
	private String isAuthorize = YesNo.NO;
	
	public static <T> DefaultRestJsonResultObj<T> build() {
		DefaultRestJsonResultObj<T> obj = new DefaultRestJsonResultObj<T>();
		Subject subject = SecurityUtils.getSubject();
		if (subject != null && !StringUtils.isBlank((String)subject.getPrincipal())) {
			obj.setIsAuthorize(YesNo.YES); // Pine-web no need check isAuthorize
			obj.setLogin(YesNo.YES);
		} else {
			obj.message = "Please login!";
		}
		return obj;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public String getSuccess() {
		return success;
	}
	
	public void setSuccess(String success) {
		this.success = success;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getIsAuthorize() {
		return isAuthorize;
	}
	
	public void setIsAuthorize(String isAuthorize) {
		this.isAuthorize = isAuthorize;
	}

}

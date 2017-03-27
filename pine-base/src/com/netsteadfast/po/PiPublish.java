package com.netsteadfast.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.netsteadfast.base.model.BaseEntity;
import com.netsteadfast.base.model.EntityPK;
import com.netsteadfast.base.model.EntityUK;

@Entity
@Table(
		name="pi_publish", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"CLIENT_ID"} ) 
		} 
)
public class PiPublish extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -8895887135759756603L;
	
	private String oid;
	private String clientId;
	private String name;
	private String topic;
	private String qos;
	private String bkBrokerAddr;
	private String bkUsername;
	private String bkPassword;
	private String content;
	private String contentExpr;
	private String eventId;
	private String scriptType;
	private String scriptId;
	private String intervalSec;
	private String firstOnStart;
	private String description;
	private String cuserid;
	private Date cdate;
	private String uuserid;
	private Date udate;		
	
	public PiPublish() {
		
	}
	
	public PiPublish(String oid, String clientId, String name, String topic, String qos, String bkBrokerAddr, 
			String intervalSec, String firstOnStart, String description) {
		super();
		this.oid = oid;
		this.clientId = clientId;
		this.name = name;
		this.topic = topic;
		this.qos = qos;
		this.bkBrokerAddr = bkBrokerAddr;
		this.intervalSec = intervalSec;
		this.firstOnStart = firstOnStart;
		this.description = description;
	}	
	
	public PiPublish(String oid, String clientId, String name, String topic, String qos, String bkBrokerAddr,
			String bkUsername, String bkPassword, String content, String contentExpr, String eventId, String scriptType,
			String scriptId, String intervalSec, String firstOnStart, String description, String cuserid, Date cdate,
			String uuserid, Date udate) {
		super();
		this.oid = oid;
		this.clientId = clientId;
		this.name = name;
		this.topic = topic;
		this.qos = qos;
		this.bkBrokerAddr = bkBrokerAddr;
		this.bkUsername = bkUsername;
		this.bkPassword = bkPassword;
		this.content = content;
		this.contentExpr = contentExpr;
		this.eventId = eventId;
		this.scriptType = scriptType;
		this.scriptId = scriptId;
		this.intervalSec = intervalSec;
		this.firstOnStart = firstOnStart;
		this.description = description;
		this.cuserid = cuserid;
		this.cdate = cdate;
		this.uuserid = uuserid;
		this.udate = udate;
	}

	@Override
	@Id
	@EntityPK(name="oid")
	@Column(name="OID")
	public String getOid() {
		return oid;
	}
	@Override
	public void setOid(String oid) {
		this.oid = oid;
	}	
	
	@EntityUK(name="clientId")
	@Column(name="CLIENT_ID")
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="TOPIC")
	public String getTopic() {
		return topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	@Column(name="QOS")
	public String getQos() {
		return qos;
	}
	
	public void setQos(String qos) {
		this.qos = qos;
	}
	
	@Column(name="BK_BROKER_ADDR")
	public String getBkBrokerAddr() {
		return bkBrokerAddr;
	}
	
	public void setBkBrokerAddr(String bkBrokerAddr) {
		this.bkBrokerAddr = bkBrokerAddr;
	}
	
	@Column(name="BK_USERNAME")
	public String getBkUsername() {
		return bkUsername;
	}
	
	public void setBkUsername(String bkUsername) {
		this.bkUsername = bkUsername;
	}
	
	@Column(name="BK_PASSWORD")
	public String getBkPassword() {
		return bkPassword;
	}
	
	public void setBkPassword(String bkPassword) {
		this.bkPassword = bkPassword;
	}
	
	@Column(name="CONTENT")
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="CONTENT_EXPR")
	public String getContentExpr() {
		return contentExpr;
	}
	
	public void setContentExpr(String contentExpr) {
		this.contentExpr = contentExpr;
	}
	
	@Column(name="EVENT_ID")
	public String getEventId() {
		return eventId;
	}
	
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	@Column(name="SCRIPT_TYPE")
	public String getScriptType() {
		return scriptType;
	}
	
	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}
	
	@Column(name="SCRIPT_ID")
	public String getScriptId() {
		return scriptId;
	}
	
	public void setScriptId(String scriptId) {
		this.scriptId = scriptId;
	}
	
	@Column(name="INTERVAL_SEC")
	public String getIntervalSec() {
		return intervalSec;
	}
	
	public void setIntervalSec(String intervalSec) {
		this.intervalSec = intervalSec;
	}
	
	@Column(name="FIRST_ON_START")
	public String getFirstOnStart() {
		return firstOnStart;
	}
	
	public void setFirstOnStart(String firstOnStart) {
		this.firstOnStart = firstOnStart;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	@Column(name="CUSERID")
	public String getCuserid() {
		return this.cuserid;
	}
	@Override
	public void setCuserid(String cuserid) {
		this.cuserid = cuserid;
	}
	@Override
	@Column(name="CDATE")
	public Date getCdate() {
		return this.cdate;
	}
	@Override
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	@Override
	@Column(name="UUSERID")
	public String getUuserid() {
		return this.uuserid;
	}
	@Override
	public void setUuserid(String uuserid) {
		this.uuserid = uuserid;
	}
	@Override
	@Column(name="UDATE")
	public Date getUdate() {
		return this.udate;
	}
	@Override
	public void setUdate(Date udate) {
		this.udate = udate;
	}	

}

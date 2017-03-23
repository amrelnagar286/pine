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
		name="pi_broker", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"ID"} ) 
		} 
)
public class PiBroker extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -2613319305581633925L;
	
	private String oid;
	private String id;
	private String name;
	private String bkUsername;
	private String bkPassword;
	private String bkPort;
	private String bkWebsocketPort;
	private String cuserid;
	private Date cdate;
	private String uuserid;
	private Date udate;	
	
	public PiBroker() {
		
	}
	
	public PiBroker(String oid, String id, String name, String bkUsername, String bkPassword, String bkPort, String bkWebsocketPort) {
		super();
		this.oid = oid;
		this.id = id;
		this.name = name;
		this.bkUsername = bkUsername;
		this.bkPassword = bkPassword;
		this.bkPort = bkPort;
		this.bkWebsocketPort = bkWebsocketPort;
	}	
	
	public PiBroker(String oid, String id, String name, String bkUsername, String bkPassword, String bkPort,
			String bkWebsocketPort, String cuserid, Date cdate, String uuserid, Date udate) {
		super();
		this.oid = oid;
		this.id = id;
		this.name = name;
		this.bkUsername = bkUsername;
		this.bkPassword = bkPassword;
		this.bkPort = bkPort;
		this.bkWebsocketPort = bkWebsocketPort;
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
	
	@EntityUK(name="id")
	@Column(name="ID")	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	@Column(name="BK_PORT")
	public String getBkPort() {
		return bkPort;
	}
	
	public void setBkPort(String bkPort) {
		this.bkPort = bkPort;
	}
	
	@Column(name="BK_WEBSOCKET_PORT")
	public String getBkWebsocketPort() {
		return bkWebsocketPort;
	}
	
	public void setBkWebsocketPort(String bkWebsocketPort) {
		this.bkWebsocketPort = bkWebsocketPort;
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

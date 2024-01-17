package com.yanshen.author.entity;

import java.io.Serializable;

public class SystemInfoDTO implements Serializable {

	private static final long serialVersionUID = 3927756921803972993L;
	
	/**
	 * *AppID 
	 */
	private long app_id;
	/**
	 * *私钥
	 */
	private String security_key;
	/**
	 * *公钥
	 */
	private String public_key;
	/**
	 * *对接的外部系统名称
	 */
	private String system_name;
	/**
	 * *对接的外部系统管理人员，也可以是我方代理负责人员
	 */
	private String system_admin;
	/**
	 * *对接联系人
	 */
	private String phone_number;
	
	/**
	 * *saas平台租户编号
	 */
	private String saas_tenantid;
	/**
	 * *saas平台产品线ID
	 */
	private String saas_productid;
	/**
	 * *saas平台租户（项目）订单
	 */
	private String saas_orderid;
	
	public long getApp_id() {
		return app_id;
	}
	public void setApp_id(long app_id) {
		this.app_id = app_id;
	}
	public String getSecurity_key() {
		return security_key;
	}
	public void setSecurity_key(String security_key) {
		this.security_key = security_key;
	}
	public String getPublic_key() {
		return public_key;
	}
	public void setPublic_key(String public_key) {
		this.public_key = public_key;
	}
	public String getSystem_name() {
		return system_name;
	}
	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}
	public String getSystem_admin() {
		return system_admin;
	}
	public void setSystem_admin(String system_admin) {
		this.system_admin = system_admin;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getSaas_tenantid() {
		return saas_tenantid;
	}
	public void setSaas_tenantid(String saas_tenantid) {
		this.saas_tenantid = saas_tenantid;
	}
	public String getSaas_productid() {
		return saas_productid;
	}
	public void setSaas_productid(String saas_productid) {
		this.saas_productid = saas_productid;
	}
	public String getSaas_orderid() {
		return saas_orderid;
	}
	public void setSaas_orderid(String saas_orderid) {
		this.saas_orderid = saas_orderid;
	}
}

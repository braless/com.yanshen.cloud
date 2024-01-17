package com.yanshen.author.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BaseQueryPO implements Serializable {
	
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
	
	/**
	 * *创建时间
	 */
	private Date create_at;
	/**
	 * *修改时间
	 */
	private Date modify_at;
	
	private String query_date;

	private String query_date_end;
	/**
	 * saas平台场景id
	 */
	private String saas_communityid;
	/**
	 * saas平台小区id
	 */
	private String saas_regionid;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 *  做情况区分用 在sql中注明情况 所传值对应的情况
	 */
	private Integer type;
	/**
	 * 垃圾情况区分
	 */
	private Integer ljType;
	/**
	 * 游标值
	 */
	private Long cursor;
	/**
	 * 条数
	 */
	private Integer limitNum;
	/**
	 * 设备id
	 */
	private String equipmentid;
	/**
	 * 所属地区列表
	 */
	private List<String> regionIdList;

	public List<String> getRegionIdList() {
		return regionIdList;
	}

	public void setRegionIdList(List<String> regionIdList) {
		this.regionIdList = regionIdList;
	}

	public String getEquipmentid() {
		return equipmentid;
	}

	public void setEquipmentid(String equipmentid) {
		this.equipmentid = equipmentid;
	}

	public String getQuery_date_end() {
		return query_date_end;
	}

	public void setQuery_date_end(String query_date_end) {
		this.query_date_end = query_date_end;
	}

	public Integer getLjType() {
		return ljType;
	}

	public void setLjType(Integer ljType) {
		this.ljType = ljType;
	}

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

	public Long getCursor() {
		return cursor;
	}

	public void setCursor(Long cursor) {
		this.cursor = cursor;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSaas_regionid() {
		return saas_regionid;
	}

	public void setSaas_regionid(String saas_regionid) {
		this.saas_regionid = saas_regionid;
	}

	public String getSaas_communityid() {
		return saas_communityid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setSaas_communityid(String saas_communityid) {
		this.saas_communityid = saas_communityid;
	}

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
	public Date getCreate_at() {
		return create_at;
	}
	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}
	public Date getModify_at() {
		return modify_at;
	}
	public void setModify_at(Date modify_at) {
		this.modify_at = modify_at;
	}
	public String getQuery_date() {
		return query_date;
	}
	public void setQuery_date(String query_date) {
		this.query_date = query_date;
	}
}

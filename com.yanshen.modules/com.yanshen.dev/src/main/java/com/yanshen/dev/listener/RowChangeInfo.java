package com.yanshen.dev.listener;

import java.util.Map;

public class RowChangeInfo {

	private String tableName;

	private String zzidKey;

	private Long zzid;

	private String uuid;

	private String time;

	private String opType;

	private Map<String, String> before;

	private Map<String, String> after;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getZzidKey() {
		return zzidKey;
	}

	public void setZzidKey(String zzidKey) {
		this.zzidKey = zzidKey;
	}

	public Long getZzid() {
		return zzid;
	}

	public void setZzid(Long zzid) {
		this.zzid = zzid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Map<String, String> getBefore() {
		return before;
	}

	public void setBefore(Map<String, String> before) {
		this.before = before;
	}

	public Map<String, String> getAfter() {
		return after;
	}

	public void setAfter(Map<String, String> after) {
		this.after = after;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
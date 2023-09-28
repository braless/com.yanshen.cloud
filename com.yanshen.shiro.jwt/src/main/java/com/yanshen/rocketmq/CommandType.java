package com.yanshen.rocketmq;

public enum CommandType {
    /**
     * 说明
     */
    SAVE_ORGAN("1001", "新增组织架构"),
    UPDATE_ORGAN("1002", "修改组织架构"),
    DELETE_ORGAN("1003", "删除组织架构"),
    CREATE_MSG("2001", "创建系统消息"),
    UPDATE_MSG("2002", "修改系统消息"),
    SAVE_ORGAN_PERSON("3001", "新增组织人员"),
    SAVE_ORGAN_PERSON_WORK("3002", "新增组织人员工作"),
    UPDATE_ORGAN_PERSON("3003", "修改组织人员"),
    UPDATE_ORGAN_PERSON_WORK("3004", "修改组织人员工作"),
    DELETE_ORGAN_PERSON("3005", "删除组织人员"),
    ORGAN_PERSON_TRANSFER("3006", "组织人员调动"),
    RESET_PERSON_PASSWORD("3007", "重置人员密码");

    /**
     * 类型
     */
    private final String type;

    /**
     * 说明
     */
    private final String remark;

    CommandType(String type, String remark) {
        this.type = type;
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public String getRemark() {
        return remark;
    }

}

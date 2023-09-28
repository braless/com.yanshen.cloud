package com.yanshen.entity.reqValidate;

import com.yanshen.aspect.annotation.IDMust;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-26 16:09
 * @Description:
 * @Location: com.yanshen.entity.reqValidate
 * @Project: com.yanshen.cloud
 */

@Data
public class BaseReqValidate {

    @NotNull(message = "分页序号不能为空",groups = BaseReqValidate.class)
    @IDMust(message = "分页序号需大于0",groups = BaseReqValidate.class)
    private Integer pageNum;

    @NotNull(message = "分页大小不能为空",groups = BaseReqValidate.class)
    @IDMust(message = "分页大小需大于0",groups = BaseReqValidate.class)
    private Integer pageSize;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;
}

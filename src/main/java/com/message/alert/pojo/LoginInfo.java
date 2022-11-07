package com.message.alert.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = " 登录实体")
public class LoginInfo {
    private static final long serialVersionUID = -9098553521895935064L;

    /**
     * 登录记录id
     */
    @ApiModelProperty(value = "登录记录id")
    private Integer loginId;
}

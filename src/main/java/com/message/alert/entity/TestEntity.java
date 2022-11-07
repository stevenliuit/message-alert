package com.message.alert.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = " 业主信息")
public class TestEntity {
    private static final long serialVersionUID = 6446047740449605616L;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

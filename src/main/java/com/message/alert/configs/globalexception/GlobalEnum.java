package com.message.alert.configs.globalexception;

public enum GlobalEnum {
    /**
     * 全局异常码
     */
    SUNCCESS("200","成功"),
    FAIL("500","系统错误"),
    RUNTIMEEXC("10001","网络错误"),
    LOGINEXC("10002","未登录"),
    QUEUEEXC("10003","请求超时"),
    OTHEREXC("10004","非法请求"),
    PARAMEXC("10005","参数缺失"),
    USERNOTEXIET("10006","用户不存在"),
    NOTENABLE("10007","禁用状态"),
    AUTHEXPIRE("10008","授权过期"),
    NOTAUTH("10009","未授权"),
    OWNERINFO("10010","业主信息需录入"),
    USERHASEXIET("10011","已认证"),
    USER_HAS_AUTHING("10012","认证中"),
    AUTHERROR("10013","验证失败"),
    EXCEL_DATA_ERROR("10014","数据格式错误");

    private String code;
    private String message;

    GlobalEnum(String code,String message){
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return code;
    }

    public String getMessage(){
        return  message;
    }
}

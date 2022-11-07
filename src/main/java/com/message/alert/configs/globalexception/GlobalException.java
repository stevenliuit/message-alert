package com.message.alert.configs.globalexception;

public class GlobalException extends RuntimeException{
    private static final long serialVersionUID = -8672085778149610628L;

    private String code;
    private String message;

    public GlobalException(String code, String message){
        this.code = code;
        this.message = message;
    }

    public GlobalException(GlobalEnum globalEnum){
        this.code = globalEnum.getCode();
        this.message = globalEnum.getMessage();
    }

    public String getCode(){
        return code;
    }

    @Override
    public String getMessage(){
         return message;
    }
}

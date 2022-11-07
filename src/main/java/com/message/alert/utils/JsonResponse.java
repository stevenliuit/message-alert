package com.message.alert.utils;

import java.io.Serializable;
import java.lang.Object;

import com.message.alert.configs.globalexception.GlobalEnum;

public class JsonResponse<T> implements Serializable {
    private static final long serialVersionUID = -3077106187440355524L;
    /** 成功 */
    public static final String SUCCESS = GlobalEnum.SUNCCESS.getCode();
    public static final String MESSAGE = GlobalEnum.SUNCCESS.getCode();

    /** 失败 */
    public static final String FAIL =  GlobalEnum.FAIL.getCode();
    private String code;
    private String message;
    private Object data;

    public JsonResponse(){
        this.code = GlobalEnum.SUNCCESS.getCode();
        this.message = GlobalEnum.SUNCCESS.getMessage();
    }

    public JsonResponse(GlobalEnum globalEnum){
        setEnum(globalEnum);
    }
    public JsonResponse(String code, String message){
         this.code = code;
         this.message = message;
    }

    public void setEnum(GlobalEnum globalEnum){
        this.code = globalEnum.getCode();
        this.message = globalEnum.getMessage();
    }
    public static <T> JsonResponse<T> success()
    {
        return restResult(null, SUCCESS, MESSAGE);
    }

    public static <T> JsonResponse<T> success(T data)
    {
        return restResult(data, SUCCESS, MESSAGE);
    }

    public static <T> JsonResponse<T> success(T data, String msg)
    {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> JsonResponse<T> fail()
    {
        return restResult(null, FAIL, null);
    }

    public static <T> JsonResponse<T> fail(String msg)
    {
        return restResult(null, FAIL, msg);
    }

    public static <T> JsonResponse<T> fail(T data)
    {
        return restResult(data, FAIL, null);
    }

    public static <T> JsonResponse<T> fail(T data, String msg)
    {
        return restResult(data, FAIL, msg);
    }

    public static <T> JsonResponse<T> fail(String code, String msg)
    {
        return restResult(null, code, msg);
    }

    private static <T> JsonResponse<T> restResult(T data, String code, String msg)
    {
        JsonResponse<T> apiResult = new JsonResponse<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMessage(msg);
        return apiResult;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

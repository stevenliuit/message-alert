package com.message.alert.utils;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.message.alert.configs.globalexception.GlobalEnum;
import com.message.alert.configs.globalexception.GlobalException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class JsonBaseUtils {

    private final Logger logger = LoggerFactory.getLogger(JsonBaseUtils.class);

    private static JsonMapper jsonMapper = new JsonMapper();

    private JsonBaseUtils(){

    }

    public static String objectToStr(Object o){
        try {
            if(Objects.isNull(o)){
                return "";
            }
            return jsonMapper.writeValueAsString(o);
        }catch (Exception e){
            throw new GlobalException(GlobalEnum.RUNTIMEEXC);
        }
    }
}

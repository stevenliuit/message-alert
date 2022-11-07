package com.message.alert.configs.globalexception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.message.alert.utils.JsonResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public JsonResponse globalExceptionHandler(GlobalException e){
        e.printStackTrace();
        logger.info(e.getMessage());
        return new JsonResponse(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse exceptionHandler(Exception e){
        e.printStackTrace();
        logger.error(e.getMessage(),e);
        return new JsonResponse(GlobalEnum.RUNTIMEEXC.getCode(),GlobalEnum.RUNTIMEEXC.getMessage());
    }
}

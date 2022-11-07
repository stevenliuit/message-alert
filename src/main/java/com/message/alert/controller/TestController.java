package com.message.alert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.message.alert.service.TestService;
import com.message.alert.utils.JsonResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "测试")
@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;

  
    @ApiOperation(value = "测试接口入")
    @RequestMapping(value = "tteesstt", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse tteeSstt(@RequestBody(required = false) String abc) {

        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setData(testService);
        return jsonResponse;
    }
}

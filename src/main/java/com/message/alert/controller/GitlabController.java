package com.message.alert.controller;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.message.alert.utils.JsonResponse;
import com.message.alert.utils.gitleb.PushEvent;
import com.message.alert.utils.gitleb.PushHandle;

@Slf4j
@RestController
@RequestMapping("/api/gitlab/")
public class GitlabController {

  @PostMapping("{token}")
  public JsonResponse<?> event(@PathVariable String token, @RequestBody String json, @RequestHeader("X-Gitlab-Event") String event) throws Exception {
    log.info("gitlab event: {} body: {}", event, json);
    if (Objects.equals(event, "Push Hook")) {
      PushEvent pushEvent = JSONObject.parseObject(json, PushEvent.class);
      PushHandle.handle(pushEvent, token);
    } else if (Objects.equals(event, "Merge Request")) {

    }
    return JsonResponse.success();
  }

}
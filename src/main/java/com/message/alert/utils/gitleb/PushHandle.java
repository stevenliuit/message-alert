package com.message.alert.utils.gitleb;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.message.alert.utils.GitEmoji;
import com.message.alert.utils.OkHttpUtils;
import static com.message.alert.utils.MapUtils.map;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;


@Slf4j
public class PushHandle {

  public static void handle(PushEvent pushEvent, String token) throws MalformedURLException {
    String webUrl = pushEvent.getProject().getWebUrl();
    URL gitlabUrl = new URL(webUrl);
    String gitlabHost = gitlabUrl.getProtocol() + "://" + gitlabUrl.getHost();

    String userName = pushEvent.getUserName();
    String userUrl = gitlabHost + "/" + pushEvent.getUserUsername();

    String branchName = pushEvent.getRef().replaceAll("refs/heads/", "");
    String branchHistoryUrl = String.format(
      "%s/-/commits/%s",
      pushEvent.getProject().getWebUrl(),
      branchName
    );

    List<PushEvent.CommitsDTO> commits = pushEvent.getCommits();

    // 反转提交历史
    Collections.reverse(commits);

    // 过多的话，只取前10
    List<PushEvent.CommitsDTO> sendCommits;
    if (commits.size() > 10) {
      sendCommits = commits.subList(0, 10);
    } else {
      sendCommits = commits;
    }

    List<List<Map<String, Object>>> commitMsgList = new ArrayList<>();
    for (PushEvent.CommitsDTO commit : sendCommits) {
      String commitMsg = StringUtils.isNotBlank(commit.getTitle()) ? commit.getTitle() : commit.getMessage();
      commitMsg = GitEmoji.replaceEmoji(commitMsg);

      commitMsgList.add(Arrays.asList(
        map(
          "tag", "a",
          "text", commit.getId().substring(0, 9),
          "href", commit.getUrl()
        ), map(
          "tag", "text",
          "text", ": " + commitMsg
        )
      ));
    }

    List<List<Map<String, Object>>> lists = Arrays.asList(
      Arrays.asList(
        map(
          "tag", "a",
          "text", userName,
          "href", userUrl
        ),
        map(
          "tag", "text",
          "text", " 推送了 "
        ),
        map(
          "tag", "a",
          "text", pushEvent.getProject().getName(),
          "href", pushEvent.getProject().getWebUrl()
        ),
        map(
          "tag", "text",
          "text", " 的 "
        ),
        map(
          "tag", "a",
          "text", branchName,
          "href", branchHistoryUrl
        ),
        map(
          "tag", "text",
          "text", "分支"
        )
      ),
      Collections.singletonList(map("tag", "a", "text", ""))
    );

    List<List<Map<String, Object>>> all = new ArrayList<>(lists);
    all.addAll(commitMsgList);

    // 如果 commit 被截断, 发一行 ...
    if (commits.size() > 10) {
      all.add(Collections.singletonList(map("tag", "text", "text", "...")));
    }

    Map<String, Objects> params = map(
      "msg_type", "post",
      "content", map(
        "post", map(
          "zh_cn", map(
            "title", "",
            "content", all
          )
        )
      )
    );

    String json = JSON.toJSONString(params);
    String url = String.format("https://open.feishu.cn/open-apis/bot/v2/hook/%s", token);
    String response = OkHttpUtils.postJson(url, json);
    LarkResponse larkResponse = JSONObject.parseObject(response, LarkResponse.class);
    log.info("发送飞书消息响应: {}", response);

    // 失败告警
    if (larkResponse == null || !Objects.equals(larkResponse.getStatusCode(), 0)) {
      String warn = JSON.toJSONString(map("text", "有消息推送失败 response:" + response));
      OkHttpUtils.postJson(url, warn);
    }
  }

  @NoArgsConstructor
  @Data
  public static class LarkResponse {
    @JsonProperty("StatusCode")
    private Integer statusCode;
    @JsonProperty("StatusMessage")
    private String statusMessage;
  }
}

package com.message.alert.utils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson2.JSONB;
import com.alibaba.fastjson2.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class GitEmoji {

  private static final Map<String, String> enEmojiMap = new HashMap<>();

  static {
    try {
      InputStream resourceAsStream = GitEmoji.class.getClassLoader().getResourceAsStream("git/gitmojis.json");
      assert resourceAsStream != null;
      String jsonString = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);

      Emoji emoji = JSONObject.parseObject(jsonString, Emoji.class);

      for (Emoji.GitmojisBean bean : emoji.getGitmojis()) {
        enEmojiMap.put(bean.getCode(), bean.getEmoji());
      }
    } catch (Exception e) {
      log.error("加载 gitmojis.json 异常", e);
    }
  }

  public static String replaceEmoji(String string) {
    if (string == null) {
      return string;
    }

    for (Map.Entry<String, String> entry : enEmojiMap.entrySet()) {
      string = string.replaceAll(entry.getKey(), entry.getValue());
    }

    return string;
  }

  @Data
  @NoArgsConstructor
  public static class Emoji {

    private List<GitmojisBean> gitmojis;

    @Data
    @NoArgsConstructor
    public static class GitmojisBean {
      private String emoji;
      private String entity;
      private String code;
      private String description;
      private String name;
      private Object semver;
    }
  }

}

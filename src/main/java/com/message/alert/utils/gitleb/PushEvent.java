package com.message.alert.utils.gitleb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PushEvent {

  @JsonProperty("object_kind")
  private String objectKind;
  @JsonProperty("event_name")
  private String eventName;
  @JsonProperty("before")
  private String before;
  @JsonProperty("after")
  private String after;
  @JsonProperty("ref")
  private String ref;
  @JsonProperty("checkout_sha")
  private String checkoutSha;
  @JsonProperty("user_id")
  private Integer userId;
  @JsonProperty("user_name")
  private String userName;
  @JsonProperty("user_username")
  private String userUsername;
  @JsonProperty("user_email")
  private String userEmail;
  @JsonProperty("user_avatar")
  private String userAvatar;
  @JsonProperty("project_id")
  private Integer projectId;
  @JsonProperty("project")
  private ProjectDTO project;
  @JsonProperty("repository")
  private RepositoryDTO repository;
  @JsonProperty("commits")
  private List<CommitsDTO> commits;
  @JsonProperty("total_commits_count")
  private Integer totalCommitsCount;

  @NoArgsConstructor
  @Data
  public static class ProjectDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("avatar_url")
    private Object avatarUrl;
    @JsonProperty("git_ssh_url")
    private String gitSshUrl;
    @JsonProperty("git_http_url")
    private String gitHttpUrl;
    @JsonProperty("namespace")
    private String namespace;
    @JsonProperty("visibility_level")
    private Integer visibilityLevel;
    @JsonProperty("path_with_namespace")
    private String pathWithNamespace;
    @JsonProperty("default_branch")
    private String defaultBranch;
    @JsonProperty("homepage")
    private String homepage;
    @JsonProperty("url")
    private String url;
    @JsonProperty("ssh_url")
    private String sshUrl;
    @JsonProperty("http_url")
    private String httpUrl;
  }

  @NoArgsConstructor
  @Data
  public static class RepositoryDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("description")
    private String description;
    @JsonProperty("homepage")
    private String homepage;
    @JsonProperty("git_http_url")
    private String gitHttpUrl;
    @JsonProperty("git_ssh_url")
    private String gitSshUrl;
    @JsonProperty("visibility_level")
    private Integer visibilityLevel;
  }

  @NoArgsConstructor
  @Data
  public static class CommitsDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("message")
    private String message;
    @JsonProperty("title")
    private String title;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("url")
    private String url;
    @JsonProperty("author")
    private AuthorDTO author;
    @JsonProperty("added")
    private List<String> added;
    @JsonProperty("modified")
    private List<String> modified;
    @JsonProperty("removed")
    private List<?> removed;

    @NoArgsConstructor
    @Data
    public static class AuthorDTO {
      @JsonProperty("name")
      private String name;
      @JsonProperty("email")
      private String email;
    }
  }
}

package com.example.githubapiserver.webhook_models.push;

import lombok.Getter;
import lombok.NoArgsConstructor;

// Owner class
@Getter
@NoArgsConstructor
public class GitHubOwner {
    private String name;
    private String email;
    private String login;
    private long id;
    private String node_id;
    private String avatar_url;
    private String url;
    private String html_url;
}

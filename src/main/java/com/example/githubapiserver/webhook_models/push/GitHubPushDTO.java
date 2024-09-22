package com.example.githubapiserver.webhook_models.push;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GitHubPushDTO {
    private String ref;
    private String before;
    private String after;
    private GitHubRepository gitHubRepository;
    private GitHubPusher pusher;
    private GitHubSender sender;
    private boolean created;
    private boolean deleted;
    private boolean forced;
    private String baseRef;
    private String compare;
    private List<GitHubCommit> commits;
    private GitHubCommit head_commit;
}


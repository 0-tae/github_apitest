package com.example.githubapiserver.domain.webhook.webhook_models.push;

import lombok.Getter;
import lombok.NoArgsConstructor;

// Committer class
@Getter
@NoArgsConstructor
public class GitHubCommitter {
    private String name;
    private String email;
    private String username;
}

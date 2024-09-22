package com.example.githubapiserver.webhook_models.push;

import lombok.Getter;
import lombok.NoArgsConstructor;

// Pusher class
@Getter
@NoArgsConstructor
public class GitHubPusher {
    private String name;
    private String email;
}

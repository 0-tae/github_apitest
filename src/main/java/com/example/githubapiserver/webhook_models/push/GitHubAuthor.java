package com.example.githubapiserver.webhook_models.push;

import lombok.Getter;
import lombok.NoArgsConstructor;

// Author class
@Getter
@NoArgsConstructor
public class GitHubAuthor {
    private String name;
    private String email;
    private String username;
}

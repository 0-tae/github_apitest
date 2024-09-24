package com.example.githubapiserver.domain.webhook.webhook_models.issue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GitHubReactions {
    private String url;
    private int total_count;
    private int plus_one;
    private int minus_one;
    private int laugh;
    private int hooray;
    private int confused;
    private int heart;
    private int rocket;
    private int eyes;
}

package com.example.githubapiserver.webhook_models.issue;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GitHubIssueDTO {
    private String action;
    private GitHubIssueDetail issue;
    private GitHubRepository repository;
    private GitHubSender sender;
}

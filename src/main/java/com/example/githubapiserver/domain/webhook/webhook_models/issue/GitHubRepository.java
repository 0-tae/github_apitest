package com.example.githubapiserver.domain.webhook.webhook_models.issue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GitHubRepository {
    private long id;
    private String node_id;
    private String name;
    private String full_name;
    private boolean is_private;
    private GitHubUser owner;
    private String html_url;
    private String url;
    private int open_issues_count;
    private boolean allow_forking;
    private String visibility;
    private int forks;
    private int open_issues;
    private int watchers;
    private String default_branch;
}

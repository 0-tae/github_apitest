package com.example.githubapiserver.webhook_models.push;

import lombok.Getter;
import lombok.NoArgsConstructor;

// Repository class
@Getter
@NoArgsConstructor
public class GitHubRepository {
    private long id;
    private String node_id;
    private String name;
    private String full_name;
    private boolean is_private;
    private GitHubOwner owner;
    private String html_url;
    private String description;
    private boolean fork;
    private String url;
    private String git_url;
    private String ssh_url;
    private String clone_url;
    private String svn_url;
    private boolean has_issues;
    private boolean has_projects;
    private boolean has_downloads;
    private boolean has_wiki;
    private boolean has_pages;
    private boolean has_discussions;
    private int forks_count;
    private boolean archived;
    private boolean disabled;
    private int open_issues_count;
    private boolean allow_forking;
    private boolean is_template;
    private boolean web_commit_signoff_required;
    private String visibility;
    private String default_branch;
    private String master_branch;
    private int forks;
    private int open_issues;
    private int watchers;
    private int stargazers;
}

package com.example.githubapiserver.comment_bot;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GitHubServerRepoArgs {

    OWNER("0-tae"),
    REPO_NAME("github_apitest");

    private final String args;
}

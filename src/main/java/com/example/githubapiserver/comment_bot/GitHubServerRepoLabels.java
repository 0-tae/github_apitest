package com.example.githubapiserver.comment_bot;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GitHubServerRepoLabels {

    REGISTER("register");
    private final String labelName;
}

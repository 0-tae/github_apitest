package com.example.githubapiserver.domain.comment_bot;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GitHubServerRepoEvents {

    ISSUE("issues"),
    PUSH("push");

    private final String event;
    public static GitHubServerRepoEvents resolveEvent(String actionName){
        try{
            return GitHubServerRepoEvents.valueOf(actionName);
        } catch(IllegalArgumentException e){
            return null;
        }
    }
}

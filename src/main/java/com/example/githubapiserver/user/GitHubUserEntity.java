package com.example.githubapiserver.user;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GitHubUserEntity {
    Integer id;
    String username;
    Long userId;
    String userNodeId;
    String repoUrl;

    public void setRepoUrl(String newRepoUrl){
        this.repoUrl = newRepoUrl;
    }
    public boolean hasRepoUrl(){return this.repoUrl!=null;}
}

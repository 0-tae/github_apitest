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
    String repoName;
    String dirPath;
    String dirName;

    public void setRepoUrl(String newRepoUrl){
        this.repoUrl = newRepoUrl;
    }
    public void setDirPath(String dirPath){
        // TODO: 컨테이너 내의 directory path를 적을것
        String prefix = "CURRENT_DIRECTORY_PATH/";
        this.dirPath = prefix + dirPath;
    }
    public void setDirName(String dirName){
        this.dirName = dirName;
    }
    public void setRepoName(String newRepoName){
        this.repoName = newRepoName;
    }
    public boolean hasRepoUrl(){return this.repoUrl!=null;}
}

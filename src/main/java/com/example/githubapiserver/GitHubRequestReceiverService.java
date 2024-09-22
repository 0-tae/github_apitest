package com.example.githubapiserver;


import com.example.githubapiserver.user.GitHubUserEntity;
import com.example.githubapiserver.webhook_models.issue.GitHubUser;
import org.springframework.stereotype.Service;

@Service
public class GitHubRequestReceiverService {

    public void gitHubUserSave(GitHubUser dto){

//        Salt newSalt =saltRepository.save(
//                Salt.builder()
//                        .salt(Salt.initSalt())
//                        .userId(dto.getId()).build());

        String dirName = "0tae";
                // newSalt.getEncrypt(dto.getId()+dto.getNode_id());

        GitHubUserEntity newUser = GitHubUserEntity.builder()
                .username(dto.getLogin())
                .dirPath(dirName)
                .userId(dto.getId())
                .userNodeId(dto.getNode_id()).build();

        // repository.save(newUser);
    }

    public void saveRepoUrlWithUser(GitHubUserEntity entity, String repoUrl){
        entity.setRepoUrl(repoUrl);
        // repository.save(entity)
    }

    public GitHubUserEntity findByGitHubUser(Long userId, String nodeId){
        // return repository.findByIdAndUserNodeId(dto.getId, dto.getNodeId);

        return null;
    }
}

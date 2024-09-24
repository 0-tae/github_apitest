package com.example.githubapiserver.domain.webhook.reciever;


import com.example.githubapiserver.domain.comment_bot.GitHubApiCallService;
import com.example.githubapiserver.domain.repository.GitHubRepoEntity;
import com.example.githubapiserver.domain.repository.GitHubRepoEntityRepository;
import com.example.githubapiserver.domain.user.GitHubUserEntity;
import com.example.githubapiserver.domain.user.GitHubUserEntityRepository;
import com.example.githubapiserver.domain.webhook.scripts.ScriptExecutor;
import com.example.githubapiserver.domain.webhook.webhook_models.issue.GitHubIssueDTO;
import com.example.githubapiserver.domain.webhook.webhook_models.issue.GitHubIssueDetail;
import com.example.githubapiserver.domain.webhook.webhook_models.issue.GitHubLabel;
import com.example.githubapiserver.domain.webhook.webhook_models.issue.GitHubUser;
import com.example.githubapiserver.domain.webhook.webhook_models.push.GitHubPushDTO;
import com.example.githubapiserver.domain.webhook.webhook_models.push.GitHubSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GitHubRequestReceiverService {

    private final GitHubApiCallService gitHubApiCallService;
    private final GitHubUserEntityRepository gitHubUserEntityRepository;
    private final GitHubRepoEntityRepository gitHubRepoEntityRepository;
    private final ScriptExecutor scriptExecutor;

    public void processIssue(GitHubIssueDTO gitHubIssueDTO) {
        if (gitHubIssueDTO.getAction() == "labeled") {
            GitHubIssueDetail issueDetail = gitHubIssueDTO.getIssue();
            for (GitHubLabel label : issueDetail.getLabels()) {
                if (label.getName() == "register") {
                    GitHubUserEntity gitHubUserEntity = gitHubUserSave(issueDetail.getUser());
                    gitHubApiCallService.createCommentApiCall(issueDetail.getComments_url(),
                            gitHubUserEntity.getUsername() + "님의 등록이 완료 되었습니다!\n");
                }
            }
        }
    }

    public void processPush(GitHubPushDTO gitHubPushDTO) throws IOException {
        // TODO: 유저 확인하기
        GitHubSender pushedUser = gitHubPushDTO.getSender();
        GitHubUserEntity gitHubUser =
                findByGitHubSender(pushedUser);

        // 해당 레포지토리가 존재하면 가져오고, 아니면 새로 생성하기
        String pushedRepoUrl = gitHubPushDTO.getGitHubRepository().getHtml_url();
        GitHubRepoEntity gitHubRepoEntity =
                findByRepoUrl(pushedRepoUrl)
                        .orElseGet(() -> {
                                    GitHubRepoEntity newGitHubRepoEntity =
                                            GitHubRepoEntity.builder()
                                                    .dirName(gitHubPushDTO.getGitHubRepository().getFull_name().replace("/", "_"))
                                                    .repoUrl(pushedRepoUrl)
                                                    .repoName(gitHubPushDTO.getGitHubRepository().getFull_name())
                                                    .gitHubUser(gitHubUser).build();

                                    // TODO: 쉘 스크립트로 지정된 경로를 통해 git clone 다운 받기
                                    /*
                                     * git clone "$1" # 깃허브 레포지토리 클론
                                     * ls
                                     * mv "$2" "$3" # 디렉토리 이름을 고유 이름으로 변경
                                     * */
                                    scriptExecutor.execute(
                                            "init_directory.sh", // 실행할 스크립트
                                            newGitHubRepoEntity.getRepoUrl(), // push된 GitHub Repository의 URL
                                            newGitHubRepoEntity.getDirName() // 클론한 디렉토리를 고유이름으로 저장
                                    );

                                    return newGitHubRepoEntity;
                                }
                        );

        // TODO: 레포지토리 동기화
        /*
        * ls
        * cd "$SYSTEM_GITHUB_REPOS_PATH/$1" || echo "directory not found: $1" && exit # cd /{저장 중인 위치의 디렉토리}/{요청 유저의 디렉토리}
        * git pull "$3" "$4" # git pull {remote_repo_name} {remote_branch}
        * */
        scriptExecutor.execute(
                "sync_directory.sh",
                gitHubRepoEntity.getDirName(),
                "origin",
                gitHubPushDTO.getGitHubRepository().getMaster_branch() // push한 branch 확인
        );

        // TODO: webhoook message의 커밋을 확인해서, added된 파일을 읽어오기 (리스트 일괄처리)
        List<String> addedFilePath = gitHubPushDTO.getHead_commit().getAdded();
        for (String filePath : addedFilePath) {
            // TODO: added된 파일 경로 읽어오기, 해당 파일을 텍스트화 하기
            BufferedReader br = new BufferedReader(new FileReader(
                    System.getenv("SYSTEM_GITHUB_REPOS_PATH") + "/"+filePath));
            String line;

            StringBuilder java2textBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                java2textBuilder.append(line).append("\n");
            }


            // TODO: GPT API에 쏘기
            // TODO: GPT API의 응답을 solution-{NUM}.md 파일에 write하기 (write 경로는 커밋한 파일의 디렉토리 내에 생성, 마지막 토큰의 바로 이전 경로)
            // TODO: 레포지토리에 커밋 하기 (created solution-9906)
        }

        // TODO: (output) 사용자 레포지토리에 callback push 하기 (혹은 pull request api 날리기)
    }

    public GitHubUserEntity gitHubUserSave(GitHubUser dto) {
        GitHubUserEntity newUser = GitHubUserEntity.builder()
                .username(dto.getLogin())
                .userId(dto.getId())
                .userNodeId(dto.getNode_id()).build();

        return gitHubUserEntityRepository.save(newUser);
    }

    public GitHubUserEntity findByGitHubSender(GitHubSender gitHubUser) {
        return gitHubUserEntityRepository.findByUserIdAndUserNodeId(gitHubUser.getId(), gitHubUser.getNode_id())
                .orElseThrow(() -> new RuntimeException("not found user Id"));
    }

    public Optional<GitHubRepoEntity> findByRepoUrl(String repoName) {
        return gitHubRepoEntityRepository.findByRepoName(repoName);
    }

    public List<GitHubRepoEntity> findAllByGitHubUserEntity(GitHubUserEntity gitHubUserEntity) {
        return gitHubRepoEntityRepository.findAllByGitHubUser(gitHubUserEntity);
    }
}

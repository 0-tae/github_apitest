package com.example.githubapiserver;


import com.example.githubapiserver.comment_bot.GitHubServerRepoEvents;
import com.example.githubapiserver.scripts.ScriptExecutor;
import com.example.githubapiserver.user.GitHubUserEntity;
import com.example.githubapiserver.webhook_models.issue.GitHubIssueDTO;
import com.example.githubapiserver.webhook_models.issue.GitHubIssueDetail;
import com.example.githubapiserver.webhook_models.issue.GitHubUser;
import com.example.githubapiserver.webhook_models.push.GitHubPushDTO;
import com.example.githubapiserver.webhook_models.push.GitHubSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/github_request")
@Slf4j
@RequiredArgsConstructor
public class GitHubRequestReceiverController {

    private final GitHubRequestReceiverService gitHubRequestReceiverService;
    private final ScriptExecutor scriptExecutor;

    @PostMapping("/webhook/server")
    @Operation(summary = "GitHub Issue에서 register 라벨을 붙인 이슈 업로더의 정보를 DB에 등록합니다.", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid User Token supplied"),
            @ApiResponse(responseCode = "404", description = "User token NotFound")})
    public ResponseEntity register(@RequestBody GitHubIssueDTO webhookRequest, HttpServletRequest request) {
        String event = request.getHeader("X-GitHub-Event");

        // 만약, X-GitHub-Event가 비어있거나, issue 이벤트가 아니라면 처리하지 않는다.
        if(event == null || GitHubServerRepoEvents.resolveEvent(event) !=
                GitHubServerRepoEvents.ISSUE){
            return ResponseEntity.ok("event is not correct");
        }

        // TODO: 유저 등록하기 (repository는 사용 시 등록)
        GitHubIssueDetail issueDetail = webhookRequest.getIssue();
        GitHubUser givenUser = issueDetail.getUser();
        gitHubRequestReceiverService.gitHubUserSave(issueDetail.getUser());

        // TODO: 이슈봇 댓글 달기
        String message = givenUser.getLogin()+"님의 등록이 완료 되었습니다!\n";

        // git API를 통해 메시지 보내기

        return ResponseEntity.ok("ok");
    }


    @PostMapping("/webhook/client")
    @Operation(summary = "GitHub push 이벤트 확인 후, solution_{PROB_NUM}.md 파일 반환",
            description = "GitHub push 이벤트를 확인하고, 서버 내 해당 레포지토리와 동기화합니다. " +
                    "이후 동기화 된 파일을 LLM 서버에 전송하여 값을 받아옵니다.")
    public ResponseEntity push(@RequestBody GitHubPushDTO webhookRequest, HttpServletRequest request) throws IOException {
        String event = request.getHeader("X-GitHub-Event");

        // 만약, X-GitHub-Event가 비어있거나, push 이벤트가 아니라면 처리하지 않는다.
        if(event == null || GitHubServerRepoEvents.resolveEvent(event) !=
                GitHubServerRepoEvents.PUSH){
            return ResponseEntity.ok("event is not correct");
        }

        // TODO: 유저 확인하기
        GitHubSender pushedUser = webhookRequest.getSender();
        GitHubUserEntity gitHubUser =
                gitHubRequestReceiverService.findByGitHubUser(pushedUser.getId(), pushedUser.getNode_id());

        // 해당 유저의 레포지토리 경로가 있는지 확인
        if(!gitHubUser.hasRepoUrl()){

            // TODO: 유저 엔티티에 원격 저장소 url 저장
            gitHubUser.setRepoUrl(webhookRequest.getGitHubRepository().getHtml_url());
            gitHubUser.setRepoName(webhookRequest.getGitHubRepository().getFull_name());
            gitHubUser.setDirName(gitHubUser.getRepoName().replace("/","_"));
            // TODO: 쉘 스크립트로 지정된 경로를 통해 git clone 다운 받기
            scriptExecutor.execute(
                    "init_directory.sh", // 실행할 스크립트
                    gitHubUser.getRepoUrl(), // push된 GitHub Repository의 URL
                    webhookRequest.getGitHubRepository().getName(), // push된 GitHub Repository의 이름
                    gitHubUser.getDirName() // 클론한 디렉토리를 고유이름으로 저장
            );
        }

        // TODO: cd /{요청 유저의 디렉토리} 스크립트
        // TODO: git pull {remote_repo_name} {remote_branch}스크립트
        scriptExecutor.execute(
           "sync_directory.sh",
                gitHubUser.getDirName(),
                "origin",
                webhookRequest.getGitHubRepository().getMaster_branch()
        );

        // TODO: webhoook message의 커밋을 확인해서, added된 파일을 읽어오기 (리스트 일괄처리)
        List<String> addedFilePath = webhookRequest.getHead_commit().getAdded();
        for(String filePath: addedFilePath){
            // TODO: added된 파일 경로 읽어오기, 해당 파일을 텍스트화 하기
            BufferedReader br=new BufferedReader(new FileReader(System.getenv("SYSTEM_GITHUB_REPOS_PATH") + filePath));
            String line;
            StringBuilder java2textBuilder = new StringBuilder();
            while((line=br.readLine())!=null){
                java2textBuilder.append(line).append("\n");
            }

            // TODO: GPT API에 쏘기
            // TODO: GPT API의 응답을 solution.md 파일에 write하기 (write 경로는 커밋한 파일의 디렉토리 내에 생성, 마지막 토큰의 바로 이전 경로)
        }


        // TODO: (output) 사용자 레포지토리에 callback push 하기 (혹은 pull request api 날리기)
        return ResponseEntity.ok("ok");
    }
}

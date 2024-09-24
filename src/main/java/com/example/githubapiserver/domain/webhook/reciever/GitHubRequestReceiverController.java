package com.example.githubapiserver.domain.webhook.reciever;


import com.example.githubapiserver.domain.comment_bot.GitHubServerRepoEvents;
import com.example.githubapiserver.domain.webhook.scripts.ScriptExecutor;
import com.example.githubapiserver.domain.webhook.webhook_models.issue.GitHubIssueDTO;
import com.example.githubapiserver.domain.webhook.webhook_models.push.GitHubPushDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    public ResponseEntity register(@RequestBody Object webhookRequest, HttpServletRequest request) throws IOException {

        // 만약, X-GitHub-Event가 비어있거나, issue 이벤트가 아니라면 처리하지 않는다.
        switch (GitHubServerRepoEvents.resolveEvent(request.getHeader("X-GitHub-Event"))){
            case ISSUE:
                GitHubIssueDTO gitHubIssueDTO= new ObjectMapper().convertValue(webhookRequest, GitHubIssueDTO.class);
                gitHubRequestReceiverService.processIssue(gitHubIssueDTO);
                break;
            case PUSH:
                GitHubPushDTO gitHubPushDTO = new ObjectMapper().convertValue(webhookRequest, GitHubPushDTO.class);
                gitHubRequestReceiverService.processPush(gitHubPushDTO);
                break;
            default:
                return ResponseEntity.ok("can't handling the event: "+request.getHeader("X-GitHub-Event"));
        }

        return ResponseEntity.ok("ok");
    }


    @PostMapping("/webhook/client")
    @Operation(summary = "GitHub push 이벤트 확인 후, solution_{PROB_NUM}.md 파일 반환",
            description = "GitHub push 이벤트를 확인하고, 서버 내 해당 레포지토리와 동기화합니다. " +
                    "이후 동기화 된 파일을 LLM 서버에 전송하여 값을 받아옵니다.")
    public ResponseEntity push(@RequestBody GitHubPushDTO webhookRequest, HttpServletRequest request) throws IOException {

        return ResponseEntity.ok("ok");
    }
}

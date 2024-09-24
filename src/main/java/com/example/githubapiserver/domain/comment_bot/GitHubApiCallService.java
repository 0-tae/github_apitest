package com.example.githubapiserver.domain.comment_bot;


import com.example.githubapiserver.domain.webhook.webhook_models.api.GitHubCreateCommentRequestApiDto;
import com.example.githubapiserver.domain.webhook.webhook_models.api.GitHubCreateCommentResponseApiDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubApiCallService {
    public GitHubCreateCommentResponseApiDto createCommentApiCall(String url, String message){
        // TODO: git API를 통해 메시지 보내기
        return new RestTemplate().postForObject(url, new GitHubCreateCommentRequestApiDto(message), GitHubCreateCommentResponseApiDto.class);
    }
}

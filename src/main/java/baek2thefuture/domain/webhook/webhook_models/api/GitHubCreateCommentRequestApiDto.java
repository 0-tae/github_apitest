package baek2thefuture.domain.webhook.webhook_models.api;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GitHubCreateCommentRequestApiDto {
    String body;
}

package baek2thefuture.domain.webhook.webhook_models.api;


import baek2thefuture.domain.webhook.webhook_models.issue.GitHubUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GitHubCreateCommentResponseApiDto {
        private Long id;
        private String node_id;
        private String url;
        private String htmlUrl;
        private String body;
        private GitHubUser user;
        private String created_at;
        private String updated_at;
        private String issueUrl;
        private String author_association;
}

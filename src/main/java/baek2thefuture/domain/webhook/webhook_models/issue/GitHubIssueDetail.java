package baek2thefuture.domain.webhook.webhook_models.issue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GitHubIssueDetail {
    private String url;
    private String repository_url;
    private String labels_url;
    private String comments_url;
    private String events_url;
    private String html_url;
    private long id;
    private String node_id;
    private int number;
    private String title;
    private GitHubUser user;
    private List<GitHubLabel> labels;
    private String state;
    private boolean locked;
    private String milestone;
    private int comments;
    private String created_at;
    private String updated_at;
    private String closed_at;
    private String author_association;
    private GitHubReactions reactions;
}

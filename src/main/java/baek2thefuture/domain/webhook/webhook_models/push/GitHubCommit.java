package baek2thefuture.domain.webhook.webhook_models.push;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// Commit class
@Getter
@NoArgsConstructor
public class GitHubCommit {
    private String id;
    private String treeId;
    private boolean distinct;
    private String message;
    private String timestamp;
    private String url;
    private GitHubAuthor author;
    private GitHubCommitter committer;
    private List<String> added;
    private List<String> removed;
    private List<String> modified;
}

package com.example.githubapiserver.webhook_models.issue;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GitHubLabel {
    private long id;
    private String node_id;
    private String url;
    private String name;
    private String color;
    private boolean is_default;
    private String description;
}

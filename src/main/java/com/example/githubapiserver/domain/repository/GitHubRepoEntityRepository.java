package com.example.githubapiserver.domain.repository;

import com.example.githubapiserver.domain.user.GitHubUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GitHubRepoEntityRepository extends JpaRepository<GitHubRepoEntity,Integer> {
    List<GitHubRepoEntity> findAllByGitHubUser(GitHubUserEntity gitHubUser);
    Optional<GitHubRepoEntity> findByRepoName(String repoName);
}

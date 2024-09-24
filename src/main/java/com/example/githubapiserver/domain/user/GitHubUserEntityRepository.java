package com.example.githubapiserver.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GitHubUserEntityRepository extends JpaRepository<GitHubUserEntity,Integer> {
    Optional<GitHubUserEntity> findByUserIdAndUserNodeId(Long userId, String nodeId);
}

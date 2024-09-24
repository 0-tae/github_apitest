package com.example.githubapiserver.domain.repository;


import com.example.githubapiserver.domain.user.GitHubUserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table
@Entity
@SoftDelete(columnName = "deleted_at")
@EntityListeners(AuditingEntityListener.class)  // Auditing 기능을 활성화
public class GitHubRepoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String repoUrl;

    @Column(unique = true)
    private String repoName;

    @Column(unique = true)
    private String dirName;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private GitHubUserEntity gitHubUser;
}

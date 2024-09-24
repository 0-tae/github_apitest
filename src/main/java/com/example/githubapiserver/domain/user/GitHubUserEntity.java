package com.example.githubapiserver.domain.user;


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
public class GitHubUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @Column(unique = true)
    private Long userId;

    @Column(unique = true)
    private String userNodeId;
}

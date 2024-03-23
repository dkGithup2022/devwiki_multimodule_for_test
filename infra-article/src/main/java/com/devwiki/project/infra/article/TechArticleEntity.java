package com.devwiki.project.infra.article;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tech_article")
public class TechArticleEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false, length = 1024)
    @Setter
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Setter
    private String content;

    @Builder.Default
    @Column(nullable = false)
    private Long likes = 0L;

    @Builder.Default
    @Column(nullable = false)
    private Long dislikes = 0L;

    @Builder.Default
    @Column(nullable = false)
    private Long views = 0L;

    @Builder.Default
    @Column(nullable = false)
    private Long comments = 0L;

}
package com.devwiki.project.core.infra.rdms.article;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleEntity {
    @Id
    @GeneratedValue
    private Long id ;
    private Long author ;
    private String title;
    private String content;

}

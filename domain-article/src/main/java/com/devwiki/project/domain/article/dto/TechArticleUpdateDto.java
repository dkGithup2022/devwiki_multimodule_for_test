package com.devwiki.project.domain.article.dto;


public record TechArticleUpdateDto(
        long articleId
        , String title,
        String content) {
}

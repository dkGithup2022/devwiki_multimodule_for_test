package com.devwiki.project.domain.article.dto;


public record TechArticleCreateDto(
        long authorId,
        String title,
        String content
) {
}
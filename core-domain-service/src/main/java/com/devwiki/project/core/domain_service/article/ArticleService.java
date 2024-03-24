package com.devwiki.project.core.domain_service.article;

import com.devwiki.project.core.domain.article.Article;
import com.devwiki.project.core.domain_service.article.dto.CreateArticleDto;
import com.devwiki.project.core.domain_service.article.dto.UpdateArticleDto;

public interface ArticleService {

    Article createArticle(CreateArticleDto dto);

    Article updateArticle(UpdateArticleDto dto);

    void deleteArticle(Long articleId);

    Article getArticle(Long articleId);
}

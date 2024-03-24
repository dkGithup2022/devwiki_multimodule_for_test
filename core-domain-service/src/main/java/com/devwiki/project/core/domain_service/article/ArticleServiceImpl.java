package com.devwiki.project.core.domain_service.article;

import com.devwiki.project.core.domain.article.Article;
import com.devwiki.project.core.domain.article.TechArticle;
import com.devwiki.project.core.domain_service.article.dto.CreateArticleDto;
import com.devwiki.project.core.domain_service.article.dto.UpdateArticleDto;
import com.devwiki.project.core.infra.rdms.article.ArticleEntity;
import com.devwiki.project.core.infra.rdms.article.ArticleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    // infra layer 를 직접 땡겨쓸지 , 중간 port 인터페이스를 둘 지는 리서치가 필요.
    // 개인적으로  포트 레이어가 있는 것을 선호
    private final ArticleJpaRepository articleJpaRepository;
    @Override
    public Article createArticle(CreateArticleDto dto) {
        var article = TechArticle.builder()
                .authorId(dto.author())
                .title(dto.title())
                .content(dto.content()).build();

        article.validate();

        articleJpaRepository.save(ArticleEntity.builder().id(null)
                .author(article.getAuthorId())
                .content(article.getContent())
                .title(article.getTitle()).build());

        return article;
    }

    @Override
    public Article updateArticle(UpdateArticleDto dto) {
        var articleEntity = articleJpaRepository.findById(dto.articleId()).orElseThrow(RuntimeException::new);
        return null;
    }

    @Override
    public void deleteArticle(Long articleId) {
    }

    @Override
    public Article getArticle(Long articleId) {

        return null;
    }
}

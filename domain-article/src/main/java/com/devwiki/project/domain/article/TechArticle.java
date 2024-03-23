package com.devwiki.project.domain.article;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TechArticle {
    private final long articleId;

    private final Author author;

    private final String title;

    private final Content content;

    private final Popularity popularity;

    public void validate() {
        if (title == null || title.isBlank())
            throw new RuntimeException("this is bad title for article ");
        content.validate();
        author.validate();
        popularity.validate();
    }

    public TechArticle(long articleId, Author author, String title, Content content, Popularity popularity) {
        this.articleId = articleId;
        this.author = author;
        this.title = title;
        this.content = content;
        this.popularity = popularity;
    }
}
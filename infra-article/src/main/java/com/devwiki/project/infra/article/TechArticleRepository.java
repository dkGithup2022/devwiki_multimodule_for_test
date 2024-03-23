package com.devwiki.project.infra.article;

import java.util.Optional;

public interface TechArticleRepository {

    Optional<TechArticleEntity> findById(long id);

    TechArticleEntity save(TechArticleEntity techArticleEntity);

    long deleteById(long id);
}

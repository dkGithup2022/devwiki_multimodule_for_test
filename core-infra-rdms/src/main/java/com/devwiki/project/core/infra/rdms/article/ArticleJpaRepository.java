package com.devwiki.project.core.infra.rdms.article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJpaRepository extends JpaRepository<ArticleEntity, Long> {
}

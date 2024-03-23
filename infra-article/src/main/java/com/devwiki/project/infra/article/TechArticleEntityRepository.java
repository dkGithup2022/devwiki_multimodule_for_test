package com.devwiki.project.infra.article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TechArticleEntityRepository extends JpaRepository<TechArticleEntity, Long >, TechArticleRepository {
}
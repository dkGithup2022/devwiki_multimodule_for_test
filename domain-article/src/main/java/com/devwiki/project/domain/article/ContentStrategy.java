package com.devwiki.project.domain.article;

import java.util.function.Predicate;

public interface ContentStrategy {
    Predicate<Content> validateContent();
}
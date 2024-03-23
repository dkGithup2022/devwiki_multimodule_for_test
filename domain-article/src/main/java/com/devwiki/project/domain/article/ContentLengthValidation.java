package com.devwiki.project.domain.article;

import java.util.function.Predicate;

public class ContentLengthValidation implements ContentStrategy {

    private static final int CONTENT_MAX_LENGTH = 1024 * 1024 * 10; // 10MB

    @Override
    public Predicate<Content> validateContent() {
        return (content) -> validate(content);
    }

    private boolean validate(Content content) {
        if (content.content() == null || content.content().length() <= 0)
            return false;
        if (content.content().length() >= CONTENT_MAX_LENGTH)
            return false;
        return true;

    }

}
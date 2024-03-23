package com.devwiki.project.domain.article;

import java.util.List;

public record Content(List<ContentStrategy> contentStrategies, String content) {
    public Content newAndGet(String content) {
        return new Content(this.contentStrategies, content);
    }

    public void validate() {
        contentStrategies.stream()
                .forEach(strategy -> {
                    var pass = strategy.validateContent().test(this);
                    if (!pass)
                        throw new RuntimeException("CONTENT VALIDATION FAIL");
                });
    }


}
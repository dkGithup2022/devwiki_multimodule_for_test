package com.devwiki.project.core.domain.comment;

import com.devwiki.project.core.domain.article.TechArticle;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Builder
@Getter
public class NormalComment implements Comment {

    private Long articleId;
    private Long authorId;
    private String content;

    private static final List<Function<NormalComment, Boolean>> validationStrategies = new ArrayList<>();

    static {
        validationStrategies.add(comment -> comment.authorId != null && comment.articleId != null);
        validationStrategies.add(comment -> comment.getContent() != null && comment.getContent().length() <= 1024 * 10 && !comment.getContent().trim().isEmpty()); // 내용 길이 검증
    }

    @Override
    public void validate() {
        validationStrategies.stream()
                .forEach(strategy -> {
                    if (strategy.apply(this) == false)
                        throw new RuntimeException("유효하지 않은 객체");
                });
    }
}

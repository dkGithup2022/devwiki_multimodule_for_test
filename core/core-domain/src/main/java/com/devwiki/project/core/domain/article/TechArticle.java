package com.devwiki.project.core.domain.article;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Builder
@Getter
public class TechArticle implements Article {

    // 검증 전략을 관리하는 리스트 -> 리팩토링 포인트, 각각의 전략을 스태틱 변수로 가져오는게 더 좋아보임 ㅇ ㅇ
    private static final List<Function<TechArticle, Boolean>> validationStrategies = new ArrayList<>();

    static {
        validationStrategies.add(article -> article.getTitle() != null && !article.getTitle().trim().isEmpty()); // 제목 검증
        validationStrategies.add(article -> article.getContent() != null && article.getContent().length() <= 1024 * 1024 * 10 && !article.getContent().trim().isEmpty()); // 내용 길이 검증
    }

    private Long authorId;
    private String content;
    private String title;


    @Override
    public void validate() {
        validationStrategies.stream()
                .forEach(strategy -> {
                    if (strategy.apply(this) == false)
                        throw new RuntimeException("유효하지 않은 객체");
                });
    }
}

/*
* import java.util.function.Function;

public class ArticleValidationStrategies {

    public static final Function<TechArticle, Boolean> validateTitleNotEmpty =
        article -> article.getTitle() != null && !article.getTitle().trim().isEmpty();

    public static final Function<TechArticle, Boolean> validateContentLengthAndNotEmpty =
        article -> article.getContent() != null && article.getContent().length() <= 1024*1024*10 && !article.getContent().trim().isEmpty();


}

*
* */

package com.devwiki.project.app.web.api;

import com.devwiki.project.domain.article.TechArticle;
import com.devwiki.project.domain.article.dto.TechArticleCreateDto;
import com.devwiki.project.domain.article.service.TechArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/article")
@RequiredArgsConstructor
public class TechArticleController {


    private final TechArticleService techArticleService;

    @GetMapping("/tech")
    public ResponseEntity<TechArticle> getTechArticle(@RequestParam long id) {
        var article = techArticleService.read(id);
        return ResponseEntity.ok(article);
    }

    @PostMapping("/tech")
    public ResponseEntity<Long> createTechArticle(@RequestBody TechArticleCreateDto createDto) {
        var articleId = techArticleService.create(createDto);
        return ResponseEntity.ok(articleId);
    }


}
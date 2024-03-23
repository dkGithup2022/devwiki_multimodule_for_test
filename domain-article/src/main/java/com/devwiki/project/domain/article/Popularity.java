package com.devwiki.project.domain.article;

import lombok.Builder;

@Builder
public record Popularity(long likes, long dislikes, long comments, long views) {
    public void validate(){

    }
}
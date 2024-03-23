package com.devwiki.project.domain.article;

import lombok.Builder;

@Builder
public record Author(long authorId, String nickname, String userGithubProfile) {
    public void validate(){

    }

}
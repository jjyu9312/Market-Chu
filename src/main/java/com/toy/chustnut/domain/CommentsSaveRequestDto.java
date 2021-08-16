package com.toy.chustnut.domain;

import com.toy.chustnut.model.Comments;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsSaveRequestDto {

    private String content;

    @Builder
    public CommentsSaveRequestDto(Long productId, String content) {
        this.content = content;
    }

    public Comments toEntity(String userId, String userEmail, Long productId) {
        return Comments.builder()
                .content(content)
                .userId(userId)
                .userEmail(userEmail)
                .productId(productId)
                .build();
    }
}

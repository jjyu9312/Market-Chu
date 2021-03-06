package com.toy.chustnut.model;

import com.sunny.ddangnmarket.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comments extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String userId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String userEmail;

    @Builder
    public Comments(Long productId, String content, String userId, String userEmail) {
        this.productId = productId;
        this.content = content;
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public void update(String content) {
        this.content = content;
    }
}

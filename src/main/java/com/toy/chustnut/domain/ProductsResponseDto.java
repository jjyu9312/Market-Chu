package com.toy.chustnut.domain;

import com.toy.chustnut.model.Products;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String userId;
    private String userEmail;
    private int price;
    private String category;
    private String region;
    private String imageFilePath;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public ProductsResponseDto(Products entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.userId = entity.getUserId();
        this.userEmail = entity.getUserEmail();
        this.price = entity.getPrice();
        this.category = entity.getCategory().getText();
        this.region = entity.getRegion();
        this.status = entity.getStatus().getText();
        this.imageFilePath = entity.getImageFilePath();
        this.createdTime = entity.getCreatedDate();
        this.updatedTime = entity.getModifiedDate();
    }
}

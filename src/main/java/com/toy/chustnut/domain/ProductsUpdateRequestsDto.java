package com.toy.chustnut.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductsUpdateRequestsDto {

    private String title;
    private String content;
    private String category;
    private int price;

    @Builder
    public ProductsUpdateRequestsDto(String title, String content, String category, int price) {

        this.title = title;
        this.content = content;
        this.category = category;
        this.price = price;
    }
}

package com.toy.chustnut.domain;

import com.toy.chustnut.model.Category;
import com.toy.chustnut.model.Products;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;s
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ProductsSaveRequestDto {

    private String title;
    private String content;
    private int price;
    private String cateogory;
    private String region;

    @Setter
    private String imageFilePath;

    @Builder
    public ProductsSaveRequestDto(String title, String content, int price, String cateogory, String region) {

        this.title = title;
        this.content = content;
        this.price = price;
        this.cateogory = cateogory;
        this.region = region;
    }

    public Products toEntity(String userid, String userEmail) {

        return Products.builder()
                .title(title)
                .content(content)
                .userId(userid)
                .userEmail(userEmail)
                .price(price)
                .category(Category.get(cateogory))
                .region(region)
                .imageFilePath(imageFilePath)
                .build();
    }
}

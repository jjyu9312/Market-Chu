package com.toy.chustnut.service;

import com.toy.chustnut.domain.ProductsListResponseDto;
import com.toy.chustnut.domain.ProductsResponseDto;
import com.toy.chustnut.domain.ProductsSaveRequestDto;
import com.toy.chustnut.domain.ProductsUpdateRequestsDto;
import com.toy.chustnut.model.Products;
import com.toy.chustnut.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    // @Transactional - transaction begin, commit을 자동 수행해준다
    //                - 예외를 발생시키면, rollback 처리를 자동 수행해준다.
    @Transactional
    public Long save(ProductsSaveRequestDto requestDto, String userId, String userEmail) {

        return productsRepository.save(requestDto.toEntity(userId, userEmail)).getId();
    }

    @Transactional
    public Long update(Long id, ProductsUpdateRequestsDto requestsDto) {

        Products products = productsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No post. id = " + id));

        products.update(requestsDto.getTitle(), requestsDto.getContent(), requestsDto.getPrice());

        return id;
    }

    @Transactional
    public Long updateStatus(Long id, String statusQueryString) {

        Products products = productsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No post. id = " + id));

        products.updateStatus(statusQueryString);

        return id;
    }

    @Transactional
    public void delete(Long id) {

        Products products = productsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No post. id = " + id));

        productsRepository.delete(products);
    }

    @Transactional(readOnly = true) // faster by using readOnly
    public List<ProductsListResponseDto> findAllDesc() {

        return productsRepository.findAllDesc().stream()
                .map(ProductsListResponseDto::new)
                .collect(Collectors.toList());
    }

    public ProductsResponseDto findById(Long id) {

        Products entity = productsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No post. id = " + id));

        return new ProductsResponseDto(entity);
    }
}
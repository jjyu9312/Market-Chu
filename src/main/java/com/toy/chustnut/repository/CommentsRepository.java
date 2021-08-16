package com.toy.chustnut.repository;

import com.toy.chustnut.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    List<Comments> findAllByProductIdOrderByCreatedDateDesc(Long productId);

    void deleteAllByproductId(Long productId);
}

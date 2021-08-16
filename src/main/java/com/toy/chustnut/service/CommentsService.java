package com.toy.chustnut.service;

import com.toy.chustnut.domain.CommentsListResponseDto;
import com.toy.chustnut.domain.CommentsSaveRequestDto;
import com.toy.chustnut.model.Comments;
import com.toy.chustnut.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;

    @Transactional
    public Long save(CommentsSaveRequestDto requestDto, String userId, String userEmail, Long productId) {

        return commentsRepository.save(requestDto.toEntity(userId, userEmail, productId)).getId();
    }

    @Transactional
    public void delete(Long id) {

        Comments comments = commentsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No comment. id = " + id));

        commentsRepository.delete(comments);
    }

    @Transactional
    public void deleteAllByProductId(Long productId) { commentsRepository.deleteAllByproductId(productId); }

    @Transactional(readOnly = true) // 조회 api 인 경우에는 메소드에 (readOnly = true) 달고 사용
    public List<CommentsListResponseDto> findAllByProductIdDesc(Long productId) {

        return commentsRepository.findAllByProductIdOrderByCreatedDateDesc(productId).stream()
                .map(CommentsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean hasComment(Long id) {

        Comments comments = commentsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No comment. id = " + id));

        return true;
    }
}

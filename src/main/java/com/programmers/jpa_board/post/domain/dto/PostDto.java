package com.programmers.jpa_board.post.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public sealed interface PostDto permits CreatePostRequest, CommonResponse, UpdatePostRequest {
    record CreatePostRequest(
            @NotBlank
            @Size(max = 100, message = "최대 사이즈는 100자입니다.")
            String title,
            String content,
            @NotNull
            @Positive(message = "양수를 입력해주세요.")
            Long userId
    ) {
    }

    record CommonResponse(Long id, String title, String content, Long userId, String createdBy, LocalDateTime createAt) {
    }

    record UpdatePostRequest(
            @NotBlank
            @Size(max = 100, message = "최대 사이즈는 100자입니다.")
            String title,
            String content
    ) {
    }
}

package com.programmers.jpaboard.web.post.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

	private Long id;
	private String title;
	private String content;
	private Long userId;
	private LocalDateTime createdAt;
	private String createdBy;
}

package com.prgrms.devcourse.springjpaboard.domain.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class PostCreateRequestDto {

	@NotNull
	private Long userId;

	@NotBlank
	private String title;

	@NotBlank
	private String content;

}

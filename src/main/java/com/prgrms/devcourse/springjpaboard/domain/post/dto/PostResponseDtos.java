package com.prgrms.devcourse.springjpaboard.domain.post.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDtos {

	private List<PostResponseDto> postResponseDtoList;

	private Long nextCursorId;

	private Boolean hasNext;

	@Builder
	public PostResponseDtos(
		List<PostResponseDto> postResponseDtoList, Long nextCursorId, boolean hasNext) {
		this.postResponseDtoList = postResponseDtoList;
		this.nextCursorId = nextCursorId;
		this.hasNext = hasNext;
	}

}

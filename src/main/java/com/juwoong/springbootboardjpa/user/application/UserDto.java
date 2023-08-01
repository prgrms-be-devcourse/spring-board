package com.juwoong.springbootboardjpa.user.application;

import java.time.LocalDateTime;
import java.util.List;
import com.juwoong.springbootboardjpa.post.domain.Post;
import com.juwoong.springbootboardjpa.user.domain.constant.Hobby;

public record UserDto(Long id, String name, Integer age, Hobby hobby, List<Post> posts, LocalDateTime createdAt,
                      LocalDateTime updatedAt) {
}

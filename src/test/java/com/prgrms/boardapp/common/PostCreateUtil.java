package com.prgrms.boardapp.common;

import com.prgrms.boardapp.model.Post;
import com.prgrms.boardapp.model.User;

public class PostCreateUtil {

    private static Long postId = 1L;

    private PostCreateUtil() {
    }

    public static Post createPost() {
        return Post.builder()
                .title("sample title")
                .content("sample content")
                .build();
    }

    public static Post createPostWithId() {
        return Post.builder()
                .id(postId++)
                .title("sample title")
                .content("sample content")
                .build();
    }

    public static Post createPostWithTitle(String title) {
        return Post.builder()
                .title(title)
                .build();
    }
}

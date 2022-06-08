package com.devcourse.springjpaboard.application.post.converter;

import com.devcourse.springjpaboard.application.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.application.post.model.Post;
import com.devcourse.springjpaboard.application.post.service.dto.PostResponse;
import com.devcourse.springjpaboard.application.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

  public Post convertPostRequest(CreatePostRequest createPostRequest, User user) {
    Post post = new Post();
    post.setTitle(createPostRequest.title());
    post.setContent(createPostRequest.content());
    post.setUser(user);
    return post;
  }

  public PostResponse convertPostResponse(Post post) {
    return new PostResponse(post.getTitle(), post.getContent());
  }

}

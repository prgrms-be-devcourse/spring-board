package com.programmers.epicblues.jpa_board.service;

import com.programmers.epicblues.jpa_board.entity.Post;
import com.programmers.epicblues.jpa_board.entity.User;
import com.programmers.epicblues.jpa_board.repository.JpaPostRepository;
import com.programmers.epicblues.jpa_board.repository.JpaUserRepository;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PostService {

  private final JpaPostRepository postRepository;
  private final JpaUserRepository userRepository;

  public PostService(JpaPostRepository postRepository, JpaUserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  public List<Post> getPosts(PageRequest pageRequest) {

    return postRepository.findAll(pageRequest).getContent();
  }

  public List<Post> getPosts() {
    return postRepository.findAll(Sort.by("createdAt").descending());
  }

  public void createPost(Long userId, String title, String content) {
    User user = userRepository.findById(userId).orElseThrow();
    Post post = Post.builder().content(content).title(title).build();
    post.assignUser(user);

    postRepository.save(post);

  }
}

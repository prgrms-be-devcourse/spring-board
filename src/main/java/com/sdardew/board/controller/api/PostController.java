package com.sdardew.board.controller.api;

import com.sdardew.board.dto.post.CreatePostDto;
import com.sdardew.board.dto.post.DetailedPostDto;
import com.sdardew.board.dto.post.PostDto;
import com.sdardew.board.dto.post.UpdatePostDto;
import com.sdardew.board.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public List<PostDto> getPosts(Pageable pageable) {
    return postService.getPosts(pageable);
  }

  @GetMapping("/{id}")
  public DetailedPostDto getPost(@PathVariable("id") Long id) {
    return postService.getPost(id);
  }

  @PostMapping
  public PostDto createPost(@RequestBody CreatePostDto createPostDto) {
    return postService.createPost(createPostDto);
  }

  @PutMapping("/{id}")
  public PostDto updatePost(@PathVariable("id") Long id, @RequestBody UpdatePostDto updatePostDto) {
    return postService.updatePost(id, updatePostDto);
  }
}

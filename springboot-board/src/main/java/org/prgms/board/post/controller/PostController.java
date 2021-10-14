package org.prgms.board.post.controller;

import org.prgms.board.common.response.ApiResponse;
import org.prgms.board.post.dto.CommentRequest;
import org.prgms.board.post.dto.PostRequest;
import org.prgms.board.post.dto.PostResponse;
import org.prgms.board.post.service.CommentService;
import org.prgms.board.post.service.PostService;
import org.prgms.board.user.dto.UserIdRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public ApiResponse<Page<PostResponse>> getAllPost(Pageable pageable) {
        return ApiResponse.toResponse(postService.getAllPost(pageable));
    }

    @GetMapping("/users")
    public ApiResponse<Page<PostResponse>> getAllPostByUser(Pageable pageable,
                                                            @RequestBody @Valid UserIdRequest userIdRequest) {
        return ApiResponse.toResponse(postService.getAllPostByUser(pageable, userIdRequest));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getOnePost(@PathVariable Long id) {
        return ApiResponse.toResponse(postService.getOnePost(id));
    }

    @PostMapping
    public ApiResponse<Long> addPost(@RequestBody @Valid PostRequest postRequest) {
        return ApiResponse.toResponse(postService.addPost(postRequest));
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> modifyPost(@PathVariable Long id,
                                        @RequestBody @Valid PostRequest postRequest) {
        return ApiResponse.toResponse(postService.modifyPost(id, postRequest));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Integer> removePost(@PathVariable Long id,
                                           @RequestBody @Valid UserIdRequest userIdRequest) {
        postService.removePost(id, userIdRequest);
        return ApiResponse.toResponse(1);
    }

    @PostMapping("/{id}/comments")
    public ApiResponse<Long> addComment(@PathVariable Long id,
                                        @RequestBody @Valid CommentRequest commentRequest) {
        return ApiResponse.toResponse(commentService.addComment(id, commentRequest));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ApiResponse<Long> modifyComment(@PathVariable Long postId,
                                           @PathVariable Long commentId,
                                           @RequestBody @Valid CommentRequest commentRequest) {
        return ApiResponse.toResponse(commentService.modifyComment(postId, commentId, commentRequest));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ApiResponse<Integer> removeComment(@PathVariable Long postId,
                                              @PathVariable Long commentId,
                                              @RequestBody @Valid UserIdRequest userIdRequest) {
        commentService.removeComment(postId, commentId, userIdRequest);
        return ApiResponse.toResponse(1);
    }

}

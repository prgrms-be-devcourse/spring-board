package programmers.jpaBoard.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import programmers.jpaBoard.dto.PostDto;
import programmers.jpaBoard.entity.Post;
import programmers.jpaBoard.repository.PostRepository;

import java.util.NoSuchElementException;

import static programmers.jpaBoard.exception.ErrorMessage.NOT_FOUND_POST;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDto.Response create(String title, String content) {
        Post post = new Post(title, content);

        Post savedPost = postRepository.save(post);
        return toResponse(savedPost);
    }

    public PostDto.Response getPost(Long id) {
        Post post = findPostById(id);

        return toResponse(post);
    }

    public Page<PostDto.Response> getAllPost(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public PostDto.Response update(Long id, String title, String content) {
        Post post = findPostById(id);
        post.updatePost(title, content);

        return toResponse(post);
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_POST.getMessage()));
    }

    private PostDto.Response toResponse(Post post) {
        return new PostDto.Response(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt()
        );
    }
}

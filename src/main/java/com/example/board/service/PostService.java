package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.domain.Post;
import com.example.board.dto.PostDto;
import com.example.board.repository.PostRepository;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Long save(PostDto postDto) {
        Post post = PostConverter.convertFromDtoToPost(postDto);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostConverter::convertFromPostToDto);
    }

    @Transactional
    public PostDto findById(Long id) throws NotFoundException {
        return postRepository.findById(id).map(PostConverter::convertFromPostToDto).orElseThrow(() -> new NotFoundException("Post Not Found"));
    }

    @Transactional
    public PostDto editPost(Long id, PostDto postDto) throws NotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post Not Found"));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        postRepository.save(post);
        return PostConverter.convertFromPostToDto(post);
    }
}

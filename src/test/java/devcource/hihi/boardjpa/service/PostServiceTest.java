package devcource.hihi.boardjpa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;


@Slf4j
@SpringBootTest
public class PostServiceTest {


    @Mock
    @Autowired
    private PostRepository postRepository;

    private List<Post> posts = new ArrayList<>();

    @InjectMocks
    @Autowired
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        for (int i = 1; i <= 5; i++) {
            Post post = Post.builder()
                    .title("Title " )
                    .content("Content ")
                    .build();
            posts.add(post);
        }
    }

    @AfterEach
    public void clear() {
        postRepository.deleteAll();
    }


    @Test
    public void testGetPostsFirstPage() {
        // Given
        int limit = 5;
        log.info("posts.size():{}", posts.size());
        when(postRepository.findTopByOrderByIdDesc(limit)).thenReturn(posts);

        // When
        List<Post> result = postService.getPosts(null, limit);
        log.info("result.size() : {}",result.size());

        for (int i = 0; i < limit; i++) {
            log.info("result : {}",result.get(i));
            log.info("post : {}", posts.get(i));
        }
        // Then
        assertEquals(limit, result.size());

    }

    @Test
    public void testGetPostsNextPage() {
        // Given
        int limit = 5;
        Long cursor = 10L;
        when(postRepository.findByIdLessThanOrderByIdDesc(cursor, limit)).thenReturn(posts);

        // When
        List<Post> result = postService.getPosts(cursor, limit);


        // Then
        assertEquals(limit, result.size());
    }

}



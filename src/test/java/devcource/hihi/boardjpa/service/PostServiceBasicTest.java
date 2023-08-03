package devcource.hihi.boardjpa.service;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.dto.post.CreatePostDto;
import devcource.hihi.boardjpa.dto.post.ResponsePostDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
public class PostServiceBasicTest {

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("")
    public void saveTest(){
        //given
        User user = new User("테스트용 고객",45,"숨쉬기");

        Post post = Post.builder()
                .title("테스트용")
                .content("테스트 컨텐츠")
                .build();

        post.allocateUser(user);
        log.info("{}", post.getUser().getId());
        CreatePostDto postDto = Post.toCreateDto(post);
        //when
        ResponsePostDto dto = postService.createDto(postDto);

        //then
        assertEquals(dto.content(),post.getContent());
    }

}

package com.example.board.converter;

import com.example.board.Dto.PostDto;
import com.example.board.Dto.UserDto;
import com.example.board.domain.Post;
import com.example.board.domain.User;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    public Post convertpost(PostDto dto){
        Post post=new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setUser(this.convertuser(dto.getUser()));
        return post;

    }
    public User convertuser(UserDto dto){
        User user=new User();
        user.setName(dto.getName());
        user.setHobby(dto.getHobby());
        user.setAge(dto.getAge());
        user.setCreatedBy(dto.getName());
        return user;

    }
    public PostDto convertPostDto(Post post){
       return  PostDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .user(this.converUserDto(post.getUser()))
                .build();

    }
    public UserDto converUserDto(User user){
        return UserDto.builder()
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}

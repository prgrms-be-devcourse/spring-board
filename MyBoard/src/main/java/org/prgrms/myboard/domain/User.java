package org.prgrms.myboard.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.prgrms.myboard.dto.UserResponseDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Nullable
    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Nullable
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User(String name, int age, String hobby) {
        validateName(name);
        validateAge(age);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    private void validateName(String name) {
        if(name == null || name.isBlank() || name.length() >= 4) {
            throw new IllegalArgumentException("잘못된 이름입니다.");
        }
    }

    private void validateAge(int age) {
        if(age <= 0 || age >= 30) {
            throw new IllegalArgumentException("잘못된 나이입니다.");
        }
    }

    public UserResponseDto toUserResponseDto() {
        return new UserResponseDto(id, name, age, hobby, posts, getCreatedAt(), getUpdatedAt());
    }

    public void writePost(Post post) {
        posts.add(post);
    }

    public void removePost(Post post) {
        posts.remove(post);
    }
}

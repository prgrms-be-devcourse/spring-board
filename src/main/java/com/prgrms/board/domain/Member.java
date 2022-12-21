package com.prgrms.board.domain;

import lombok.*;
import lombok.Builder.Default;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 10, unique = true)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotNull
    private int age;

    private String hobby;

    @Default
    @OneToMany(mappedBy = "writer", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

}

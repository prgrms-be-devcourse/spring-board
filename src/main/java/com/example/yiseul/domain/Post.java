package com.example.yiseul.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Post(Member member, String title, String content) {
        this.title = title;
        this.content = content;
        setCreatedBy(member.getName());
    }

    public void updatePost(String updateTitle, String updateContent) {
        changeTitle(updateTitle);
        changeContent(updateContent);
    }

    private void changeTitle(String updateTitle) {
        if (updateTitle != null) {
            this.title = updateTitle;
        }
    }

    private void changeContent(String updateContent) {
        if (updateContent != null) {
            this.content = updateContent;
        }
    }
}

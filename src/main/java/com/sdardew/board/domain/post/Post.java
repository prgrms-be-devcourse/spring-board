package com.sdardew.board.domain.post;

import com.sdardew.board.domain.post.converter.UserIdConverter;
import com.sdardew.board.domain.user.User;
import com.sdardew.board.dto.post.PostDto;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name="title")
  @NotBlank
  @Size(min = 3, max = 255)
  private String title;

  @Column(name="content")
  @NotBlank
  @Size(min = 3, max = 1024)
  private String content;

  @Column(name="create_at")
  @NotNull
  private LocalDateTime createAt;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @Convert(converter = UserIdConverter.class)
  private User user; // user 테이블과 연관

  public Post() {
  }

  public Post(String title, String content, LocalDateTime createAt, User user) {
    this.title = title;
    this.content = content;
    this.createAt = createAt;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public String getContent() {
    return content;
  }

  public User getUser() {
    return user;
  }

  public void updatePost(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public PostDto toPostDto() {
    return new PostDto(id, title, content, createAt, user.getId());
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}

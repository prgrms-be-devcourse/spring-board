package com.programmers.epicblues.jpa_board.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private int age;
  private String hobby;
  private String createdBy;
  private LocalDateTime createdAt;

  @Builder
  public User(String name, int age, String hobby, String createdBy, LocalDateTime createdAt) {
    this.id = null;
    this.name = name;
    this.age = age;
    this.hobby = hobby;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
  }
}

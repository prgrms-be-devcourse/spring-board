package com.example.board.domain.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(@NotBlank @Size(max = 30) String name, @NotNull
@Min(1) Integer age, @NotBlank @Size(max = 100) String hobby) {

}
package kdt.prgms.springbootboard.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PostDto {

    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @Size(max = 255)
    private String content;

    @NotNull
    @JsonProperty("user")
    private UserDto userDto;

    public PostDto() {
    }

    @Builder
    public PostDto(Long id, String title, String content,
        UserDto userDto) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userDto = userDto;
    }
}

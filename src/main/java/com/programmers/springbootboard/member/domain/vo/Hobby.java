package com.programmers.springbootboard.member.domain.vo;

import com.programmers.springbootboard.exception.ErrorMessage;
import com.programmers.springbootboard.exception.InvalidArgumentException;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
public class Hobby {
    @Transient
    private static final String HOBBY_VALIDATOR = "^.{1,50}$";

    @Column(name = "member_hobby", nullable = false)
    private String hobby;

    protected Hobby() {

    }

    public Hobby(String hobby) {
        validate(hobby);
        this.hobby = hobby;
    }

    private void validate(String name) {
        if (!Pattern.matches(HOBBY_VALIDATOR, name)) {
            throw new InvalidArgumentException(ErrorMessage.INVALID_MEMBER_HOBBY);
        }
    }

    public String getHobby() {
        return hobby;
    }
}

package com.programmers.springbootboard.member.dto;

import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Hobby;
import com.programmers.springbootboard.member.domain.vo.Name;
import lombok.Data;

@Data
public class MemberSignRequest {
    private Name name;
    private Age age;
    private Hobby hobby;
}

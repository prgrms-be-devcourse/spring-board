package prgms.boardmission.post;

import prgms.boardmission.domain.Member;
import prgms.boardmission.domain.Post;
import prgms.boardmission.member.MemberConverter;
import prgms.boardmission.member.dto.MemberDto;
import prgms.boardmission.post.dto.PostDto;

import java.time.LocalDateTime;

public final class PostConverter {
    public static Post convertToPost(PostDto postDto) {
        String title = postDto.title();
        String content = postDto.content();
        Member member = MemberConverter.convertToMember(postDto.memberDto());

        Post post = new Post(title, content, member);

        return post;
    }

    public static PostDto convertToPostDto(Post post) {
        Member member = post.getMember();
        MemberDto memberDto = MemberConverter.convertToMemberDto(member);

        return new PostDto(post.getTitle(), post.getContent(), memberDto);
    }
}

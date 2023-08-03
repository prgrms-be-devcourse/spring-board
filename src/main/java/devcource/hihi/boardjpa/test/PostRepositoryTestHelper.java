package devcource.hihi.boardjpa.test;

import devcource.hihi.boardjpa.domain.Post;

import java.util.ArrayList;
import java.util.List;

public class PostRepositoryTestHelper {
    public static List<Post> createSamplePosts(int count){

        // 테스트용 데이터 생성
        List<Post> testData = new ArrayList<>();
        for (long i = 1; i <= count; i++) {
            testData.add(Post.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .build()
            );
        }
        return testData;
    }


}

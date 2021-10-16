package kdt.prgms.springbootboard.domain;

import javax.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "post")
@SQLDelete(sql = "UPDATE post SET deleted = true WHERE post_id=?")
@Where(clause = "deleted=false")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    protected Post() {
    }

    private Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Post createPost(String title, String content, User user) {
        var newPost = new Post(title, content);
        newPost.changeUser(user);
        return newPost;

    }

    public void changeUser(User user) {
        if(this.user != null) {
            this.user.removePost(this);
        }
        this.user = user;
        this.user.addPost(this);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Post{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", user=" + user +
            "} " + super.toString();
    }
}

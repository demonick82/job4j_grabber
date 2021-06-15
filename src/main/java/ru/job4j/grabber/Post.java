package ru.job4j.grabber;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private String postName;
    private String postText;
    private String postLink;
    private LocalDateTime postCreated;

    public Post(String postName, String postText, String postLink, LocalDateTime postCreated) {
        this.postName = postName;
        this.postText = postText;
        this.postLink = postLink;
        this.postCreated = postCreated;
    }

    public String getPostName() {
        return postName;
    }

    public String getPostText() {
        return postText;
    }

    public String getPostLink() {
        return postLink;
    }

    public LocalDateTime getPostCreated() {
        return postCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(postName, post.postName) &&
                Objects.equals(postText, post.postText) &&
                Objects.equals(postLink, post.postLink) &&
                Objects.equals(postCreated, post.postCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postName, postText, postLink, postCreated);
    }

    @Override
    public String toString() {
        return "Post{" +
                "postName='" + postName + '\'' +
                ", postText='" + postText + '\'' +
                ", postLink='" + postLink + '\'' +
                ", postCreated=" + postCreated +
                '}';
    }
}

package org.openchat.builders

import org.openchat.domain.post.Post
import org.openchat.domain.post.PostId
import org.openchat.domain.user.UserId

import java.time.LocalDateTime

class PostBuilder {
    private String postId = UUID.randomUUID().toString()
    private String userId = UUID.randomUUID().toString()
    private String text = "Hello!!!"
    private LocalDateTime dateTime = LocalDateTime.now()

    static PostBuilder aPost() {
        return new PostBuilder()
    }

    def withUserId(String userId) {
        this.userId = userId
        return this
    }

    def withText(String text) {
        this.text = text
        return this
    }

    def withDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime
        return this
    }

    def withPostId(String postId) {
        this.postId = postId
        return this
    }

    def build() {
        return new Post(new PostId(postId), new UserId(userId), text, dateTime)
    }
}

package org.openchat.infrastructure.persistence

import org.openchat.domain.post.Post
import org.openchat.domain.post.PostId
import org.openchat.domain.post.PostRepository
import org.openchat.domain.user.UserId
import java.util.*

class InMemoryPostRepository : PostRepository {
    private val posts = mutableListOf<Post>()

    override fun nextId(): PostId {
        return PostId(UUID.randomUUID().toString())
    }

    override fun add(post: Post) {
        posts.add(post)
    }

    override fun allBy(userId: UserId): List<Post> {
        return posts
                .filter(userIdEqualTo(userId))
                .reversed()
    }

    override fun allBy(userIds: List<UserId>): List<Post> {
        return posts
                .filter(userIdIn(userIds))
                .reversed()
    }

    private fun userIdEqualTo(userId: UserId) = { post : Post -> post.userId == userId }

    private fun userIdIn(userIds: List<UserId>) = { post : Post -> userIds.contains(post.userId)}
}

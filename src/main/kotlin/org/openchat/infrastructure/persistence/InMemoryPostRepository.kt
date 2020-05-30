package org.openchat.infrastructure.persistence

import org.openchat.domain.post.Post
import org.openchat.domain.post.PostId
import org.openchat.domain.post.PostRepository
import java.util.*
import kotlin.collections.ArrayList

class InMemoryPostRepository : PostRepository {
    private val posts = mutableListOf<Post>()

    override fun nextId(): PostId {
        return PostId(UUID.randomUUID().toString())
    }

    override fun add(post: Post) {
        posts.add(post)
    }

}

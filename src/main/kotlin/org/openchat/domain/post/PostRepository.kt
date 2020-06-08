package org.openchat.domain.post

import org.openchat.domain.user.UserId

interface PostRepository {
    fun nextId() : PostId
    fun add(post: Post)
    fun allBy(userId: UserId): List<Post>
    fun allBy(userIds: List<UserId>): List<Post>
}
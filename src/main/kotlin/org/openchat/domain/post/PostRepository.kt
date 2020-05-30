package org.openchat.domain.post

interface PostRepository {
    fun nextId() : PostId
    fun add(post: Post)
}
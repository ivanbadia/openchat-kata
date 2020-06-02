package org.openchat.application.usecases

import org.openchat.domain.post.Post
import org.openchat.domain.post.PostRepository
import org.openchat.domain.user.UserId

fun retrieveTimeline(postRepository: PostRepository): (String) -> List<Post> = {
    userId -> postRepository.allBy(UserId(userId))
}
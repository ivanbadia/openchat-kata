package org.openchat.application.usecases

import org.openchat.domain.following.FollowingRepository
import org.openchat.domain.post.Post
import org.openchat.domain.post.PostRepository
import org.openchat.domain.user.UserId

fun retrieveWall(followingRepository: FollowingRepository, postRepository: PostRepository): (String) -> List<Post> = {
    userId ->
    val followees = followingRepository.followeesBy(UserId(userId))
    postRepository.allBy(followees + UserId(userId))
}
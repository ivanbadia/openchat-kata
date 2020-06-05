package org.openchat.infrastructure.persistence

import org.openchat.domain.following.Following
import org.openchat.domain.following.FollowingRepository

class InMemoryFollowingRepository : FollowingRepository {
    private val followings = mutableListOf<Following>()

    override fun add(following: Following) {
        followings.add(following)
    }

    override fun exists(following: Following) : Boolean{
        return followings.any {addedFollowing -> addedFollowing == following }
    }

}

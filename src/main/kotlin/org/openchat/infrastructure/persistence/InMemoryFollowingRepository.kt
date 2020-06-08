package org.openchat.infrastructure.persistence

import arrow.core.extensions.list.foldable.toList
import org.openchat.domain.following.Following
import org.openchat.domain.following.FollowingRepository
import org.openchat.domain.user.UserId
import java.util.*

class InMemoryFollowingRepository : FollowingRepository {
    private val followings = mutableListOf<Following>()

    override fun add(following: Following) {
        followings.add(following)
    }

    override fun exists(following: Following): Boolean {
        return followings.any { addedFollowing -> addedFollowing == following }
    }

    override fun followeesBy(followerId: UserId): List<UserId> {
        return followings
                .filter { following -> following.followerId == followerId }
                .map(Following::followeeId)
    }
}

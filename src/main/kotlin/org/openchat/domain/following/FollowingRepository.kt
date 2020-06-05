package org.openchat.domain.following

import org.openchat.domain.user.UserId


interface FollowingRepository {
    fun add(following: Following)
    fun exists(following: Following) : Boolean
    fun followeesBy(followerId: UserId): List<UserId>
}
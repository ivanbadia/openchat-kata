package org.openchat.domain.following

interface FollowingRepository {
    fun add(following: Following)
    fun exists(following: Following) : Boolean
}
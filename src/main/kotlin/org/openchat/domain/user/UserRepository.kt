package org.openchat.domain.user

import arrow.core.Option

interface UserRepository {
    fun nextId(): UserId
    fun add(user: User)
    fun isUsernameInUse(username : Username) : Boolean
    fun userWith(credentials: Credentials): Option<User>
    fun get(userIds: List<UserId>): List<User>
}
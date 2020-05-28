package org.openchat.domain.user

interface UserRepository {
    fun nextId(): UserId
    fun add(user: User)
    fun isUsernameInUse(username : Username) : Boolean
}
package org.openchat.infrastructure.persistence

import org.openchat.domain.user.User
import org.openchat.domain.user.UserId
import org.openchat.domain.user.UserRepository
import org.openchat.domain.user.Username
import java.util.*
import kotlin.collections.HashMap

class InMemoryUserRepository : UserRepository {
    private var usersByUsername : HashMap<Username, User> = HashMap()

    override fun add(user: User) {
        usersByUsername[user.username] = user
    }

    override fun nextId(): UserId {
       return UserId(UUID.randomUUID().toString())
    }

    override fun isUsernameInUse(username: Username): Boolean {
        return usersByUsername.containsKey(username)
    }
}
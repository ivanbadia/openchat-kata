package org.openchat.infrastructure.persistence

import arrow.core.Option
import org.openchat.domain.user.*
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

    override fun userWith(credentials: Credentials): Option<User> {
        return Option.fromNullable(usersByUsername[credentials.username])
                .filter { user -> user.has(credentials) }
    }

    override fun get(userIds: List<UserId>): List<User> {
        return usersByUsername.values
                .filter { user -> userIds.contains(user.id) }
    }
}
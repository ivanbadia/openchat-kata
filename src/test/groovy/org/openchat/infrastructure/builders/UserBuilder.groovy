package org.openchat.infrastructure.builders

import org.openchat.domain.user.User
import org.openchat.domain.user.UserId
import org.openchat.domain.user.Username

class UserBuilder {
    private UserId userId = new UserId(UUID.randomUUID().toString())
    private Username username = new Username("Alice")
    private String password = "alki324d"
    private String about = "I love playing the piano and travelling."

    static anUser() {
        return new UserBuilder()
    }

    User build() {
        return new User(userId, username, password, about)
    }

    UserBuilder withUsername(String username) {
        this.username = new Username(username)
        return this
    }

    UserBuilder withAbout(String about) {
        this.about = about
        return this
    }
}

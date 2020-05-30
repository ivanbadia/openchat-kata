package org.openchat.builders

import org.openchat.domain.user.Credentials
import org.openchat.domain.user.User
import org.openchat.domain.user.UserId
import org.openchat.domain.user.Username

class UserBuilder {
    private UserId userId = new UserId(UUID.randomUUID().toString())
    private Username username = new Username(UUID.randomUUID().toString())
    private String password = "alki324d"
    private String about = "I love playing the piano and travelling."

    static anUser() {
        return new UserBuilder()
    }

    User build() {
        return new User(userId, new Credentials(username, password), about)
    }

    UserBuilder withUsername(String username) {
        this.username = new Username(username)
        return this
    }

    UserBuilder withAbout(String about) {
        this.about = about
        return this
    }

    UserBuilder withPassword(String password) {
        this.password = password
        return this
    }
}

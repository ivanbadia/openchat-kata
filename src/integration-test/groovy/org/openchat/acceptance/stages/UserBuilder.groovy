package org.openchat.acceptance.stages

class UserBuilder {
    private String id = UUID.randomUUID().toString()
    private String username = UUID.randomUUID().toString()
    private String password = "alki324d"
    private String about = "I love playing the piano and travelling."

    static anUser() {
        return new UserBuilder()
    }

    User build() {
        return new User(id, username, password, about)
    }
}

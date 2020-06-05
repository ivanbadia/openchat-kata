package org.openchat.acceptance.stages

class UserBuilder {
    private String id = UUID.randomUUID().toString()
    private String username = UUID.randomUUID().toString()
    private String password = "alki324d"
    private String about = "I love playing the piano and travelling."

    static anUser() {
        return new UserBuilder()
    }

    def withId(String id) {
        this.id = id
        return this
    }

    def withUsername(String username) {
        this.username = username
        return this
    }

    def withAbout(String about) {
        this.about = about
        return this
    }


    User build() {
        return new User(id, username, password, about)
    }


}

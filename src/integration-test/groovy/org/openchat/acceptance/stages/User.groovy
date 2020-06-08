package org.openchat.acceptance.stages

class User {
    public final String id
    public final String username
    public final String password
    public final String about

    User(String id, String username, String password, String about) {
        this.id = id
        this.username = username
        this.about = about
        this.password = password
    }
}

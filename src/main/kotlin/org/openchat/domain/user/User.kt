package org.openchat.domain.user

data class User(val id: UserId,
           val credentials: Credentials,
           val about: String) {

    val username: Username = this.credentials.username

    fun has(credentials: Credentials): Boolean {
        return this.credentials == credentials
    }
}
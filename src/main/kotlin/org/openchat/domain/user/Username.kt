package org.openchat.domain.user

data class Username(private val username: String) {
    fun asString(): String {
        return username
    }
}
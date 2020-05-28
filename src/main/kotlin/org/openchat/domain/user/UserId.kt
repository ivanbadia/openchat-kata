package org.openchat.domain.user

data class UserId(private val id: String) {
    fun asString(): String {
        return id
    }
}
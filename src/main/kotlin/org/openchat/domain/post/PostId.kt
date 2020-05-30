package org.openchat.domain.post

data class PostId(private val id: String) {
    fun asString(): String {
        return id
    }
}

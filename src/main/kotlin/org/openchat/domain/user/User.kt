package org.openchat.domain.user

data class User(val id: UserId,
           val username: Username,
           val password: String,
           val about: String)
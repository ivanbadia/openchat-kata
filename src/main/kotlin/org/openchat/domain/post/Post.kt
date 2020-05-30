package org.openchat.domain.post

import org.openchat.domain.user.UserId
import java.time.LocalDateTime

data class Post(val id: PostId, val userId: UserId, val text: String, val dateTime: LocalDateTime)

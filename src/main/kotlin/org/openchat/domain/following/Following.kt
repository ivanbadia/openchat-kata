package org.openchat.domain.following

import org.openchat.domain.user.UserId

data class Following(val followerId: UserId, val followeeId: UserId)

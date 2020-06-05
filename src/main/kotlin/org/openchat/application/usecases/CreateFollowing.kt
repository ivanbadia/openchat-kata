package org.openchat.application.usecases

import arrow.core.Either
import org.openchat.domain.following.Following
import org.openchat.domain.following.FollowingAlreadyExists
import org.openchat.domain.following.FollowingRepository
import org.openchat.domain.user.UserId

fun createFollowing(followingRepository: FollowingRepository): (String, String) -> Either<FollowingAlreadyExists, Unit> =
        lambda@{ followerId, followeeId ->
            val following = Following(UserId(followerId), UserId(followeeId))
            if (followingRepository.exists(following)) {
                return@lambda Either.left(FollowingAlreadyExists())
            }

            followingRepository.add(following)
            Either.right(Unit)
        }

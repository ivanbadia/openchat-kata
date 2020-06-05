package org.openchat.application.usecases

import org.openchat.domain.following.FollowingRepository
import org.openchat.domain.user.User
import org.openchat.domain.user.UserId
import org.openchat.domain.user.UserRepository

fun getFollowees(followingRepository: FollowingRepository, userRepository: UserRepository): (String) -> List<User> =
        { followerId ->
                val followeeIds = followingRepository.followeesBy(UserId(followerId))
                userRepository.get(followeeIds)
        }

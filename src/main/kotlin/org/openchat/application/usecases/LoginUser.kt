package org.openchat.application.usecases

import arrow.core.Option
import org.openchat.domain.user.Credentials
import org.openchat.domain.user.User
import org.openchat.domain.user.UserRepository
import org.openchat.domain.user.Username


fun loginUser(userRepository: UserRepository): (String, String) -> Option<User> = { username, password ->
    userRepository.userWith(Credentials(Username(username), password))
}

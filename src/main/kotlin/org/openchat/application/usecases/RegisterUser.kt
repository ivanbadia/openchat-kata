package org.openchat.application.usecases

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.openchat.domain.user.*

class RegisterUserCmd(val username: String, val password: String, val about: String)

fun registerUser(userRepository: UserRepository): (RegisterUserCmd) -> Either<UsernameAlreadyInUse, User> = { registerUserCmd ->
    val username = Username(registerUserCmd.username)
    if (userRepository.isUsernameInUse(username)) {
        UsernameAlreadyInUse(username).left()
    } else {
        val user = userFrom(userRepository.nextId(), registerUserCmd)
        userRepository.add(user)
        user.right()
    }
}

private fun userFrom(userId: UserId, registerUserCmd: RegisterUserCmd): User {
    return User(userId,
            Credentials(Username(registerUserCmd.username), registerUserCmd.password),
            registerUserCmd.about)
}

package org.openchat.application.usecases

import arrow.core.Either
import org.openchat.domain.user.*
import spock.lang.Specification

import static org.openchat.application.usecases.RegisterUserKt.registerUser

class RegisterUserShould extends Specification {
    private UserRepository userRepository = Mock(UserRepository)
    private static final String USERNAME = "username"
    private static final String PASSWORD = "password"
    private static final String ABOUT = "about"

    def "create a user" () {
        given:
        UserId userId = new UserId("userId")
        userRepository.nextId() >> userId
        RegisterUserCmd registerUserCmd = new RegisterUserCmd(USERNAME, PASSWORD, ABOUT)
        def expectedUser = new User(userId, new Username(USERNAME), PASSWORD, ABOUT)

        when:
        Either<UsernameAlreadyInUse, User> result = registerUser(userRepository).invoke registerUserCmd

        then:
        1 * userRepository.add(expectedUser)
        result == new Either.Right(expectedUser)
    }

    def "fail if user is already in use"() {
        given:
        Username username = new Username(USERNAME)
        userRepository.isUsernameInUse(username) >> true
        RegisterUserCmd registerUserCmd = new RegisterUserCmd(USERNAME, PASSWORD, ABOUT)

        when:
        def result = registerUser(userRepository).invoke registerUserCmd

        then:
        result == new Either.Left<>(new UsernameAlreadyInUse(username))

    }

}

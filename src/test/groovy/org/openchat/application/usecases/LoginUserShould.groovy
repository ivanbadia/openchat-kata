package org.openchat.application.usecases

import arrow.core.Option
import org.openchat.domain.user.Credentials
import org.openchat.domain.user.User
import org.openchat.domain.user.UserRepository
import org.openchat.domain.user.Username
import spock.lang.Specification

import static org.openchat.application.usecases.LoginUserKt.loginUser
import static org.openchat.builders.UserBuilder.anUser

class LoginUserShould extends Specification {
    private UserRepository userRepository = Mock(UserRepository)
    private static final String IVAN_USERNAME = "username"
    private static final String IVAN_PASSWORD = "password"

    private User IVAN = anUser()
            .withUsername(IVAN_USERNAME)
            .withPassword(IVAN_PASSWORD)
            .build()

    def "login user" () {
        given:
        Credentials credentials = new Credentials(new Username(IVAN_USERNAME), IVAN_PASSWORD)
        userRepository.userWith(credentials) >> Option.@Companion.just(IVAN)

        when:
        Option<User> result = loginUser(userRepository).invoke IVAN_USERNAME, IVAN_PASSWORD

        then:
        result == Option.@Companion.just(IVAN)
    }


}

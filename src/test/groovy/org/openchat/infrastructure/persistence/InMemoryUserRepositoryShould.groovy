package org.openchat.infrastructure.persistence

import arrow.core.Option
import org.openchat.domain.user.Credentials
import org.openchat.domain.user.User
import org.openchat.domain.user.UserRepository
import org.openchat.domain.user.Username
import spock.lang.Specification

import static org.openchat.infrastructure.builders.UserBuilder.anUser

class InMemoryUserRepositoryShould extends Specification {
    private static final User IVAN = anUser()
            .withUsername("ivan")
            .withPassword("ivan_password")
            .build()
    private static final User PABLO = anUser()
            .withUsername("pablo")
            .build()
    public static final Credentials UNKNOWN_USER_CREDENTIALS = new Credentials(new Username("invalid"), "invalid")
    private static final Credentials IVAN_CREDENTIALS_WITH_INVALID_PASSWORD = new Credentials(IVAN.username, "invalid_password")
    private static final Credentials IVAN_CREDENTIALS = new Credentials(IVAN.username, "ivan_password")

    private UserRepository userRepository

    void setup() {
        userRepository = new InMemoryUserRepository()
    }

    def "inform when username is already in use"() {
        given:
        userRepository.add(IVAN)

        expect:
        userRepository.isUsernameInUse(IVAN.username)
        !userRepository.isUsernameInUse(PABLO.username)

    }

    def "return user with credentials"() {
        given:
        userRepository.add(PABLO)
        userRepository.add(IVAN)

        expect:
        userRepository.userWith(UNKNOWN_USER_CREDENTIALS) == Option.@Companion.empty()
        userRepository.userWith(IVAN_CREDENTIALS_WITH_INVALID_PASSWORD) == Option.@Companion.empty()
        userRepository.userWith(IVAN_CREDENTIALS) == Option.@Companion.just(IVAN)

    }
}

package org.openchat.infrastructure.persistence

import org.openchat.domain.user.User
import org.openchat.domain.user.UserRepository
import spock.lang.Specification

import static org.openchat.infrastructure.builders.UserBuilder.anUser

class InMemoryUserRepositoryShould extends Specification {
    private static final User IVAN = anUser()
            .withUsername("ivan")
            .build()
    private static final User PABLO = anUser()
            .withUsername("pablo")
            .build()
    UserRepository userRepository = new InMemoryUserRepository()

    def "inform when username is already in use"() {
        given:
        userRepository.add(IVAN)

        expect:
        userRepository.isUsernameInUse(IVAN.username)
        !userRepository.isUsernameInUse(PABLO.username)

    }
}

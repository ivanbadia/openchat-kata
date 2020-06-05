package org.openchat.application.usecases


import org.openchat.domain.following.FollowingRepository
import org.openchat.domain.user.User
import org.openchat.domain.user.UserId
import org.openchat.domain.user.UserRepository
import spock.lang.Specification

import static org.openchat.application.usecases.GetFolloweesKt.getFollowees
import static org.openchat.builders.UserBuilder.anUser

class GetFolloweesShould extends Specification {
    private FollowingRepository followingRepository = Stub(FollowingRepository)
    private UserRepository userRepository = Stub(UserRepository)
    private static final IVAN = new UserId(UUID.randomUUID().toString())
    private static final PABLO = new UserId(UUID.randomUUID().toString())
    private static final VICTOR = new UserId(UUID.randomUUID().toString())
    private static final List<UserId> PABLO_AND_VICTOR = [PABLO, VICTOR]
    private static final List<User> FOLLOWEES = [anUser().build(), anUser().build()]


    def "return followees"() {
        given:
        followingRepository.followeesBy(IVAN) >> PABLO_AND_VICTOR
        userRepository.get(PABLO_AND_VICTOR) >> FOLLOWEES

        when:
        List<User> result = getFollowees(followingRepository, userRepository).invoke IVAN.asString()

        then:
        result == FOLLOWEES
    }
}

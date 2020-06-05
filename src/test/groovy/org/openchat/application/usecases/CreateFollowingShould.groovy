package org.openchat.application.usecases

import arrow.core.Either
import org.openchat.domain.following.Following
import org.openchat.domain.following.FollowingRepository
import org.openchat.domain.user.UserId
import spock.lang.Specification

import static org.openchat.application.usecases.CreateFollowingKt.createFollowing

class CreateFollowingShould extends Specification {
    private FollowingRepository followingRepository = Mock(FollowingRepository)
    private static final String FOLLOWER_ID = UUID.randomUUID().toString()
    private static final String FOLLOWEE_ID = UUID.randomUUID().toString()

    def "create following"() {
        when:
        Either result = createFollowing(followingRepository).invoke FOLLOWER_ID, FOLLOWEE_ID

        then:
        result.isRight()
        1 * followingRepository.add(new Following(new UserId(FOLLOWER_ID), new UserId(FOLLOWEE_ID)))
    }

    def "fail if following already exists"() {
        given:
        Following following = new Following(new UserId(FOLLOWER_ID), new UserId(FOLLOWEE_ID))
        followingRepository.exists(following) >> true

        when:
        Either result = createFollowing(followingRepository).invoke FOLLOWER_ID, FOLLOWEE_ID

        then:
        result.isLeft()
    }
}

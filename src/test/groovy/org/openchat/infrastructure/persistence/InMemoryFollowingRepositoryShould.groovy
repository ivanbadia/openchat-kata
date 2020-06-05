package org.openchat.infrastructure.persistence

import org.openchat.domain.following.Following
import org.openchat.domain.following.FollowingRepository
import org.openchat.domain.user.UserId
import spock.lang.Specification

class InMemoryFollowingRepositoryShould extends Specification {

    private static final UserId IVAN = new UserId(UUID.randomUUID().toString())
    private static final UserId PABLO = new UserId(UUID.randomUUID().toString())
    private static final UserId VICTOR = new UserId(UUID.randomUUID().toString())
    private static Following IVAN_FOLLOWS_PABLO = new Following(IVAN, PABLO)
    private static Following IVAN_FOLLOWS_VICTOR = new Following(IVAN, VICTOR)
    private static Following PABLO_FOLLOWS_IVAN = new Following(PABLO, IVAN)

    private FollowingRepository followingRepository = new InMemoryFollowingRepository()

    def "inform that following exists"() {
        given:
        followingRepository.add(IVAN_FOLLOWS_PABLO)

        when:
        followingRepository.exists(IVAN_FOLLOWS_PABLO)

        then:
        assert followingRepository.exists(IVAN_FOLLOWS_PABLO)
        assert !followingRepository.exists(PABLO_FOLLOWS_IVAN)
    }

    def "return followees by follower"() {
        given:
        followingRepository.add(IVAN_FOLLOWS_PABLO)
        followingRepository.add(IVAN_FOLLOWS_VICTOR)

        when:
        def followees = followingRepository.followeesBy(IVAN)

        then:
        followees == [PABLO, VICTOR]
    }
}

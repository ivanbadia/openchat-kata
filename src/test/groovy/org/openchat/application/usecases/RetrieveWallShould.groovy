package org.openchat.application.usecases

import org.openchat.domain.following.FollowingRepository
import org.openchat.domain.post.Post
import org.openchat.domain.post.PostRepository
import org.openchat.domain.user.UserId
import spock.lang.Specification

import static org.openchat.application.usecases.RetrieveWallKt.retrieveWall
import static org.openchat.builders.PostBuilder.aPost

class RetrieveWallShould extends Specification {
    private static List<Post> POSTS = [aPost().build()]
    private static final UserId BIEL = new UserId(UUID.randomUUID().toString())
    private static final List<UserId> LAURA_AND_DAVID = [new UserId(UUID.randomUUID().toString()), new UserId(UUID.randomUUID().toString())]

    private FollowingRepository followingRepository = Mock(FollowingRepository)
    private PostRepository postRepository = Mock(PostRepository)

    def "retrieve user's posts and followees' posts"() {
        given:
        followingRepository.followeesBy(BIEL) >> LAURA_AND_DAVID
        postRepository.allBy(LAURA_AND_DAVID + BIEL) >> POSTS

        when:
        def result = retrieveWall(followingRepository, postRepository).invoke BIEL.asString()

        then:
        result == POSTS
    }
}

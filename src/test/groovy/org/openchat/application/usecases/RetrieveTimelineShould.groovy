package org.openchat.application.usecases

import org.openchat.domain.post.Post
import org.openchat.domain.post.PostRepository
import org.openchat.domain.user.UserId
import spock.lang.Specification

import static org.openchat.application.usecases.RetrieveTimelineKt.retrieveTimeline
import static org.openchat.builders.PostBuilder.aPost

class RetrieveTimelineShould extends Specification {
    private static List<Post> POSTS = [aPost().build()]
    private static final String USER_ID = UUID.randomUUID().toString()

    private PostRepository postRepository = Mock(PostRepository)

    def "retrieve user's posts"() {
        given:
        postRepository.allBy(new UserId(USER_ID)) >> POSTS

        when:
        def result = retrieveTimeline(postRepository).invoke USER_ID

        then:
        result == POSTS
    }
}

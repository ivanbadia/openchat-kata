package org.openchat.infrastructure.persistence

import org.openchat.domain.post.Post
import org.openchat.domain.post.PostRepository
import org.openchat.domain.user.UserId
import spock.lang.Specification

import static org.openchat.builders.PostBuilder.aPost

class InMemoryPostRepositoryShould extends Specification {
    private static final UserId IVAN_ID = new UserId(UUID.randomUUID().toString())
    private static final Post IVAN_FIRST_POST = aPost()
    .withUserId(IVAN_ID.asString())
    .withText("First post")
    .build()
    private static final Post IVAN_SECOND_POST = aPost()
            .withUserId(IVAN_ID.asString())
            .withText("Second post")
            .build()
    private static final Post PABLO_POST = aPost()
            .withUserId(UUID.randomUUID().toString())
            .withText("Pablo post")
            .build()


    private PostRepository postRepository = new InMemoryPostRepository()


    def "return user's posts in reverse chronological order"() {
        given:
        postRepository.add(IVAN_FIRST_POST)
        postRepository.add(PABLO_POST)
        postRepository.add(IVAN_SECOND_POST)

        when:
        def result = postRepository.allBy(IVAN_ID)

        then:
        result == [IVAN_SECOND_POST, IVAN_FIRST_POST]
    }
}

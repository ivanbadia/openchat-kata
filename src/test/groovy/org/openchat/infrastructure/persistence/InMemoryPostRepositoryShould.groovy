package org.openchat.infrastructure.persistence

import org.openchat.domain.post.Post
import org.openchat.domain.post.PostRepository
import org.openchat.domain.user.UserId
import spock.lang.Specification

import static org.openchat.builders.PostBuilder.aPost

class InMemoryPostRepositoryShould extends Specification {
    private static final UserId IVAN = new UserId(UUID.randomUUID().toString())
    private static final UserId PABLO = new UserId(UUID.randomUUID().toString())
    private static final Post IVAN_FIRST_POST = aPost()
    .withUserId(IVAN.asString())
    .withText("First post")
    .build()
    private static final Post IVAN_SECOND_POST = aPost()
            .withUserId(IVAN.asString())
            .withText("Second post")
            .build()
    private static final Post PABLO_POST = aPost()
            .withUserId(PABLO.asString())
            .withText("Pablo post")
            .build()

    private PostRepository postRepository = new InMemoryPostRepository()


    def "return posts in reverse chronological order for a user"() {
        given:
        postRepository.add(IVAN_FIRST_POST)
        postRepository.add(PABLO_POST)
        postRepository.add(IVAN_SECOND_POST)

        when:
        def result = postRepository.allBy(IVAN)

        then:
        result == [IVAN_SECOND_POST, IVAN_FIRST_POST]
    }


    def "return posts for several users in reverse chronological order"() {
        given:
        postRepository.add(IVAN_FIRST_POST)
        postRepository.add(PABLO_POST)
        postRepository.add(IVAN_SECOND_POST)

        when:
        def result = postRepository.allBy([IVAN, PABLO])

        then:
        result == [IVAN_SECOND_POST, PABLO_POST,  IVAN_FIRST_POST]
    }
}

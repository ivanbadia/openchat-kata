package org.openchat.application.usecases

import arrow.core.Either
import org.openchat.domain.post.*
import spock.lang.Specification

import java.time.LocalDateTime

import static org.openchat.application.usecases.CreatePostKt.createPost
import static org.openchat.builders.PostBuilder.aPost

class CreatePostShould extends Specification {
    private Clock clock = Stub(Clock)
    private PostRepository postRepository = Mock(PostRepository)
    private static final String USER_ID = UUID.randomUUID().toString()
    private static final String POST_ID = UUID.randomUUID().toString()
    private static final String POST_TEXT = "Hi!"
    private static final LocalDateTime CURRENT_TIME = LocalDateTime.now()
    private InappropriateLanguageDetector inappropriateLanguageDetector = new InappropriateLanguageDetector()


    def "create post"() {
        given:
        clock.now() >> CURRENT_TIME
        postRepository.nextId() >> new PostId(POST_ID)
        Post expectedPost = aPost()
                .withUserId(USER_ID)
                .withPostId(POST_ID)
                .withText(POST_TEXT)
                .withDateTime(CURRENT_TIME)
                .build()

        when:
        Either<InappropriateLanguage, Post> result = createPostWith(USER_ID, POST_TEXT)

        then:
        1 * postRepository.add(expectedPost)
        result == new Either.Right<Post>(expectedPost)
    }

    def "fail if text contains inappropriate language"() {
        when:
        Either<InappropriateLanguage, Post> result = createPostWith(USER_ID, postText)

        then:
        result.isLeft()

        where:
        postText << ["orange", "ORANGE", "this orange on the floor", "ice cream", "elephant"]
    }

    private Either<InappropriateLanguage, Post> createPostWith(String userId, String postText) {
        createPost(postRepository, inappropriateLanguageDetector, clock).invoke userId, postText
    }
}

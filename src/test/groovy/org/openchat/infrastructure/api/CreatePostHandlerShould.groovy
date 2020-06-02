package org.openchat.infrastructure.api

import arrow.core.Either
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput
import org.openchat.builders.PostBuilder
import org.openchat.domain.post.InappropriateLanguage
import org.openchat.domain.post.Post
import ratpack.http.Status
import ratpack.test.handling.HandlingResult
import ratpack.test.handling.RequestFixture
import spock.lang.Specification

import java.time.LocalDateTime

import static ratpack.http.MediaType.APPLICATION_JSON

class CreatePostHandlerShould extends Specification {

    private static final String POST_TEXT = "Hello World!!!"
    private static final String USER_ID = UUID.randomUUID().toString()
    private static final POST = PostBuilder.aPost()
            .withUserId(USER_ID)
            .withText(POST_TEXT)
            .withDateTime(LocalDateTime.of(2020, 5, 29, 16, 04, 0))
            .build()

    def "create post"() {
        given:
        def registerPost = { String userId, String text ->
            assert userId == USER_ID
            assert text == POST_TEXT
            new Either.Right<Post>(POST)
        }

        when:
        HandlingResult result = RequestFixture.handle(
                new CreatePostHandler(registerPost),
                { fixture ->
                    fixture
                            .pathBinding(["userId": USER_ID])
                            .body(jsonWith(POST_TEXT), APPLICATION_JSON)
                }
        )

        then:
        result.status == Status.CREATED
        result.headers["content-type"] == APPLICATION_JSON
        result.bodyText == JsonOutput.toJson([postId: POST.id.asString(), text: POST_TEXT, userId: USER_ID, dateTime: "2020-05-29T16:04:00Z"])
    }

    def "fail if post contains inappropriate language"() {
        given:
        def registerPost = { String userId, String text ->
            new Either.Left<InappropriateLanguage>(new InappropriateLanguage())
        }

        when:
        HandlingResult result = RequestFixture.handle(
                new CreatePostHandler(registerPost),
                { fixture ->
                    fixture
                            .pathBinding(["userId": USER_ID])
                            .body(jsonWith(POST_TEXT), APPLICATION_JSON)
                }
        )

        then:
        result.status == Status.BAD_REQUEST
        result.bodyText == "Post contains inappropriate language."
    }


    String jsonWith(String text) {
        def objectNode = new ObjectMapper().createObjectNode()
        objectNode.put("text", text)
        objectNode.toPrettyString()
    }
}

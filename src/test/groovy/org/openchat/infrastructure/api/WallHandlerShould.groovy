package org.openchat.infrastructure.api

import groovy.json.JsonOutput
import org.openchat.builders.PostBuilder
import ratpack.http.Status
import ratpack.test.handling.HandlingResult
import ratpack.test.handling.RequestFixture
import spock.lang.Specification

import java.time.LocalDateTime

import static ratpack.http.MediaType.APPLICATION_JSON

class WallHandlerShould extends Specification {

    private static final String USER_ID = UUID.randomUUID().toString()
    private static final POST = PostBuilder.aPost()
            .withUserId(USER_ID)
            .withDateTime(LocalDateTime.of(2020, 6, 8, 15, 33, 22))
            .build()

    def "retrieve wall"() {
        given:
        def retrieveWall = { String userId ->
            assert userId == USER_ID
            return [POST]
        }

        when:
        HandlingResult result = RequestFixture.handle(
                new WallHandler(retrieveWall),
                { fixture -> fixture.pathBinding(["userId": USER_ID])}
        )

        then:
        result.status == Status.OK
        result.headers["content-type"] == APPLICATION_JSON
        result.bodyText == JsonOutput.toJson([[postId: POST.id.asString(), text: POST.text, userId: USER_ID, dateTime: "2020-06-08T15:33:22Z"]])
    }
}

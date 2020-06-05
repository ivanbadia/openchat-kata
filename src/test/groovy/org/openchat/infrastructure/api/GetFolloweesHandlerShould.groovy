package org.openchat.infrastructure.api

import groovy.json.JsonOutput
import ratpack.http.Status
import ratpack.test.handling.HandlingResult
import ratpack.test.handling.RequestFixture
import spock.lang.Specification

import static org.openchat.builders.UserBuilder.anUser
import static ratpack.http.MediaType.APPLICATION_JSON

class GetFolloweesHandlerShould extends Specification {
    private static final String FOLLOWER_ID = UUID.randomUUID().toString()
    private static final USER = anUser()
            .build()

    def "return followees"() {
        given:
        def getFollowees = { String followerId ->
            assert followerId == FOLLOWER_ID
            return [USER]
        }

        when:
        HandlingResult result = RequestFixture.handle(
                new GetFolloweesHandler(getFollowees),
                { fixture ->
                    fixture.pathBinding(["followerId": FOLLOWER_ID])
                }
        )

        then:
        result.status == Status.OK
        result.headers["content-type"] == APPLICATION_JSON
        result.bodyText == JsonOutput.toJson([[id: USER.id.asString(), username: USER.username.asString(), about: USER.about]])
    }
}

package org.openchat.infrastructure.api

import arrow.core.Either
import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.Unit
import org.openchat.domain.following.FollowingAlreadyExists
import ratpack.http.Status
import ratpack.test.handling.HandlingResult
import ratpack.test.handling.RequestFixture
import spock.lang.Specification

import static ratpack.http.MediaType.APPLICATION_JSON

class CreateFollowingHandlerShould extends Specification {
    private static final String FOLLOWER_ID = UUID.randomUUID().toString()
    private static final String FOLLOWEE_ID = UUID.randomUUID().toString()

    def "create following"() {
        given:
        String followerIdCaptured = null
        String followeeIdCaptured = null
        def createFollowing = { String followerId, String followeeId ->
            followerIdCaptured = followerId
            followeeIdCaptured = followeeId
            new Either.Right<Unit>(Unit.INSTANCE)
        }

        when:
        HandlingResult result = RequestFixture.handle(
                new CreateFollowingHandler(createFollowing),
                { fixture ->
                    fixture
                            .body(jsonWith(FOLLOWER_ID, FOLLOWEE_ID), APPLICATION_JSON)
                }
        )


        then:
        result.status == Status.CREATED
        followerIdCaptured == FOLLOWER_ID
        followeeIdCaptured == FOLLOWEE_ID
    }

    def "fail if following already exists"() {
        given:
        def createFollowing = { String followerId, String followeeId ->
            new Either.Left<FollowingAlreadyExists>(new FollowingAlreadyExists())
        }

        when:
        HandlingResult result = RequestFixture.handle(
                new CreateFollowingHandler(createFollowing),
                { fixture ->
                    fixture
                            .body(jsonWith(FOLLOWER_ID, FOLLOWEE_ID), APPLICATION_JSON)
                }
        )

        then:
        result.status == Status.BAD_REQUEST
    }


    String jsonWith(String followerId, String followingId) {
        def objectNode = new ObjectMapper().createObjectNode()
        objectNode.put("followerId", followerId)
        objectNode.put("followeeId", followingId)
        objectNode.toPrettyString()
    }
}

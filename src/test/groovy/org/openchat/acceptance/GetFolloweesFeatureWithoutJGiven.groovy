package org.openchat.acceptance

import groovy.json.JsonSlurper
import org.openchat.acceptance.stages.User
import ratpack.http.client.ReceivedResponse
import spock.lang.Ignore
import spock.lang.Specification

import static org.openchat.acceptance.stages.UserBuilder.anUser
import static org.openchat.acceptance.stages.clients.FollowingClient.createFollowing
import static org.openchat.acceptance.stages.clients.FollowingClient.retrieveFolloweesBy
import static org.openchat.acceptance.stages.clients.RegistrationClient.register
import static ratpack.http.MediaType.APPLICATION_JSON
import static ratpack.http.Status.OK

class GetFolloweesFeatureWithoutJGiven extends Specification {

    @Ignore
    def "return all followees for a given user"() {
        given:
        def biel = registerUser("biel")
        def rebeca = registerUser("rebeca")
        def sonia = registerUser("sonia")

        createFollowing biel.id, rebeca.id
        createFollowing biel.id, sonia.id

        when:
        def response = retrieveFolloweesBy biel.id

        then:
        assert response.status == OK
        assert response.body.contentType.type == APPLICATION_JSON
        assert usernamesFrom(response) ==  ["rebeca", "sonia"]
    }

    private User registerUser(String username) {
        def response = register anUser().withUsername(username).build()
        def json = new JsonSlurper().parseText(response.body.text)
        return anUser()
                .withId(json.id)
                .withUsername(json.username)
                .withAbout(json.about)
                .build()
    }

    private String[] usernamesFrom(ReceivedResponse response) {
        def followees = new JsonSlurper().parseText(response.body.text)
        return followees.stream()
                .map({ it.username })
                .toList()
    }
}

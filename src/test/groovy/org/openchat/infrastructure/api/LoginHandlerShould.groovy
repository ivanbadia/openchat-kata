package org.openchat.infrastructure.api

import arrow.core.Option
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput
import org.openchat.domain.user.User
import ratpack.http.MediaType
import ratpack.http.Status
import ratpack.test.handling.HandlingResult
import ratpack.test.handling.RequestFixture
import spock.lang.Specification

import static org.openchat.infrastructure.builders.UserBuilder.anUser

class LoginHandlerShould extends Specification {
    private static final String IVAN_USERNAME = "ivan"
    private static final String IVAN_PASSWORD = "password"
    private static final User IVAN = anUser()
            .withUsername(IVAN_USERNAME)
            .withPassword(IVAN_PASSWORD)
            .build()

    def "log in user"() {
        given:
        def loginUser = { String username, String password ->
            assert username == IVAN_USERNAME
            assert password == IVAN_PASSWORD
            return Option.@Companion.just(IVAN)
        }

        when:
        HandlingResult result = RequestFixture.handle(
                new LoginHandler(loginUser),
                { fixture -> fixture.body(jsonWith(IVAN_USERNAME, IVAN_PASSWORD), MediaType.APPLICATION_JSON) }
        )

        then:
        result.status == Status.OK
        result.bodyText == JsonOutput.toJson([userId: IVAN.id.asString(), username: IVAN_USERNAME, about: IVAN.about])
    }

    def "fail if credentials are not valid"() {
        given:
        def loginUser = { String username, String password -> Option.@Companion.empty() }

        when:
        HandlingResult result = RequestFixture.handle(
                new LoginHandler(loginUser),
                { fixture -> fixture.body(jsonWith(IVAN_USERNAME, IVAN_PASSWORD), MediaType.APPLICATION_JSON) }
        )

        then:
        result.status == Status.BAD_REQUEST
        result.bodyText == "Invalid credentials."
    }

    String jsonWith(String username, String password) {
        def objectNode = new ObjectMapper().createObjectNode()
        objectNode.put("username", username)
        objectNode.put("password", password)
        objectNode.toPrettyString()
    }
}

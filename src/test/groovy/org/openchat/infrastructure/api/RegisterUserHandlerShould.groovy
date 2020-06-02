package org.openchat.infrastructure.api

import arrow.core.Either
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput
import org.openchat.application.usecases.RegisterUserCmd
import org.openchat.domain.user.User
import org.openchat.domain.user.Username
import org.openchat.domain.user.UsernameAlreadyInUse
import ratpack.http.MediaType
import ratpack.http.Status
import ratpack.test.handling.HandlingResult
import ratpack.test.handling.RequestFixture
import spock.lang.Specification

import static org.openchat.builders.UserBuilder.anUser
import static ratpack.http.MediaType.APPLICATION_JSON

class RegisterUserHandlerShould extends Specification {
    private static final String IVAN_PASSWORD = "password"
    private static final User IVAN = anUser()
            .withUsername("ivan")
            .withPassword(IVAN_PASSWORD)
            .withAbout("about")
            .build()

    def "register user"() {
        given:
        RegisterUserCmd capturedRegisterUserCmd = null
        def registerUser = { RegisterUserCmd registerUserCmd ->
            capturedRegisterUserCmd = registerUserCmd
            new Either.Right<User>(IVAN)
        }

        when:
        HandlingResult result = RequestFixture.handle(
                new RegisterUserHandler(registerUser),
                { fixture -> fixture.body(jsonWith(IVAN), MediaType.APPLICATION_JSON) }
        )

        then:
        result.status == Status.CREATED
        result.headers["content-type"] == APPLICATION_JSON
        result.bodyText == JsonOutput.toJson([userId: IVAN.id.asString(), username: IVAN.username.asString(), about: IVAN.about])
        capturedRegisterUserCmd.properties == new RegisterUserCmd(IVAN.username.asString(), IVAN_PASSWORD, IVAN.about).properties
    }

    def "fail if username is already in use"() {
        given:
        def registerUser = {
            new Either.Left(new UsernameAlreadyInUse(new Username("ivan")))
        }

        when:
        HandlingResult result = RequestFixture.handle(
                new RegisterUserHandler(registerUser),
                { fixture -> fixture.body(jsonWith(IVAN), MediaType.APPLICATION_JSON) }
        )

        then:
        result.status == Status.BAD_REQUEST
        result.bodyText == "Username already in use."
    }

    String jsonWith(User user) {
        def objectNode = new ObjectMapper().createObjectNode()
        objectNode.put("username", user.username.asString())
        objectNode.put("password", IVAN_PASSWORD)
        objectNode.put("about", user.about)
        objectNode.toPrettyString()

    }
}

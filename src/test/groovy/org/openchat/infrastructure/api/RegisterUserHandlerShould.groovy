package org.openchat.infrastructure.api

import arrow.core.Either
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput
import org.openchat.application.usecases.RegisterUserCmd
import org.openchat.domain.user.User
import org.openchat.domain.user.Username
import org.openchat.domain.user.UsernameAlreadyInUse
import ratpack.http.Status
import ratpack.test.handling.HandlingResult
import ratpack.test.handling.RequestFixture
import spock.lang.Specification

import static org.openchat.builders.UserBuilder.anUser
import static ratpack.http.MediaType.APPLICATION_JSON

class RegisterUserHandlerShould extends Specification {
    private static final String USER_PASSWORD = "password"
    private static final User USER = anUser()
            .withUsername("ivan")
            .withPassword(USER_PASSWORD)
            .withAbout("about")
            .build()

    def "register user"() {
        given:
        RegisterUserCmd capturedRegisterUserCmd = null
        def registerUser = { RegisterUserCmd registerUserCmd ->
            capturedRegisterUserCmd = registerUserCmd
            new Either.Right<User>(USER)
        }

        when:
        HandlingResult result = RequestFixture.handle(
                new RegisterUserHandler(registerUser),
                { fixture -> fixture.body(jsonWith(USER), APPLICATION_JSON) }
        )

        then:
        result.status == Status.CREATED
        result.headers["content-type"] == APPLICATION_JSON
        result.bodyText == JsonOutput.toJson([id: USER.id.asString(), username: USER.username.asString(), about: USER.about])
        capturedRegisterUserCmd.properties == new RegisterUserCmd(USER.username.asString(), USER_PASSWORD, USER.about).properties
    }

    def "fail if username is already in use"() {
        given:
        def registerUser = {
            new Either.Left(new UsernameAlreadyInUse(new Username("ivan")))
        }

        when:
        HandlingResult result = RequestFixture.handle(
                new RegisterUserHandler(registerUser),
                { fixture -> fixture.body(jsonWith(USER), APPLICATION_JSON) }
        )

        then:
        result.status == Status.BAD_REQUEST
        result.bodyText == "Username already in use."
    }

    String jsonWith(User user) {
        def objectNode = new ObjectMapper().createObjectNode()
        objectNode.put("username", user.username.asString())
        objectNode.put("password", USER_PASSWORD)
        objectNode.put("about", user.about)
        objectNode.toPrettyString()

    }
}

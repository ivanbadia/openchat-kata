package org.openchat.infrastructure.api

import arrow.core.Either
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

import static org.openchat.infrastructure.builders.UserBuilder.anUser

class RegisterUserHandlerShould extends Specification {


    private static final String USERNAME = "username"
    private static final String PASSWORD = "password"
    private static final String ABOUT = "about"

    def JSON_BODY = """{"username": "$USERNAME",
                    "password": "$PASSWORD",
                    "about": "$ABOUT"
                    }"""

    def "register user"() {
        given:
        def user = anUser()
                .withUsername(USERNAME)
                .withAbout(ABOUT)
                .build()
        RegisterUserCmd capturedRegisterUserCmd = null
        def registerUser = { RegisterUserCmd registerUserCmd ->
            capturedRegisterUserCmd = registerUserCmd
            new Either.Right<User>(user)
        }

        when:
        HandlingResult result = RequestFixture.handle(
                new RegisterUserHandler(registerUser),
                { fixture -> fixture.body(JSON_BODY, MediaType.APPLICATION_JSON) }
        )

        then:
        result.status == Status.CREATED
        result.bodyText == JsonOutput.toJson([userId: user.id.asString(), username: USERNAME, about: ABOUT])
        capturedRegisterUserCmd.properties == new RegisterUserCmd(USERNAME, PASSWORD, ABOUT).properties
    }

    def "fail if username is already in use"() {
        given:
        def registerUser = {
            new Either.Left(new UsernameAlreadyInUse(new Username(USERNAME)))
        }

        when:
        HandlingResult result = RequestFixture.handle(
                new RegisterUserHandler(registerUser),
                { fixture -> fixture.body(JSON_BODY, MediaType.APPLICATION_JSON) }
        )

        then:
        result.status == Status.BAD_REQUEST
        result.bodyText == "Username already in use."
    }
}

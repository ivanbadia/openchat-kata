package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import groovy.json.JsonOutput
import org.openchat.acceptance.OpenChat
import org.openchat.domain.user.User
import ratpack.http.client.ReceivedResponse
import ratpack.http.client.RequestSpec

class When extends Stage<When> {
    @ProvidedScenarioState
    User user
    @ProvidedScenarioState
    ReceivedResponse response

    def the_registration_is_requested() {
        response = OpenChat.app.httpClient
                .requestSpec { RequestSpec requestSpec ->
                    requestSpec.body.type("application/json")
                    requestSpec.body.text(JsonOutput.toJson(username: user.username.asString(), password: user.password, about: user.about))
                }
                .post("/registration")

        self()
    }
}

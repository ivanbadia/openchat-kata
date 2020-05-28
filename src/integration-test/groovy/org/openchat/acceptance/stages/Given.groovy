package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import org.openchat.acceptance.stages.clients.RegistrationClient
import ratpack.http.client.ReceivedResponse

import static org.openchat.acceptance.stages.UserBuilder.anUser


class Given extends Stage<Given> {
    @ProvidedScenarioState
    User user
    @ProvidedScenarioState
    ReceivedResponse response

    def an_unregistered_user() {
        user = anUser().build()
        self()
    }

    def a_registered_user() {
        user = anUser().build()
        response = RegistrationClient.register user
        self()
    }

}

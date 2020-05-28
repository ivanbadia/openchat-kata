package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import ratpack.http.client.ReceivedResponse

import static org.openchat.acceptance.stages.clients.LoginClient.login
import static org.openchat.acceptance.stages.clients.RegistrationClient.register

class When extends Stage<When> {
    @ProvidedScenarioState
    User user
    @ProvidedScenarioState
    ReceivedResponse response

    def the_registration_is_requested() {
        response = register user
        self()
    }

    def the_user_performs_login() {
        response = login user
        self()
    }
}

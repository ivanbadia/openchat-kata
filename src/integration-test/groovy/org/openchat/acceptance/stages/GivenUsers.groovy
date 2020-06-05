package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ProvidedScenarioState

import static org.openchat.acceptance.stages.UserBuilder.anUser
import static org.openchat.acceptance.stages.clients.RegistrationClient.register

class GivenUsers extends Stage<GivenUsers> {
    @ProvidedScenarioState
    private List<User> users = []

    def registered_user(String username) {
        User user = anUser().withUsername(username).build()
        register user
        users.add(user)
        self()
    }

}

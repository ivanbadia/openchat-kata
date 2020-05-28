package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import org.openchat.domain.user.User

import static org.openchat.infrastructure.builders.UserBuilder.anUser

class Given extends Stage<Given> {
    @ProvidedScenarioState
    User user

    def an_unregistered_user() {
        user = anUser().build()
        self()
    }
}

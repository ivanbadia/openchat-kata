package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import groovy.json.JsonSlurper
import org.openchat.domain.user.User
import ratpack.http.MediaType
import ratpack.http.client.ReceivedResponse

import static ratpack.http.Status.CREATED

class Then extends Stage<Then> {
    private static final def UUID_PATTERN = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"

    @ExpectedScenarioState
    User user
    @ExpectedScenarioState
    ReceivedResponse response

    def the_user_is_registered() {
        assert response.status == CREATED
        assert response.body.contentType.type == MediaType.APPLICATION_JSON
        def createdUser = parseJson(response.body.text)
        assert createdUser.userId ==~ UUID_PATTERN
        assert createdUser.username == user.username.asString()
        assert createdUser.about == user.about

        self()
    }

    private Object parseJson(String json) {
        new JsonSlurper().parseText(json)
    }
}

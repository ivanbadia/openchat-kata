package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import groovy.json.JsonSlurper
import ratpack.http.MediaType
import ratpack.http.client.ReceivedResponse

import static ratpack.http.Status.CREATED
import static ratpack.http.Status.OK

class Then extends Stage<Then> {
    private static final def UUID_PATTERN = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"

    @ExpectedScenarioState
    User user
    @ExpectedScenarioState
    ReceivedResponse response

    def the_user_is_registered() {
        assert response.status == CREATED
        assertThatBodyContainsUser()

        self()
    }

    def the_user_is_logged_in() {
        assert response.status == OK
        assertThatBodyContainsUser()
        self()
    }

    private void assertThatBodyContainsUser() {
        assert response.body.contentType.type == MediaType.APPLICATION_JSON
        def createdUser = parseJson(response.body.text)
        assert createdUser.userId ==~ UUID_PATTERN
        assert createdUser.username == user.username
        assert createdUser.about == user.about
    }

    private Object parseJson(String json) {
        new JsonSlurper().parseText(json)
    }
}

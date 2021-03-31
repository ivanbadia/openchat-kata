package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import groovy.json.JsonSlurper
import org.openchat.acceptance.formatters.ArrayFormat
import ratpack.http.client.ReceivedResponse

import static ratpack.http.MediaType.APPLICATION_JSON
import static ratpack.http.Status.CREATED
import static ratpack.http.Status.OK

class ThenFollowing extends Stage<ThenFollowing> {
    @ExpectedScenarioState
    ReceivedResponse response

    def the_following_is_created() {
        assert response.status == CREATED
        return self()
    }

    def the_users_returned_are(@ArrayFormat String[] usernames) {
        assert response.status == OK
        assert response.body.contentType.type == APPLICATION_JSON
        assert usernamesFrom(response) == usernames
        return self()
    }

    private String[] usernamesFrom(ReceivedResponse response) {
        def followees = new JsonSlurper().parseText(response.body.text)
        return followees.stream()
                .map({ it.username })
                .toList()
    }
}


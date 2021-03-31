package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import com.tngtech.jgiven.annotation.Quoted
import groovy.json.JsonSlurper
import ratpack.http.MediaType
import ratpack.http.client.ReceivedResponse

import static ratpack.http.Status.CREATED
import static ratpack.http.Status.OK

class ThenFollowing extends Stage<ThenFollowing> {
    @ExpectedScenarioState
    ReceivedResponse response

    def the_following_is_created() {
        assert response.status == CREATED
        return self()
    }

    def the_users_displayed_are(String[] usernames) {
        assert response.status == OK
        assert response.body.contentType.type == MediaType.APPLICATION_JSON

        def followees = new JsonSlurper().parseText(response.body.text)
        assert followees.size() == usernames.size()
        followees.stream()
                .map({ it.username })
                .each { assert usernames.contains(it) }
        return self()
    }

}


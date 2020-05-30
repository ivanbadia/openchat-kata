package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import groovy.json.JsonSlurper
import ratpack.http.MediaType
import ratpack.http.client.ReceivedResponse

import static ratpack.http.Status.CREATED

class ThenPost extends Stage<ThenPost> {
    private static final def UUID_PATTERN = /[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}/

    @ExpectedScenarioState
    private User user
    @ExpectedScenarioState
    private ReceivedResponse response
    @ExpectedScenarioState
    private String postText

    def the_post_is_created(String text) {
        assert response.status == CREATED
        assert response.body.contentType.type == MediaType.APPLICATION_JSON
        def createdPost = parseJson(response.body.text)
        assert createdPost.postId ==~ UUID_PATTERN
        assert createdPost.userId == user.id
        assert createdPost.text == postText
        assert createdPost.dateTime != null
        self()
    }

    private Object parseJson(String json) {
        new JsonSlurper().parseText(json)
    }
}

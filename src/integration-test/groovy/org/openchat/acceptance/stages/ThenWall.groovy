package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import groovy.json.JsonSlurper
import ratpack.http.MediaType
import ratpack.http.client.ReceivedResponse

import static org.openchat.acceptance.stages.RegularExpressions.DATE_PATTERN
import static org.openchat.acceptance.stages.RegularExpressions.UUID_PATTERN
import static ratpack.http.Status.OK

class ThenWall extends Stage<ThenWall> {
    @ProvidedScenarioState
    private ReceivedResponse response

    def the_posts_shown_are(List<String> posts) {
        assert response.status == OK
        assert response.body.contentType.type == MediaType.APPLICATION_JSON
        List retrievedPosts = new JsonSlurper().parseText(response.body.text) as List
        assert retrievedPosts.size() == posts.size()
        for (int i = 0; i < retrievedPosts.size(); i++) {
            assert retrievedPosts[i].postId ==~ UUID_PATTERN
            assert retrievedPosts[i].userId ==~ UUID_PATTERN
            assert retrievedPosts[i].text == posts[i]
            assert retrievedPosts[i].dateTime ==~ DATE_PATTERN
        }
        self()
    }
}

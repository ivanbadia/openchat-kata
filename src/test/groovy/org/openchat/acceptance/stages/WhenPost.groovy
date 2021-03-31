package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import com.tngtech.jgiven.annotation.Quoted
import ratpack.http.client.ReceivedResponse

import static org.openchat.acceptance.stages.clients.PostsClient.createPost
import static org.openchat.acceptance.stages.clients.PostsClient.retrieveTimelineFor

class WhenPost extends Stage<WhenPost> {
    @ExpectedScenarioState
    private User user
    @ProvidedScenarioState
    private ReceivedResponse response
    @ProvidedScenarioState
    private List<String> posts = []

    def the_user_creates_a_post_with(@Quoted String text) {
        posts.add text
        response = createPost user.id, text
        self()
    }

    def the_user_checks_his_timeline() {
        response = retrieveTimelineFor user.id
        self()
    }

}

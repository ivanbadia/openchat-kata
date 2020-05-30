package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import ratpack.http.client.ReceivedResponse

import static org.openchat.acceptance.stages.clients.PostsClient.createPost

class WhenPost extends Stage<WhenPost> {
    @ExpectedScenarioState
    private User user
    @ProvidedScenarioState
    private ReceivedResponse response
    @ProvidedScenarioState
    private String postText

    def the_user_creates_a_post_with(String text) {
        postText = text
        response = createPost user.id, text
        self()
    }
}

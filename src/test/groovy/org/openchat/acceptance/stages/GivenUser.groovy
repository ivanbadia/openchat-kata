package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import ratpack.http.client.ReceivedResponse

import static org.openchat.acceptance.stages.UserBuilder.anUser
import static org.openchat.acceptance.stages.clients.PostsClient.createPost
import static org.openchat.acceptance.stages.clients.RegistrationClient.register

class GivenUser extends Stage<GivenUser> {
    @ProvidedScenarioState
    private User user
    @ProvidedScenarioState
    private ReceivedResponse response
    @ProvidedScenarioState
    private List<String> posts = []

    def an_unregistered_user() {
        user = anUser().build()
        self()
    }

    def a_registered_user() {
        user = anUser().build()
        response = register user
        self()
    }

    def post(String postText) {
        createPost user.id, postText
        posts.add(postText)
        self()
    }
}

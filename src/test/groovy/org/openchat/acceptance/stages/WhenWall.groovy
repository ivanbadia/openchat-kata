package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import ratpack.http.client.ReceivedResponse

import static org.openchat.acceptance.stages.clients.PostsClient.retrieveWallFor

class WhenWall extends Stage<WhenWall> {
    @ExpectedScenarioState
    private List<User> registeredUsers = []
    @ProvidedScenarioState
    private ReceivedResponse response

    def $_checks_the_wall(String username) {
        response = retrieveWallFor userIdFor(username)
    }

    private String userIdFor(String username) {
        registeredUsers.find({ user -> user.username == username }).id
    }
}
